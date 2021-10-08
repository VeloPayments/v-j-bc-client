package com.velopayments.blockchain.crypt;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class EncryptionPublicKeyTest {

    /**
     * Test that we can create an encryption public key.
     */
    @Test
    public void create() throws Exception {
        final byte[] KEY_BYTES = { 1, 2, 3, 4 };

        var key = new EncryptionPublicKey(KEY_BYTES);

        assertEquals(KEY_BYTES, key.getRawBytes());
    }
}
