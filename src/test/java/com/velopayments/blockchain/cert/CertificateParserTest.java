package com.velopayments.blockchain.cert;

import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateParserTest {

    /**
     * Test that an empty certificate throws an InvalidArgumentException.
     */
    @Test
    public void emptyCertificateTest() throws Exception {
        byte TEST_CERT[] = {
        };

        //create a parser for this cert
        CertificateParser p =
            new CertificateParser(
                Certificate.fromByteArray(TEST_CERT));

        //parse the raw certificate
        assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, List<byte[]>> certMap = p.parse();
        });
    }

    /**
     * Test that a truncated certificate throws an IllegalArgumentException.
     */
    @Test
    public void truncatedCertificateTest() throws Exception {
        byte TEST_CERT[] = {
            //field 0x0001 is truncated
            0, 1, 0, 4, -1, -1
        };

        //create a parser for this cert
        CertificateParser p =
            new CertificateParser(
                Certificate.fromByteArray(TEST_CERT));

        //parse the raw certificate
        assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, List<byte[]>> certMap = p.parse();
        });
    }

    /**
     * Test that we can parse a normal certificate.
     */
    @Test
    public void normalCertificateTest() throws Exception {
        byte TEST_CERT[] = {
            //field 0x0001 is 0x01020304
            0, 1, 0, 4, 1, 2, 3, 4,
            //field 0x7002 is 0x01
            112, 2, 0, 1, 1,
            //field 0x0001 is 0xFFFFFFFF
            0, 1, 0, 4, -1, -1, -1, -1,
            //field 0x7007 is 0x13
            112, 7, 0, 1, 19,
            //field 0x7000 is 0x56
            112, 0, 0, 1, 86,
            //field 0x0001 is 0x77777777
            0, 1, 0, 4, 119, 119, 119, 119,
        };

        //create a parser for this cert
        CertificateParser p =
            new CertificateParser(
                Certificate.fromByteArray(TEST_CERT));

        //read the raw certificate
        CertificateReader r = new CertificateReader(p);
        Map<Integer, List<byte[]>> certMap = p.parse();

        // check that we can get the same certificate back from the parser and
        // reader
        assertArrayEquals(p.getCertificate().toByteArray(), TEST_CERT);
        assertArrayEquals(r.getCertificate().toByteArray(), TEST_CERT);

        //check that we parsed each field value
        assertNotEquals(null, certMap);
        assertEquals(false, certMap.isEmpty());

        //check field 0x7002
        assertEquals(1, r.count(0x7002));
        assertEquals((byte)0x01, r.getFirst(0x7002).asByte());

        //check field 0x7007
        assertEquals(1, r.count(0x7007));
        assertEquals((byte)0x13, r.getFirst(0x7007).asByte());

        //check field 0x7000
        assertEquals(1, r.count(0x7000));
        assertEquals((byte)0x56, r.getFirst(0x7000).asByte());

        //There should be three 0x0001 fields
        assertEquals(3, r.count(0x0001));

        assertEquals(0x01020304, r.get(0x0001, 0).asInt());
        assertEquals(-1, r.get(0x0001, 1).asInt());
        assertEquals(0x77777777, r.get(0x0001, 2).asInt());
    }
}
