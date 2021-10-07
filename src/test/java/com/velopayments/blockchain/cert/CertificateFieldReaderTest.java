package com.velopayments.blockchain.cert;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateFieldReaderTest {

    /**
     * Test that we can read a byte from an array containing a byte.
     */
    @Test
    public void asByteTest() throws Exception {
        final byte[] input = {88};

        assertEquals(input[0], new CertificateFieldReader(input).asByte());
    }

    /**
     * Reading a byte from a buffer that is too small will raise an exception.
     */
    @Test
    public void asByteSmallBufferException() throws Exception {
        final byte[] input = {};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asByte();
        });
    }

    /**
     * Reading a byte from a buffer that is too large will raise an exception.
     */
    @Test
    public void asByteLargeBufferException() throws Exception {
        final byte[] input = {1, 2};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asByte();
        });
    }

    /**
     * Test that we can read a short from an appropriately sized array.
     */
    @Test
    public void asShortTest() throws Exception {
        final short TEST_VALUE = 0x1234;
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort(TEST_VALUE);

        assertEquals(
            TEST_VALUE, new CertificateFieldReader(buf.array()).asShort());
    }

    /**
     * Reading a short from a buffer that is too small will raise an exception.
     */
    @Test
    public void asShortSmallBufferException() throws Exception {
        final byte[] input = {1};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asShort();
        });
    }

    /**
     * Reading a short from a buffer that is too large will raise an exception.
     */
    @Test
    public void asShortLargeBufferException() throws Exception {
        final byte[] input = {1, 2, 3};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asShort();
        });
    }

    /**
     * Test that we can read an int from an appropriately sized array.
     */
    @Test
    public void asIntTest() throws Exception {
        final int TEST_VALUE = -15;
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(TEST_VALUE);

        assertEquals(
            TEST_VALUE, new CertificateFieldReader(buf.array()).asInt());
    }

    /**
     * Reading an int from a buffer that is too small will raise an exception.
     */
    @Test
    public void asIntSmallBufferException() throws Exception {
        final byte[] input = {1, 2, 3};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asInt();
        });
    }

    /**
     * Reading an int from a buffer that is too large will raise an exception.
     */
    @Test
    public void asIntLargeBufferException() throws Exception {
        final byte[] input = {1, 2, 3, 4, 5};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asInt();
        });
    }

    /**
     * Test that we can read a long from an appropriately sized array.
     */
    @Test
    public void asLongTest() throws Exception {
        final long TEST_VALUE = 8773;
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(TEST_VALUE);

        assertEquals(
            TEST_VALUE, new CertificateFieldReader(buf.array()).asLong());
    }

    /**
     * Reading a long from a buffer that is too small will raise an exception.
     */
    @Test
    public void asLongSmallBufferException() throws Exception {
        final byte[] input = {1, 2, 3, 4, 5, 6, 7};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asLong();
        });
    }

    /**
     * Reading a long from a buffer that is too large will raise an exception.
     */
    @Test
    public void asLongLargeBufferException() throws Exception {
        byte[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asLong();
        });
    }

    /**
     * Test that we can read a UUID from an appropriately sized array.
     */
    @Test
    public void asUUIDTest() throws Exception {
        final UUID TEST_VALUE =
            UUID.fromString("cd319764-5586-43d9-a17a-76b12e108fa7");
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.putLong(TEST_VALUE.getMostSignificantBits());
        buf.putLong(TEST_VALUE.getLeastSignificantBits());

        assertEquals(
            TEST_VALUE, new CertificateFieldReader(buf.array()).asUUID());
    }

    /**
     * Reading a UUID from a buffer that is too small will raise an exception.
     */
    @Test
    public void asUUIDSmallBufferException() throws Exception {
        final byte[] input = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asUUID();
        });
    }

    /**
     * Reading a UUID from a buffer that is too large will raise an exception.
     */
    @Test
    public void asUUIDLargeBufferException() throws Exception {
        final byte[] input = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asUUID();
        });
    }

    /**
     * Test that we can read a String from an array.
     */
    @Test
    public void asStringTest() throws Exception {
        final byte[] TEST_VALUE = {65, 66, 67, 68, 69, 70};

        assertEquals(
            "ABCDEF", new CertificateFieldReader(TEST_VALUE).asString());
    }

    /**
     * Reading an empty array returns an empty String.
     */
    @Test
    public void asStringEmptyBufferTest() throws Exception {
        final byte[] input = {};

        assertEquals("", new CertificateFieldReader(input).asString());
    }

    /**
     * Reading a String with an invalid mapping throws an exception.
     */
    @Test
    public void asStringInvalidSequenceExceptionTest() throws Exception {
        final byte[] input = {(byte) 0xc3, (byte) 0x28};

        assertThrows(FieldConversionException.class, () -> {
            new CertificateFieldReader(input).asString();
        });
    }

    /**
     * Test that asByteArray just returns the byte array.
     */
    @Test
    public void asByteArrayTest() throws Exception {
        final byte[] TEST_VALUE = {2, 3, 4};

        assertEquals(
            TEST_VALUE, new CertificateFieldReader(TEST_VALUE).asByteArray());
    }
}
