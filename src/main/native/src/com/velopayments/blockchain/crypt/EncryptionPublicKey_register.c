/**
 * \file EncryptionPublicKey_register.c
 *
 * Class and method exports for EncryptionPublicKey.  This source file contains
 * a registration method that registers the class and method references just
 * once, making each of these a global reference.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */

#include <cbmc/model_assert.h>
#include <com/velopayments/blockchain/init/init.h>
#include <stdbool.h>

/**
 * Property: EncryptionPublicKey globals are set.
 */
#define MODEL_PROP_GLOBALS_SET(inst) \
    (   NULL != inst->EncryptionPublicKey.classid \
     && NULL != inst->EncryptionPublicKey.init \
     && NULL != inst->EncryptionPublicKey.getRawBytes)

/**
 * Register the following EncryptionPublicKey references and make them global.
 *
 * Note: this method must be called in a synchronized static initialization
 * block in Java to ensure that there isn't a registration race.  This method
 * must be called before any of the following references are used.
 *
 * \param env   JNI environment to use.
 * \param inst  native instance to initialize.
 *
 * \returns 0 on success and non-zero on failure.
 */
int
EncryptionPublicKey_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst)
{
    jclass tempClassID;

    /* function contract enforcement */
    MODEL_ASSERT(MODEL_PROP_VALID_JNI_ENV(env));

    /* register EncryptionPublicKey class */
    tempClassID = (*env)->FindClass(env,
        "com/velopayments/blockchain/crypt/EncryptionPublicKey");
    if (NULL == tempClassID)
        return 1;

    /* register EncryptionPublicKey class */
    inst->EncryptionPublicKey.classid =
        (jclass)(*env)->NewGlobalRef(env, tempClassID);
    if (NULL == inst->EncryptionPublicKey.classid)
        return 1;

    /* we don't need this local reference anymore */
    (*env)->DeleteLocalRef(env, tempClassID);

    /* register init method */
    inst->EncryptionPublicKey.init =
        (*env)->GetMethodID(
            env, inst->EncryptionPublicKey.classid, "<init>", "([B)V");
    if (NULL == inst->EncryptionPublicKey.init)
        return 1;

    /* register getRawBytes method */
    inst->EncryptionPublicKey.getRawBytes =
        (*env)->GetMethodID(
            env, inst->EncryptionPublicKey.classid, "getRawBytes", "()[B");
    if (NULL == inst->EncryptionPublicKey.getRawBytes)
        return 1;

    /* globals invariant in place. */
    MODEL_ASSERT(MODEL_PROP_GLOBALS_SET(inst));

    /* success */
    return 0;
}
