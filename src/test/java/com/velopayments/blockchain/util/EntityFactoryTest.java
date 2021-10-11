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
        final byte[] PRIV_CERT = {
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

        var cert = Certificate.fromByteArray(PRIV_CERT);
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


    /**
     * Test that we can create a PrivateEntity from an encrypted private key
     * cert.
     */
    @Test
    public void createPrivateEntityFromEncryptedCertificate() throws Exception {
        final byte[] PRIV_CERT = {
            69, 78, 67, 0, 0, -61, 80, 77,
            102, 77, -51, 15, -127, 6, 111, 117,
            -49, -3, -16, -108, 43, -92, -105, 109,
            40, 65, -56, 59, -118, 8, -43, 77,
            -96, -10, -108, -46, -85, 119, -48, -63,
            -50, -117, -42, -95, -34, 118, 31, 31,
            -69, 48, 118, -92, -15, -114, -65, -92,
            -109, 103, -114, 125, 107, -41, 105, 96,
            -127, 72, 18, -101, -26, -104, 25, -37,
            -125, -64, 109, -16, -47, -119, -75, -75,
            69, 62, 23, -38, 89, -69, -61, 4,
            -71, 68, -49, -106, 14, -113, 27, 57,
            -43, 7, -90, -41, 86, 21, 80, -106,
            -55, -128, -122, -51, -23, 87, -71, -108,
            121, 52, -91, 115, -95, -14, 68, -26,
            39, -123, 73, 92, -108, -123, -66, -110,
            -64, -25, 1, 18, -5, 52, 120, -126,
            85, -36, 9, -6, 65, -75, -58, -36,
            -27, -30, 100, -72, -89, -99, 18, -87,
            85, 113, -52, -101, -89, -100, 41, 21,
            -81, 108, 10, -93, -45, 33, 36, -91,
            4, -59, 72, -84, -17, -117, 109, -17,
            -118, -37, -13, -15, 68, -120, 2, 75,
            -116, 93, -25, -81, -33, -26, -106, -107,
            33, -90, 116, -49, -32, -108, -126, -18,
            -116, -27, 32, 106, 41, 87, -11, 93,
            122, 16, -59, 35, -40, 95, -117, 82,
            -67, 28, 9, -12, -108, -2, 26, -17,
            68, 29, 70, 2, 111, 76, -27, -73,
            -11, -82, -60, 91, 101, -30, 86, -35,
            12, -108, -35, -10, 6, -103, 7, 33,
            -83, -80, -10, -46, -61, 74, 112, 45,
            55, -56, 113, -33, 5, 47, -105, -101,
            39, 54, -45, 7, -55, -53, 27, -21,
            -89, -75, -109, -105, -99, 16, 101, 16,
            -121, 101, 24, 78, -17, 34, -75, 39,
            72, 19, 112, -125, -101, -10, -122, -1,
            79, 42, -90, -46, 35, 41, -78, -82,
            93, 113, -28, 116, 70, -76, -32, -34,
            30, 108, 111, 20, -49, 52, -15, 72,
            85, -93, 46, 107, 120, 15, 30, -125,
            72, 80, 111, 114, -108, -114, 127, -31,
            -86, -98, -100, -33, -13,
        };
        final var ENTITY_ID =
            UUID.fromString("7bb9d0b3-c4ad-3d30-dd59-6637a2663fac");
        final byte[] ENC_PUB_KEY = {
            -6, -34, -57, -115, -53, 62, -7, 20,
            100, 116, -2, -113, 8, -86, 79, -111,
            84, 77, -7, -12, 126, -12, -117, -92,
            -39, 26, -74, 92, 33, -87, -36, 104,
        };
        final byte[] SIGN_PUB_KEY = {
            -28, -40, -74, 107, 58, 54, 102, 115,
            29, 92, -124, 122, 40, -67, -30, -56,
            40, -7, -2, 24, 100, -53, -1, -80,
            -33, 32, 81, -96, -4, -76, 9, 122,
        };
        final byte[] ENC_PRIV_KEY = {
            127, -110, -73, -76, -85, -29, -53, 70,
            97, -83, -92, 12, 100, 2, 8, -97,
            55, 46, -88, 92, -85, -114, -14, 106,
            -111, 70, 92, 14, 80, -50, 41, -93,
        };
        final byte[] SIGN_PRIV_KEY = {
            -50, -44, -37, 117, -123, -121, -4, 60,
            23, 40, -61, 42, -122, -18, -66, -99,
            121, -59, -73, 125, 102, 17, 49, -61,
            -55, -18, -93, -16, -53, -84, -58, 5,
            -28, -40, -74, 107, 58, 54, 102, 115,
            29, 92, -124, 122, 40, -67, -30, -56,
            40, -7, -2, 24, 100, -53, -1, -80,
            -33, 32, 81, -96, -4, -76, 9, 122,
        };
        final String PASSPHRASE = "This is a test.";
        final byte[] PASSPHRASE_BYTES = PASSPHRASE.getBytes("UTF-8");

        var entity =
            EntityFactory.createPrivateEntityFromEncryptedCertificate(
                PRIV_CERT, PASSPHRASE_BYTES);

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
