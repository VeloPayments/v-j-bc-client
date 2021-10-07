package com.velopayments.blockchain.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class SigningPublicKeyTest {

    @Test
    void ctor() {
        byte[] EXPECTED_ARRAY = {
            1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, 31, 32 };

        var msg = new SigningPublicKey(EXPECTED_ARRAY);
        assertEquals(EXPECTED_ARRAY, msg.getRawBytes());
    }

    /* Ed25519 TEST 2 from rfc8032. */
    @Test
    void sign() {
        byte[] PUBLIC_KEY = {
            61, 64, 23, -61, -24, 67, -119, 90, 
            -110, -73, 10, -89, 77, 27, 126, -68, 
            -100, -104, 44, -49, 46, -60, -106, -116, 
            -64, -51, 85, -15, 42, -12, 102, 12 };
        byte[] MESSAGE = { 114 };
        byte[] SIGNATURE = {
            -110, -96, 9, -87, -16, -44, -54, -72,
            114, 14, -126, 11, 95, 100, 37, 64,
            -94, -78, 123, 84, 22, 80, 63, -113,
            -77, 118, 34, 35, -21, -37, 105, -38,
            8, 90, -63, -28, 62, 21, -103, 110,
            69, -113, 54, 19, -48, -15, 29, -116,
            56, 123, 46, -82, -76, 48, 42, -18,
            -80, 13, 41, 22, 18, -69, 12, 0 };

        var key = new SigningPublicKey(PUBLIC_KEY);
        var signature = new Signature(SIGNATURE);
        var msg = new Message(MESSAGE);

        assertEquals(true, key.verify(signature, msg));
    }
}
