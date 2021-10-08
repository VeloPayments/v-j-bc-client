/**
 * \file EncryptionPublicKey.h
 *
 * Class and method exports for EncryptionPublicKey.  This header includes a
 * static registration mechanism for creating global references to the
 * EncryptionPublicKey class, so that EncryptionPublicKey instances can be
 * created from C and methods for these instances can be called from C.
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
typedef struct EncryptionPublicKey_JavaVars
EncryptionPublicKey_JavaVars;

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
    vjblockchain_native_instance* inst);

/**
 * \brief Java variables for EncryptionPublicKey.
 */
struct EncryptionPublicKey_JavaVars
{
    /* public class com.velopayments.blockchain.crypt.EncryptionPublicKey {
     */
    jclass classid;

    /* public com.velopayments.blockchain.crypt.EncryptionPublicKey(byte[]);
     * descriptor: ([B)V
     */
    jmethodID init;

    /* public byte[] getRawBytes();
     * descriptor: ()[B
     */
    jmethodID getRawBytes;
};

/* helper macro. */
#define ENCRYPTION_PUBLIC_KEY_JAVA_VARS() \
    EncryptionPublicKey_JavaVars EncryptionPublicKey

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
