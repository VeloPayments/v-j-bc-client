/**
 * \file EntityFactory_decryptCertificate.c
 *
 * Decrypt a certificate from raw bytes and a pass phrase.
 *
 * \copyright 2021 Velo Payments, Inc.  All rights reserved.
 */

#include <cbmc/model_assert.h>
#include <com/velopayments/blockchain/init/init.h>
#include <string.h>
#include <vccrypt/suite.h>
#include <vctool/certificate.h>
#include <vpr/parameters.h>

/*
 * Class:     com_velopayments_blockchain_util_EntityFactory
 * Method:    decryptCertificate
 * Signature: (J[B[B)Lcom/velopayments/blockchain/cert/Certificate;
 */
JNIEXPORT jobject JNICALL
Java_com_velopayments_blockchain_util_EntityFactory_decryptCertificate(
    JNIEnv* env, jclass UNUSED(clazz), jlong nativeInst, jbyteArray enc_cert,
    jbyteArray passphrase)
{
    jobject result = NULL;
    int retval;
    vccrypt_buffer_t encrypted_cert, password;
    vccrypt_buffer_t* cert;

    /* function contract enforcement. */
    MODEL_ASSERT(MODEL_PROP_VALID_JNI_ENV(env));
    MODEL_ASSERT(0 != nativeInst);
    MODEL_ASSERT(NULL != enc_cert);
    MODEL_ASSERT(NULL != passphrase);

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

    /* verify that the enc_cert parameter is not null. */
    if (NULL == enc_cert)
    {
        (*env)->ThrowNew(
            env, native_inst->NullPointerException.classid, "encCert");
        return NULL;
    }

    /* verify that the passphrase parameter is not null. */
    if (NULL == passphrase)
    {
        (*env)->ThrowNew(
            env, native_inst->NullPointerException.classid, "passphrase");
        return NULL;
    }

    /* get the C bytes for the encrypted certificate byte array. */
    jbyte* encbuffer = (*env)->GetByteArrayElements(env, enc_cert, NULL);
    jsize encbuffer_size = (*env)->GetArrayLength(env, enc_cert);

    /* get the C bytes for the passphrase byte array. */
    jbyte* passbuffer = (*env)->GetByteArrayElements(env, passphrase, NULL);
    jsize passbuffer_size = (*env)->GetArrayLength(env, passphrase);

    /* create a buffer instance for the enc buffer. */
    retval =
        vccrypt_buffer_init(
            &encrypted_cert, &native_inst->alloc_opts, encbuffer_size);
    if (0 != retval)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "encrypted_cert could not be initialized.");
        goto releaseJBuffer;
    }
    memcpy(encrypted_cert.data, encbuffer, encbuffer_size);

    /* create a buffer instance for the passphrase buffer. */
    retval =
        vccrypt_buffer_init(
            &password, &native_inst->alloc_opts, passbuffer_size);
    if (0 != retval)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalStateException.classid,
            "password could not be initialized.");
        goto release_encrypted_cert_buffer;
    }
    memcpy(password.data, passbuffer, passbuffer_size);

    /* decrypt the buffer. */
    retval =
        certificate_decrypt(
            &native_inst->crypto_suite, &cert, &encrypted_cert, &password);
    if (0 != retval)
    {
        (*env)->ThrowNew(
            env, native_inst->IllegalArgumentException.classid, "bad cert");
        goto release_password_buffer;
    }

    /* allocate a byte array. */
    jbyteArray out = (*env)->NewByteArray(env, cert->size);

    /* get the buffer of this array. */
    jbyte* outData = (*env)->GetByteArrayElements(env, out, NULL);

    /* copy the cert data to the output array. */
    memcpy(outData, cert->data, cert->size);

    /* commit data to the byte array. */
    (*env)->ReleaseByteArrayElements(env, out, outData, 0);

    /* create the return object. */
    result =
        (*env)->CallStaticObjectMethod(
            env, native_inst->Certificate.classid,
            native_inst->Certificate.fromByteArray, out);

    /* success? */
    goto release_cert_buffer;

release_cert_buffer:
    dispose((disposable_t*)cert);
    free(cert);

release_password_buffer:
    dispose((disposable_t*)&password);

release_encrypted_cert_buffer:
    dispose((disposable_t*)&encrypted_cert);

releaseJBuffer:
    (*env)->ReleaseByteArrayElements(env, enc_cert, encbuffer, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, passphrase, passbuffer, JNI_ABORT);

    return result;
}
