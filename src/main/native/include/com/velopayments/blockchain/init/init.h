/**
 * \file init.h
 *
 * Reference to init flag and Velo C related control structures.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */
#pragma once

#include <com/velopayments/blockchain/cert/AttestationException.h>
#include <com/velopayments/blockchain/cert/Certificate.h>
#include <java/lang/IllegalStateException.h>
#include <rcpr/allocator.h>
#include <stdbool.h>
#include <vccert/builder.h>
#include <vccert/parser.h>
#include <vccrypt/stream_cipher.h>
#include <vccrypt/suite.h>
#include <vpr/allocator.h>

/* make this header C++ friendly */
#ifdef __cplusplus
extern "C" {
#endif /*__cplusplus*/

struct vjblockchain_native_instance;
typedef struct vjblockchain_native_instance vjblockchain_native_instance;

struct vjblockchain_native_instance
{
    bool initialized;
    RCPR_SYM(allocator)* alloc;
    allocator_options_t alloc_opts;
    vccrypt_suite_options_t crypto_suite;
    vccert_builder_options_t builder_opts;

    ATTESTATION_EXCEPTION_JAVA_VARS();
    ILLEGAL_STATE_EXCEPTION_JAVA_VARS();
    CERTIFICATE_JAVA_VARS();
};

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
