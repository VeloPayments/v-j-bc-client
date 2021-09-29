/**
 * \file init_fwd.h
 *
 * Forward references for init.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */
#pragma once

#include <stdbool.h>
#include <rcpr/allocator.h>
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

/* make this header C++ friendly */
#ifdef __cplusplus
}
#endif /*__cplusplus*/
