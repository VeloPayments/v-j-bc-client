package com.velopayments.blockchain.crypt;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class SigningKeyPairTest {

    /**
     * Test that we can generate a signing keypair.
     */
    @Test
    public void generateSigningKeyPairTest() throws Exception {
        var key = SigningKeyPair.generate();

        assertNotEquals(null, key.getPublicKey().getRawBytes());
        assertNotEquals(null, key.getPrivateKey().getRawBytes());
    }

    /**
     * Test that we can sign a message with the private key and verify the
     * message with the public key.
     */
    @Test
    public void signVerifyMessage() throws Exception {
        var key = SigningKeyPair.generate();
        var testMsg = "This is a test.";

        /* create the message to sign. */
        var m = new Message(testMsg.getBytes(StandardCharsets.UTF_8));

        /* sign message. */
        var s = key.getPrivateKey().sign(m);

        /* verify that the signed message matches the signature. */
        assertEquals(true, key.getPublicKey().verify(s, m));
    }

    /**
     * Test that passing a null Message causes sign to throw an NPE.
     */
    @Test
    public void signMessageNPE() throws Exception {
        final var key = SigningKeyPair.generate();

        /* sign null throws exception. */
        assertThrows(NullPointerException.class, () -> {
            var s = key.getPrivateKey().sign(null);
        });
    }

    /**
     * Test that we passing a null to verify throws an NPE.
     */
    @Test
    public void verifyMessageNPE1() throws Exception {
        var key = SigningKeyPair.generate();

        /* verify null, null throws exception. */
        assertThrows(NullPointerException.class, () -> {
            key.getPublicKey().verify(null, null);
        });
    }

    /**
     * Test that a bad signature fails verification.
     */
    @Test
    public void signVerifyMessageFails() throws Exception {
        var key = SigningKeyPair.generate();
        var fake = SigningKeyPair.generate();
        var testMsg = "This is a test.";

        /* create the message to sign. */
        var m = new Message(testMsg.getBytes(StandardCharsets.UTF_8));

        /* sign message. */
        var fakeSignature = fake.getPrivateKey().sign(m);

        /* verify that the signed message matches the signature. */
        assertEquals(false, key.getPublicKey().verify(fakeSignature, m));
    }
}
