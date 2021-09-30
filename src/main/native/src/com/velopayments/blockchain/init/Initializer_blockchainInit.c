/**
 * \file Initializer_blockchainInit.c
 *
 * Initialize the vcblockchain subsystem.
 *
 * \copyright 2017-2021 Velo Payments, Inc.  All rights reserved.
 */

#include <cbmc/model_assert.h>
#include <com_velopayments_blockchain_init_Initializer.h>
#include <com/velopayments/blockchain/init/init.h>
#include <java/lang/IllegalStateException.h>
#include <rcpr/allocator.h>
#include <string.h>
#include <vccrypt/suite.h>
#include <vpr/allocator/malloc_allocator.h>
#include <vpr/parameters.h>

RCPR_IMPORT_allocator_as(rcpr);

#define INIT_OR_FAIL(text, invocation) \
    if (0 != (invocation)) \
    { \
        (*env)->ThrowNew(env, native_inst->IllegalStateException.classid, \
                         text " not initialized."); \
        return 0; \
    } \
    do { } while (0)

/*
 * Class:     com_velopayments_blockchain_init_Initializer
 * Method:    blockchainInit
 * Signature: ()Z
 */
JNIEXPORT jlong JNICALL
Java_com_velopayments_blockchain_init_Initializer_blockchainInit(
    JNIEnv* env, jclass UNUSED(that))
{
    vjblockchain_native_instance* native_inst = NULL;

    /* function contract enforcement */
    MODEL_ASSERT(MODEL_PROP_VALID_JNI_ENV(env));

    /* register crypto suite */
    vccrypt_suite_register_velo_v1();

    /* create our global instance. */
    native_inst =
        (vjblockchain_native_instance*)
        malloc(sizeof(vjblockchain_native_instance));
    memset(native_inst, 0, sizeof(*native_inst));

    /* we need IllegalStateException further on.  If this registration fails,
     * we're dead in the water.
     */
    if (0 != IllegalStateException_register(env, native_inst))
    {
        free(native_inst);

        return 0;
    }

    /* create allocator used by vjblockchain C methods. */
    malloc_allocator_options_init(&native_inst->alloc_opts);
    MODEL_ASSERT(MODEL_PROP_VALID_VPR_ALLOCATOR(&alloc_opts));

    /* create our RCPR allocator. */
    INIT_OR_FAIL(
        "RCPR allocator",
         (rcpr_malloc_allocator_create(&native_inst->alloc)));
    MODEL_ASSERT(prop_allocator_valid(native_inst->alloc));

    /* create crypto suite used by Velo certificates. */
    INIT_OR_FAIL(
        "vccrypt suite options",
        (vccrypt_suite_options_init(
            &native_inst->crypto_suite, &native_inst->alloc_opts,
            VCCRYPT_SUITE_VELO_V1)));
    MODEL_ASSERT(
        MODEL_PROP_VALID_VCCRYPT_CRYPTO_SUITE(&native_inst->crypto_suite));

    /* create a builder options instance. */
    INIT_OR_FAIL("vccert builder options",
                 (vccert_builder_options_init(
                        &native_inst->builder_opts, &native_inst->alloc_opts,
                        &native_inst->crypto_suite)));
    MODEL_ASSERT(
        MODEL_PROP_VALID_VCCERT_BUILDER_OPTIONS(&native_inst->builder_opts));

    /* register Java classes used by the client library.. */
    INIT_OR_FAIL(
        "AttestationException",
        AttestationException_register(env, native_inst));
    INIT_OR_FAIL(
        "Certificate",
        Certificate_register(env, native_inst));

    /* we are now initialized */
    native_inst->initialized = true;

    return (jlong)native_inst;
}
