/**
 * \file CertificateBuilder_signNative.c
 *
 * Sign the certificate and emit it as a byte array.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */

#include <cbmc/model_assert.h>
#include <com/velopayments/blockchain/init/init.h>
#include <string.h>
#include <vccrypt/suite.h>
#include <vpr/parameters.h>

/*
 * Class:     com_velopayments_blockchain_cert_CertificateBuilder
 * Method:    signNative
 * Signature: ([B[B)Lcom/velopayments/blockchain/cert/Certificate;
 */
JNIEXPORT jobject JNICALL
Java_com_velopayments_blockchain_cert_CertificateBuilder_signNative(
    JNIEnv *env, jobject that, jlong nativeInst, jbyteArray signer_id,
    jbyteArray private_key)
{
    jobject retval = NULL;

    /* function contract enforcement */
    MODEL_ASSERT(MODEL_PROP_VALID_JNI_ENV(env));
    MODEL_ASSERT(0 != nativeInst);
    MODEL_ASSERT(NULL != that);
    MODEL_ASSERT(NULL != signer_id);
    MODEL_ASSERT(NULL != private_key);

    /* get a pointer to the native instance. */
    vjblockchain_native_instance* native_inst =
        (vjblockchain_native_instance*)nativeInst;

    /* verify that the vjblockchain library has been initialized. */
    if (!native_inst || !native_inst->initialized)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "vjblockchain not initialized.");
        return NULL;
    }

    /* verify that the signer_id parameter is not null. */
    if (NULL == signer_id)
    {
        (*env)->ThrowNew(
            env, native_inst->NullPointerException.classid, "signerId");
        return NULL;
    }

    /* verify that the private_key parameter is not null. */
    if (NULL == private_key)
    {
        (*env)->ThrowNew(
            env, native_inst->NullPointerException.classid, "privateKey");
        return NULL;
    }

    /* verify the signer id size matches */
    jsize signer_id_size = (*env)->GetArrayLength(env, signer_id);
    if (16 != signer_id_size)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalArgumentException.classid,
            "Signer ID is the wrong size");
        return NULL;
    }

    /* compute the size of the certificate to emit */
    jobject lst =
        (*env)->GetObjectField(
            env, that, native_inst->CertificateBuilder.field_fields);
    jint lst_size =
        (*env)->CallIntMethod(
            env, lst, native_inst->LinkedList.size);
    jsize cert_size = 0;
    for (jint i = 0; i < lst_size; ++i)
    {
        jobject pair =
            (*env)->CallObjectMethod(
                env, lst, native_inst->LinkedList.get, i);
        jbyteArray array =
            (jbyteArray)(*env)->CallObjectMethod(
                env, pair, native_inst->SimpleEntry.getValue);
        jsize array_len = 
            (*env)->GetArrayLength(env, array);

        cert_size += FIELD_TYPE_SIZE + FIELD_SIZE_SIZE + array_len;

        /* clean up references */
        (*env)->DeleteLocalRef(env, pair);
        (*env)->DeleteLocalRef(env, array);
    }

    /* calculate the signature size and add this to the cert size */
    cert_size += FIELD_TYPE_SIZE*2 + FIELD_SIZE_SIZE*2 + 16 +
                 native_inst->crypto_suite.sign_opts.signature_size;

    /* create builder context */
    vccert_builder_context_t builder;
    if (0 != vccert_builder_init(
                    &native_inst->builder_opts, &builder, cert_size))
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid, "general error.");
        return NULL;
    }

    /* for each field; add it to the builder */
    for (jint i = 0; i < lst_size; ++i)
    {
        jobject pair =
            (*env)->CallObjectMethod(
                env, lst, native_inst->LinkedList.get, i);
        jobject int_obj =
            (*env)->CallObjectMethod(
                env, pair, native_inst->SimpleEntry.getKey);
        jint field_type =
            (*env)->CallIntMethod(
                env, int_obj, native_inst->Integer.intValue);
        jbyteArray array =
            (jbyteArray)(*env)->CallObjectMethod(
                env, pair, native_inst->SimpleEntry.getValue);
        jsize array_len = 
            (*env)->GetArrayLength(env, array);
        jbyte* field_val = (*env)->GetByteArrayElements(env, array, NULL);

        if (0 !=
            vccert_builder_add_short_buffer(
                &builder, field_type, (const uint8_t*)field_val, array_len))
        {
            (*env)->ReleaseByteArrayElements(env, array, field_val, JNI_ABORT);
            (*env)->ThrowNew(
                env, native_inst->IllegalStateException.classid,
                "general error.");
            retval = NULL;
            goto dispose_builder;
        }

        /* clean up references */
        (*env)->DeleteLocalRef(env, pair);
        (*env)->DeleteLocalRef(env, int_obj);
        (*env)->ReleaseByteArrayElements(env, array, field_val, JNI_ABORT);
        (*env)->DeleteLocalRef(env, array);
    }

    /* create a buffer for the private key */
    vccrypt_buffer_t priv;
    if (0 != vccrypt_suite_buffer_init_for_signature_private_key(
                &native_inst->crypto_suite, &priv))
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "Couldn't create private key buffer");
        retval = NULL;
        goto dispose_builder;
    }

    /* verify that the private key size matches the crypto suite size */
    jsize priv_size = (*env)->GetArrayLength(env, private_key);
    if ((unsigned)priv_size != priv.size)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalArgumentException.classid,
            "Private key is the wrong size");
        retval = NULL;
        goto dispose_priv;
    }

    /* get the private key from Java */
    jbyte* priv_bytes = (*env)->GetByteArrayElements(env, private_key, NULL);
    memcpy(priv.data, priv_bytes, priv.size);
    (*env)->ReleaseByteArrayElements(env, private_key, priv_bytes, JNI_ABORT);

    /* get the signer id raw bytes from Java */
    jbyte* signer_id_bytes =
        (*env)->GetByteArrayElements(env, signer_id, NULL);

    /* sign the certificate */
    if (0 != vccert_builder_sign(
                    &builder, (const uint8_t*)signer_id_bytes, &priv))
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "signature error");
        retval = NULL;
        goto release_signer_id;
    }

    /* use emit() to get the current size and assert that it matches */
    size_t emitted_size = 0;
    const uint8_t* outbuf = vccert_builder_emit(&builder, &emitted_size);
    if (emitted_size != (unsigned)cert_size)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "emitted_size != cert_size");
        retval = NULL;
        goto release_signer_id;
    }

    /* allocate a byte array of the appropriate size */
    jbyteArray out = (*env)->NewByteArray(env, cert_size);

    /* get the buffer of this array */
    jbyte* outData = (*env)->GetByteArrayElements(env, out, NULL);

    /* copy the buffer data to the output array */
    memcpy(outData, outbuf, cert_size);

    /* commit data to the byte array */
    (*env)->ReleaseByteArrayElements(env, out, outData, 0);

    /* set retval to the byte array */
    retval = 
        (*env)->CallStaticObjectMethod(
            env, native_inst->Certificate.classid,
            native_inst->Certificate.fromByteArray, out);

    /* clean up */
release_signer_id:
    (*env)->ReleaseByteArrayElements(
        env, signer_id, signer_id_bytes, JNI_ABORT);

dispose_priv:
    dispose((disposable_t*)&priv);

dispose_builder:
    dispose((disposable_t*)&builder);

    return retval;
}
