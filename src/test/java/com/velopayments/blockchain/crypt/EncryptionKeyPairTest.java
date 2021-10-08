package com.velopayments.blockchain.crypt;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class EncryptionKeyPairTest {

    /**
     * Test that we can create an encryption key pair from a public key and a
     * private key.
     */
    @Test
    public void create() throws Exception {
        final byte[] PUB_KEY_BYTES = { 1, 2, 3, 4 };
        final byte[] PRIV_KEY_BYTES = { 5, 6, 7, 8 };
        var pubkey = new EncryptionPublicKey(PUB_KEY_BYTES);
        var privkey = new EncryptionPrivateKey(PRIV_KEY_BYTES);

        var keypair = new EncryptionKeyPair(pubkey, privkey);

        assertEquals(pubkey, keypair.getPublicKey());
        assertEquals(privkey, keypair.getPrivateKey());
    }

    /**
     * Test that we can generate an encryption keypair.
     */
    @Test
    public void generate() throws Exception {
        var keypair = EncryptionKeyPair.generate();

        assertNotEquals(null, keypair.getPublicKey());
        assertNotEquals(null, keypair.getPrivateKey());
    }
}
