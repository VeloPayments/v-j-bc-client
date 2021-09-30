/**
 * \file Certificate.h
 *
 * Class and method exports for Certificate.  This header includes a
 * static registration mechanism for creating global references to the
 * Certificate class, so that Certificate instances can be created
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
typedef struct Certificate_JavaVars
Certificate_JavaVars;

/**
 * Register the following Certificate references and make them global.
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
Certificate_register(
    JNIEnv* env,
    vjblockchain_native_instance* inst);

/**
 * \brief Java variables for Certificate.
 */
struct Certificate_JavaVars
{
    /* public class com.velopayments.blockchain.cert.Certificate {
     */
    jclass classid;

    /* public static com.velopayments.blockchain.cert.Certificate
     * fromByteArray(byte[]);
     * descriptor: ([B)Lcom/velopayments/blockchain/cert/Certificate;
     */
    jmethodID fromByteArray;

    /* public byte[] toByteArray();
     * descriptor: ()[B
     */
    jmethodID toByteArray;
};

/* helper macro. */
#define CERTIFICATE_JAVA_VARS() \
    Certificate_JavaVars Certificate

#ifdef __cplusplus
}
#endif /*__cplusplus*/
