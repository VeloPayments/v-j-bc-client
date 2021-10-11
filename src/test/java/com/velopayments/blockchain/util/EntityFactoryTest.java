package com.velopayments.blockchain.util;

import com.velopayments.blockchain.cert.Certificate;
import com.velopayments.blockchain.crypt.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityFactoryTest {

    /**
     * Test that we can create an Entity from a pub key cert.
     */
    @Test
    public void createEntityFromCertificate() throws Exception {
        final byte[] PUB_CERT = {
            0, 1, 0, 4, 0, 1, 0, 0, 
            0, 48, 0, 16, 67, -86, -94, -31, 
            30, 125, 76, -6, -117, -110, -9, -53, 
            7, 30, 68, -116, 0, 32, 0, 2, 
            0, 1, 0, 65, 0, 16, 6, 36, 
            66, -28, 40, 102, 127, -96, -102, 24, 
            81, -41, 32, 109, 17, -28, 0, 82, 
            0, 32, 75, 117, -73, 97, 126, 77, 
            -127, 53, 42, 106, 31, 0, -20, -34, 
            -116, -14, -82, -102, -64, -82, 109, 124, 
            -9, 93, -62, 48, 76, -16, -128, 62, 
            -114, 62, 0, 83, 0, 32, -34, -78, 
            -98, -125, -55, 107, -105, -24, -21, 33, 
            -118, 59, -15, 78, 70, -40, -73, 79, 
            -3, 55, -33, -20, 93, -97, 3, -125, 
            112, -73, -127, 118, -50, -78, 10, 
        };
        final var ENTITY_ID =
            UUID.fromString("062442e4-2866-7fa0-9a18-51d7206d11e4");
        final byte[] ENC_PUB_KEY = {
            75, 117, -73, 97, 126, 77, -127, 53, 
            42, 106, 31, 0, -20, -34, -116, -14, 
            -82, -102, -64, -82, 109, 124, -9, 93, 
            -62, 48, 76, -16, -128, 62, -114, 62, 
        };
        final byte[] SIGN_PUB_KEY = {
            -34, -78, -98, -125, -55, 107, -105, -24, 
            -21, 33, -118, 59, -15, 78, 70, -40, 
            -73, 79, -3, 55, -33, -20, 93, -97, 
            3, -125, 112, -73, -127, 118, -50, -78, 
        };

        var cert = Certificate.fromByteArray(PUB_CERT);
        var entity = EntityFactory.createEntityFromCertificate(cert);

        assertEquals(ENTITY_ID, entity.getId());
        assertArrayEquals(
            ENC_PUB_KEY, entity.getPublicEncryptionKey().getRawBytes());
        assertArrayEquals(
            SIGN_PUB_KEY, entity.getPublicSigningKey().getRawBytes());
    }

    /**
     * Test that we can create a PrivateEntity from a private key cert.
     */
    @Test
    public void createPrivateEntityFromCertificate() throws Exception {
        final byte[] PUB_CERT = {
            0, 1, 0, 4, 0, 1, 0, 0, 
            0, 48, 0, 16, -127, 78, 106, 116, 
            -121, -86, 69, -107, -99, 49, -68, -58, 
            39, -49, -28, 78, 0, 32, 0, 2, 
            0, 1, 0, 65, 0, 16, 6, 36, 
            66, -28, 40, 102, 127, -96, -102, 24, 
            81, -41, 32, 109, 17, -28, 0, 82, 
            0, 32, 75, 117, -73, 97, 126, 77, 
            -127, 53, 42, 106, 31, 0, -20, -34, 
            -116, -14, -82, -102, -64, -82, 109, 124, 
            -9, 93, -62, 48, 76, -16, -128, 62, 
            -114, 62, 0, 84, 0, 32, 31, 125, 
            34, -29, 92, 57, -51, 87, 19, 42, 
            56, -113, -45, 65, -79, 11, 82, 20, 
            -72, -122, -53, -43, 11, 85, 14, -59, 
            -63, 72, -102, -78, 48, -99, 0, 83, 
            0, 32, -34, -78, -98, -125, -55, 107, 
            -105, -24, -21, 33, -118, 59, -15, 78, 
            70, -40, -73, 79, -3, 55, -33, -20, 
            93, -97, 3, -125, 112, -73, -127, 118, 
            -50, -78, 0, 85, 0, 64, -100, -97, 
            -80, 59, -100, 79, -25, 13, -58, -107, 
            15, 118, 31, 7, 125, 59, -16, 57, 
            -36, -14, -99, 3, 68, 98, -33, 81, 
            -87, -4, 36, 33, 69, -118, -34, -78, 
            -98, -125, -55, 107, -105, -24, -21, 33, 
            -118, 59, -15, 78, 70, -40, -73, 79, 
            -3, 55, -33, -20, 93, -97, 3, -125, 
            112, -73, -127, 118, -50, -78, 10, 
        };
        final var ENTITY_ID =
            UUID.fromString("062442e4-2866-7fa0-9a18-51d7206d11e4");
        final byte[] ENC_PUB_KEY = {
            75, 117, -73, 97, 126, 77, -127, 53, 
            42, 106, 31, 0, -20, -34, -116, -14, 
            -82, -102, -64, -82, 109, 124, -9, 93, 
            -62, 48, 76, -16, -128, 62, -114, 62, 
        };
        final byte[] SIGN_PUB_KEY = {
            -34, -78, -98, -125, -55, 107, -105, -24, 
            -21, 33, -118, 59, -15, 78, 70, -40, 
            -73, 79, -3, 55, -33, -20, 93, -97, 
            3, -125, 112, -73, -127, 118, -50, -78, 
        };
        final byte[] ENC_PRIV_KEY = {
            31, 125, 34, -29, 92, 57, -51, 87, 
            19, 42, 56, -113, -45, 65, -79, 11, 
            82, 20, -72, -122, -53, -43, 11, 85, 
            14, -59, -63, 72, -102, -78, 48, -99, 
        };
        final byte[] SIGN_PRIV_KEY = {
            -100, -97, -80, 59, -100, 79, -25, 13, 
            -58, -107, 15, 118, 31, 7, 125, 59, 
            -16, 57, -36, -14, -99, 3, 68, 98, 
            -33, 81, -87, -4, 36, 33, 69, -118, 
            -34, -78, -98, -125, -55, 107, -105, -24, 
            -21, 33, -118, 59, -15, 78, 70, -40, 
            -73, 79, -3, 55, -33, -20, 93, -97, 
            3, -125, 112, -73, -127, 118, -50, -78, 
        };

        var cert = Certificate.fromByteArray(PUB_CERT);
        var entity = EntityFactory.createPrivateEntityFromCertificate(cert);

        assertEquals(ENTITY_ID, entity.getId());
        assertArrayEquals(
            ENC_PUB_KEY, entity.getPublicEncryptionKey().getRawBytes());
        assertArrayEquals(
            SIGN_PUB_KEY, entity.getPublicSigningKey().getRawBytes());
        assertArrayEquals(
            ENC_PRIV_KEY, entity.getPrivateEncryptionKey().getRawBytes());
        assertArrayEquals(
            SIGN_PRIV_KEY, entity.getPrivateSigningKey().getRawBytes());
    }

}
