package com.velopayments.blockchain.cert;

import com.velopayments.blockchain.crypt.SigningPrivateKey;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateBuilderTest {

    private static byte PRIVATE_KEY_BYTES[] = {
        101, -109, 33, -48, 53, -87, -8, -49,
        53, 55, -47, -47, -126, -3, -18, -8,
        -110, -114, 12, -2, -76, 86, 75, 45,
        -75, 17, 96, 109, -58, -10, 19, -67,
        71, -125, -23, -10, 120, -47, 73, -84,
        -46, 9, 102, -80, -85, -120, -9, -48,
        93, 109, 79, 84, 15, 31, 35, -126,
        -122, 0, 58, -38, 12, 39, -52, 53,
    };
    private static SigningPrivateKey PRIVATE_KEY =
        new SigningPrivateKey(PRIVATE_KEY_BYTES);

    private static byte PARENT_ENCRYPTION_KEY[] = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };
    private static byte PARENT_SIGNING_KEY[] = {
        71, -125, -23, -10, 120, -47, 73, -84,
        -46, 9, 102, -80, -85, -120, -9, -48,
        93, 109, 79, 84, 15, 31, 35, -126,
        -122, 0, 58, -38, 12, 39, -52, 53,
    };

    private static UUID PARENT_UUID =
        UUID.fromString("00010203-0405-0607-0809-0a0b0c0d0e0f");

    /**
     * Test that an empty certificate includes the version, crypto suite, and
     * certificate type.
     */
    @Test
    public void emptyCertificateTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var certMap = p.parse();
        var r = new CertificateReader(p);

        assertNotEquals(null, certMap);
        assertEquals(false, certMap.isEmpty());

        //check field CERTIFICATE_VERSION
        assertEquals(1, r.count(Field.CERTIFICATE_VERSION));
        assertEquals(
            0x00010000, r.getFirst(Field.CERTIFICATE_VERSION).asInt());

        //check field CERTIFICATE_CRYPTO_SUITE
        assertEquals(1, r.count(Field.CERTIFICATE_CRYPTO_SUITE));
        assertEquals(
            0x0001, r.getFirst(Field.CERTIFICATE_CRYPTO_SUITE).asShort());

        //check field CERTIFICATE_TYPE
        assertEquals(1, r.count(Field.CERTIFICATE_TYPE));
        assertEquals(CERT_TYPE, r.getFirst(Field.CERTIFICATE_TYPE).asUUID());
    }

    /**
     * Test that an empty certificate fragment contains just the added field.
     */
    @Test
    public void certificateFragmentTest() throws Exception {
        final int TEST_FIELD = 0x4301;
        final byte TEST_VALUE = 0x12;

        var builder = CertificateBuilder.createCertificateFragmentBuilder();
        builder.addByte(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var certMap = p.parse();
        var r = new CertificateReader(p);

        assertNotEquals(null, certMap);
        assertEquals(false, certMap.isEmpty());

        //check that field CERTIFICATE_VERSION was not emitted
        assertEquals(0, r.count(Field.CERTIFICATE_VERSION));

        //check that field CERTIFICATE_CRYPTO_SUITE was not emitted
        assertEquals(0, r.count(Field.CERTIFICATE_CRYPTO_SUITE));

        //check that field CERTIFICATE_TYPE was not emitted
        assertEquals(0, r.count(Field.CERTIFICATE_TYPE));

        //check that our test field was emitted
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asByte());
    }

    /**
     * Test that an empty certificate fragment throws an IllegalStateException
     * if an attempt is made to emit it.
     */
    @Test
    public void emptyCertificateFragmentFailsEmitTest() throws Exception {
        var builder = CertificateBuilder.createCertificateFragmentBuilder();

        assertThrows(IllegalStateException.class, () -> {
            var outCert = builder.emit();
        });
    }

    /**
     * Test that we can add a byte field to a certificate and parse it.
     */
    @Test
    public void buildByteValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4301;
        final byte TEST_VALUE = 0x12;

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addByte(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asByte());
    }

    /**
     * Test that we can add a short field to a certificate and parse it.
     */
    @Test
    public void buildShortValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4302;
        final short TEST_VALUE = (short)0x8051;

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addShort(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asShort());
    }

    /**
     * Test that we can add an int field to a certificate and parse it.
     */
    @Test
    public void buildIntValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4303;
        final int TEST_VALUE = -6782;

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addInt(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var certMap = p.parse();
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asInt());
    }

    /**
     * Test that we can add a long field to a certificate and parse it.
     */
    @Test
    public void buildLongValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4304;
        final long TEST_VALUE = 1745;

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addLong(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asLong());
    }

    /**
     * Test that we can add a UUID field to a certificate and parse it.
     */
    @Test
    public void buildUUIDValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4305;
        final UUID TEST_VALUE =
            UUID.fromString("b2dd6d91-a30e-425f-a06e-9f1b4bcc2d87");

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addUUID(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asUUID());
    }

    /**
     * Test that we can add a String field to a certificate and parse it.
     */
    @Test
    public void buildStringValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4306;
        final String TEST_VALUE = "This is a test string.";

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addString(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asString());
    }

    /**
     * Test that we can add a Date field to a certificate and parse it.
     */
    @Test
    public void buildDateValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4307;
        //we don't store milliseconds in the certificate
        final Date TEST_VALUE = new Date(
            new Date().toInstant().getEpochSecond() * 1000);

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addDate(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asDate());
    }

    /**
     * Test that we can add a byte array field to a certificate and parse it.
     */
    @Test
    public void buildByteArrayValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final int TEST_FIELD = 0x4308;
        final byte[] TEST_VALUE = {1, 2, 3, 4, 5};

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addByteArray(TEST_FIELD, TEST_VALUE);

        var outCert = builder.emit();
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertArrayEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asByteArray());
    }

    /**
     * Test that we can add a byte field to a certificate, sign it, and parse
     * it.
     */
    @Test
    public void buildAndSignByteValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final UUID ARTIFACT_TYPE =
            UUID.fromString("897d4d04-d5d8-4bf4-b1d1-6884bb362a44");
        final int TEST_FIELD = 0x4301;
        final byte TEST_VALUE = 0x12;
        final long BLOCK_HEIGHT = 77;

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addByte(TEST_FIELD, TEST_VALUE);

        var outCert = builder.sign(PARENT_UUID, PRIVATE_KEY);
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);

        //read the certificate
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asByte());
    }

    /**
     * Test that we can add a Date field to a certificate, sign it, and parse
     * it.
     */
    @Test
    public void buildAndSignDateValueTest() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final UUID ARTIFACT_TYPE =
            UUID.fromString("897d4d04-d5d8-4bf4-b1d1-6884bb362a44");
        final int TEST_FIELD = 0x4307;
        final long BLOCK_HEIGHT = 66;
        final Date TEST_VALUE = new Date(
            new Date().toInstant().getEpochSecond() * 1000);

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addDate(TEST_FIELD, TEST_VALUE);

        var outCert = builder.sign(PARENT_UUID, PRIVATE_KEY);
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);

        //read the certificate
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(TEST_VALUE, r.getFirst(TEST_FIELD).asDate());
    }

    /**
     * We do not support ZonedDateTime for non-custom fields.
     */
    public void buildZonedDateTimeFailsOnReservedDates() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final ZoneId TEST_ZONE = ZoneId.of("Europe/Paris");
        ZonedDateTime NOW_VALUE =
            ZonedDateTime.now(TEST_ZONE);

        final var TEST_VALUE = NOW_VALUE.minusNanos(NOW_VALUE.getNano());

        var builder =
            CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        /* this blows up with an exception. */
        assertThrows(IllegalArgumentException.class, () -> {
            builder.addZonedDateTime(Field.CERTIFICATE_VALID_FROM, TEST_VALUE);
        });
    }

    /**
     * Test that we cannot turn a Date into a ZonedDateTime.
     */
    @Test
    public void buildDateAndParseZonedDateTimeFails() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final UUID ARTIFACT_TYPE =
            UUID.fromString("897d4d04-d5d8-4bf4-b1d1-6884bb362a44");
        final int TEST_FIELD = 0x4307;
        final long BLOCK_HEIGHT = 66;
        final ZoneId TEST_ZONE = ZoneId.of("Europe/Paris");
        final Date TEST_VALUE = new Date(
            new Date().toInstant().getEpochSecond() * 1000);

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addDate(TEST_FIELD, TEST_VALUE);

        var outCert = builder.sign(PARENT_UUID, PRIVATE_KEY);
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);

        //read the certificate
        var r = new CertificateReader(p);

        //This fails with an exception.
        assertThrows(FieldConversionException.class, () -> {
            var boomVal = r.getFirst(TEST_FIELD).asZonedDateTime();
        });
    }

    /**
     * Test that we can add a ZonedDateTime field to a certificate and parse it.
     */
    @Test
    public void buildAndParseZonedDateTime() throws Exception {
        final UUID CERT_TYPE =
            UUID.fromString("23b11e7f-4260-48b3-993e-6baf1df0e8dd");
        final UUID ARTIFACT_TYPE =
            UUID.fromString("897d4d04-d5d8-4bf4-b1d1-6884bb362a44");
        final int TEST_FIELD = 0x4307;
        final long BLOCK_HEIGHT = 66;
        final ZoneId TEST_ZONE = ZoneId.of("Europe/Paris");
        ZonedDateTime TEST_VALUE = ZonedDateTime.now(TEST_ZONE);

        TEST_VALUE = TEST_VALUE.minusNanos(TEST_VALUE.getNano());

        var builder = CertificateBuilder.createCertificateBuilder(CERT_TYPE);
        builder.addZonedDateTime(TEST_FIELD, TEST_VALUE);

        var outCert = builder.sign(PARENT_UUID, PRIVATE_KEY);
        assertNotEquals(null, outCert);

        var p = new CertificateParser(outCert);

        //read the certificate
        var r = new CertificateReader(p);

        //check field TEST_FIELD
        assertEquals(
            TEST_VALUE,
            r.getFirst(TEST_FIELD)
                .asZonedDateTime()
                .withZoneSameInstant(TEST_ZONE));
    }
}
