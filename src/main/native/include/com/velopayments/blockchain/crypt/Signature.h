/**
 * \file Signature.h
 *
 * Class and method exports for Signature.  This header includes a static
 * registration mechanism for creating global references to the Signature class,
 * so that Signature instances can be created from C and methods for these
 * instances can be called from C.
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
typedef struct Signature_JavaVars
Signature_JavaVars;

/**
 * Register the following Signature references and make them global.
 *
 * Note: this method must be called in a synchronized static initialization
 * block in Java to ensure that there isn't a registration race.  This method
 * must be called before any of the following references are used.
 *
 * \param env   JNI environment to use.
 *
 * \returns 0 on success and non-zero on failure.
 */
int
Signature_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst);

/**
 * \brief Java variables for Signature.
 */
struct Signature_JavaVars
{
    /* public class com.velopayments.blockchain.crypt.Signature {
     */
    jclass classid;

    /* public com.velopayments.blockchain.crypt.Signature(byte[]);
     * descriptor: ([B)V
     */
    jmethodID init;

    /* public byte[] getSignatureBytes();
     * descriptor: ()[B
     */
    jmethodID getSignatureBytes;
};

/* helper macro. */
#define SIGNATURE_JAVA_VARS() \
    Signature_JavaVars Signature

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
