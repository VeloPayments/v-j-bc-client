package com.velopayments.blockchain.cert;

import com.velopayments.blockchain.init.Initializer;
import java.util.*;
import java.util.function.BiFunction;

public class CertificateParser {

    private byte[] certificate;
    private int size;
    private int rawSize;

    /**
     * Create a CertificateParser instance to parse the given certificate.
     *
     * @param cert The certificate to parse.
     */
    public CertificateParser(Certificate cert) {
        certificate = cert.toByteArray();
        rawSize = size = certificate.length;
    }

    /**
     * Append a field to a certificate field list for a certificate map.
     *
     * @param appendValue The value to append.
     * @return the updated list.
     */
    private static BiFunction<Integer, List<byte[]>, List<byte[]>>
    appendField(List<byte[]> appendValue) {
        return (k, v) -> {
            if (v != null) {
                //if set, append this value
                v.addAll(appendValue);
                return v;
            } else {
                //if not set, return this value
                return appendValue;
            }
        };
    }

    /**
     * Parse a certificate, returning a Map of key-value pairs.
     *
     * @return the fields of a certificate, as key-value pairs.
     */
    public Map<Integer, List<byte[]>> parse() {
        List<AbstractMap.SimpleEntry<Integer, byte[]>> certFields =
            parseNative(Initializer.getInstance(), certificate, size);

        HashMap<Integer, List<byte[]>> map =
            new HashMap<>();

        for (AbstractMap.SimpleEntry<Integer, byte[]> v : certFields) {
            List<byte[]> lst = new LinkedList<>();
            lst.add(v.getValue());

            map.compute(v.getKey(), appendField(lst));
        }

        return map;
    }

    /**
     * Get the underlying certificate being parsed.
     *
     * @return the underlying certificate being parsed.
     */
    public Certificate getCertificate() {
        return Certificate.fromByteArray(certificate);
    }

    /**
     * Internal parse method for parsing a certificate.
     *
     * @param nativeInst the native instance pointer.
     * @param cert     The raw certificate to parse.
     * @param certSize The size override to use for finding the end of
     *                 the certificate.
     * @return the fields of the certificate, as key-value pairs.
     */
    private native List<AbstractMap.SimpleEntry<Integer, byte[]>>
    parseNative(long nativeInst, byte[] cert, int certSize);
}
