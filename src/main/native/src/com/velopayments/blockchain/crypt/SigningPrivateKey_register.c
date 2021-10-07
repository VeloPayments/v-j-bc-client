/**
 * \file SigningPrivateKey.c
 *
 * Class and method exports for SigningPrivateKey.  This source file contains
 * a registration method that registers the class and method references just
 * once, making each of these a global reference.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */

#include <cbmc/model_assert.h>
#include <com/velopayments/blockchain/init/init.h>
#include <stdbool.h>

/**
 * Property: SigningPrivateKey globals are set.
 */
#define MODEL_PROP_GLOBALS_SET(inst) \
    (   NULL != inst->SigningPrivateKey.classid \
     && NULL != inst->SigningPrivateKey.init \
     && NULL != inst->SigningPrivateKey.getRawBytes)

/**
 * Register the following SigningPrivateKey references and make them global.
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
SigningPrivateKey_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst)
{
    jclass tempClassID;

    /* function contract enforcement */
    MODEL_ASSERT(MODEL_PROP_VALID_JNI_ENV(env));

    /* register KeyPair class */
    tempClassID = (*env)->FindClass(env,
        "com/velopayments/blockchain/crypt/SigningPrivateKey");
    if (NULL == tempClassID)
        return 1;

    /* register KeyPair class */
    inst->SigningPrivateKey.classid =
        (jclass)(*env)->NewGlobalRef(env, tempClassID);
    if (NULL == inst->SigningPrivateKey.classid)
        return 1;

    /* we don't need this local reference anymore */
    (*env)->DeleteLocalRef(env, tempClassID);

    /* register init method */
    inst->SigningPrivateKey.init =
        (*env)->GetMethodID(
            env, inst->SigningPrivateKey.classid, "<init>", "([B)V");
    if (NULL == inst->SigningPrivateKey.init)
        return 1;

    /* register getRawBytes method */
    inst->SigningPrivateKey.getRawBytes =
        (*env)->GetMethodID(
            env, inst->SigningPrivateKey.classid, "getRawBytes", "()[B");
    if (NULL == inst->SigningPrivateKey.getRawBytes)
        return 1;

    /* globals invariant in place. */
    MODEL_ASSERT(MODEL_PROP_GLOBALS_SET(inst));

    /* success */
    return 0;
}
