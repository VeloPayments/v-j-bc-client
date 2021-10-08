package com.velopayments.blockchain.crypt;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class EncryptionPrivateKeyTest {

    /**
     * Test that we can create an encryption private key.
     */
    @Test
    public void create() throws Exception {
        final byte[] KEY_BYTES = { 1, 2, 3, 4 };

        var key = new EncryptionPrivateKey(KEY_BYTES);

        assertEquals(KEY_BYTES, key.getRawBytes());
    }
}
