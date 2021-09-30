package com.velopayments.blockchain.cert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class CertificateTest {

    @Test
    void fromByteArray() {
        byte[] EXPECTED_ARRAY = { 1, 2, 3, 4, 5 };

        var cert = Certificate.fromByteArray(EXPECTED_ARRAY);
        assertEquals(EXPECTED_ARRAY, cert.toByteArray());
    }
}
