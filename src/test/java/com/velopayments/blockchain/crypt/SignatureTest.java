package com.velopayments.blockchain.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class SignatureTest {

    @Test
    void ctor() {
        byte[] EXPECTED_ARRAY = { 1, 2, 3, 4, 5 };

        var msg = new Signature(EXPECTED_ARRAY);
        assertEquals(EXPECTED_ARRAY, msg.getSignatureBytes());
    }
}
