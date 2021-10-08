/**
 * \file EncryptionKeyPair.h
 *
 * Class and method exports for EncryptionKeyPair.  This header includes a
 * static registration mechanism for creating global references to the
 * EncryptionKeyPair class, so that EncryptionKeyPair instances can be created
 * from C and methods for these instances can be called from C.
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
typedef struct EncryptionKeyPair_JavaVars
EncryptionKeyPair_JavaVars;

/**
 * Register the following EncryptionKeyPair references and make them global.
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
EncryptionKeyPair_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst);

/**
 * \brief Java variables for EncryptionKeyPair.
 */
struct EncryptionKeyPair_JavaVars
{
    /* public class com.velopayments.blockchain.crypt.EncryptionKeyPair {
     */
    jclass classid;

    /* public com.velopayments.blockchain.crypt.EncryptionKeyPair(
     *      com.velopayments.blockchain.crypt.EncryptionPublicKey,
     *      com.velopayments.blockchain.crypt.EncryptionPrivateKey);
     * descriptor: (Lcom/velopayments/blockchain/crypt/EncryptionPublicKey;
     *              Lcom/velopayments/blockchain/crypt/EncryptionPrivateKey;)V
     */
    jmethodID init;

    /* public com.velopayments.blockchain.crypt.EncryptionPublicKey
     * getPublicKey();
     * descriptor: ()Lcom/velopayments/blockchain/crypt/EncryptionPublicKey;
     */
    jmethodID getPublicKey;

    /* public com.velopayments.blockchain.crypt.EncryptionPrivateKey
     * getPrivateKey();
     * descriptor: ()Lcom/velopayments/blockchain/crypt/EncryptionPrivateKey;
     */
    jmethodID getPrivateKey;
};

/* helper macro. */
#define ENCRYPTION_KEY_PAIR_JAVA_VARS() \
    EncryptionKeyPair_JavaVars EncryptionKeyPair

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
