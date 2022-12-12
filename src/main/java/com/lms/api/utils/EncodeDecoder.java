package com.lms.api.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class EncodeDecoder {

    public static String encodeBase64(String str){

        Base64.Encoder encoder = Base64.getMimeEncoder();
        String eStr = encoder.encodeToString(str.getBytes());

        return eStr;
    }

    public static  String decodeBase64(String eStr){

        Base64.Decoder decoder = Base64.getMimeDecoder();
        // Decoding MIME encoded message
        String dStr = new String(decoder.decode(eStr));

        return dStr;
    }


    public final static UUID namespace = UUID.randomUUID();

    private final static int version = 5; // UUIDv5 SHA1

    private final static String algorithm = "SHA-1"; // UUIDv5

    public static String getHashUuid(String data) {

        final byte[] hash;
        final MessageDigest hasher;

        try {
            // Instantiate a message digest for the chosen algorithm
            hasher = MessageDigest.getInstance(algorithm);

            // Insert name space if NOT NULL
            if (namespace != null) {
                hasher.update(toBytes(namespace.getMostSignificantBits()));
                hasher.update(toBytes(namespace.getLeastSignificantBits()));
            }

            // Generate the hash
            hash = hasher.digest(data.getBytes(StandardCharsets.UTF_8));

            // Split the hash into two parts: MSB and LSB
            long msb = toNumber(hash, 0, 8); // first 8 bytes for MSB
            long lsb = toNumber(hash, 8, 16); // last 8 bytes for LSB

            // Apply version and variant bits (required for RFC-4122 compliance)
            msb = (msb & 0xffffffffffff0fffL) | (version & 0x0f) << 12; // apply version bits
            lsb = (lsb & 0x3fffffffffffffffL) | 0x8000000000000000L; // apply variant bits

            // Return the UUID
            UUID generatedUUID = new UUID(msb, lsb);
            return generatedUUID.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Message digest algorithm not supported.");
        }
    }

    private static byte[] toBytes(final long number) {
        return new byte[] { (byte) (number >>> 56), (byte) (number >>> 48), (byte) (number >>> 40),
                (byte) (number >>> 32), (byte) (number >>> 24), (byte) (number >>> 16), (byte) (number >>> 8),
                (byte) (number) };
    }

    private static long toNumber(final byte[] bytes, final int start, final int length) {
        long result = 0;
        for (int i = start; i < length; i++) {
            result = (result << 8) | (bytes[i] & 0xff);
        }
        return result;
    }
}
