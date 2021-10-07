/**
 * \file SigningKeyPair.h
 *
 * Class and method exports for SigningKeyPair.  This header includes a static
 * registration mechanism for creating global references to the SigningKeyPair
 * class, so that SigningKeyPair instances can be created from C and methods for
 * these instances can be called from C.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */

#pragma once

#include <com/velopayments/blockchain/init/init_fwd.h>
#include <jni.h>

/* make this header C++ friendly */
#ifdef __cplusplus
extern "C" {
#endif /*__cplusplus*/

/* forward decls. */
typedef struct SigningKeyPair_JavaVars
SigningKeyPair_JavaVars;

/**
 * Register the following SigningKeyPair references and make them global.
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
SigningKeyPair_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst);

/**
 * \brief Java variables for SigningKeyPair.
 */
struct SigningKeyPair_JavaVars
{
    /* public class com.velopayments.blockchain.crypt.SigningKeyPair {
     */
    jclass classid;

    /* private com.velopayments.blockchain.crypt.SigningKeyPair(
     *      SigningPublicKey, SigningPrivateKey)
     * descriptor:
     *      (Lcom/velopayments/blockchain/crypt/SigningPublicKey;
     *       Lcom/velopayments/blockchain/crypt/SigningPrivateKey;)V
     */
    jmethodID init;
};

/* helper macro. */
#define SIGNING_KEY_PAIR_JAVA_VARS() \
    SigningKeyPair_JavaVars SigningKeyPair

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
