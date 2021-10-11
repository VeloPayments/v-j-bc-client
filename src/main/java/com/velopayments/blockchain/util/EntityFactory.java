package com.velopayments.blockchain.util;

import com.velopayments.blockchain.cert.*;
import com.velopayments.blockchain.crypt.*;
import com.velopayments.blockchain.init.Initializer;
import java.util.UUID;

public final class EntityFactory {

    /**
     * EntityFactory is a static method class and cannot be instantiated.
     */
    private EntityFactory() {
        throw new IllegalStateException("Cannot be instantiated.");
    }

    /**
     * Create an Entity from a Certificate.
     *
     * @param cert      The certificate from which this Entity is created.
     */
    public static Entity createEntityFromCertificate(Certificate cert) {

        CertificateParser parser = new CertificateParser(cert);
        CertificateReader reader = new CertificateReader(parser);

        final var encryptionPublicKey =
            new EncryptionPublicKey(
                reader.getFirst(Field.PUBLIC_ENCRYPTION_KEY).asByteArray());
        final var signingPublicKey =
            new SigningPublicKey(
                reader.getFirst(Field.PUBLIC_SIGNING_KEY).asByteArray());
        final var artifactId = reader.getFirst(Field.ARTIFACT_ID).asUUID();

        return new Entity() {
            public UUID getId() {
                return artifactId;
            }

            public EncryptionPublicKey getPublicEncryptionKey() {
                return encryptionPublicKey;
            }

            public SigningPublicKey getPublicSigningKey() {
                return signingPublicKey;
            }
        };
    }

    /**
     * Create a PrivateEntity from a Certificate.
     *
     * @param cert      The certificate from which this PrivateEntity is
     *                  created.
     */
    public static PrivateEntity createPrivateEntityFromCertificate(
            Certificate cert) {

        CertificateParser parser = new CertificateParser(cert);
        CertificateReader reader = new CertificateReader(parser);

        final var encryptionKeyPair =
            new EncryptionKeyPair(
                new EncryptionPublicKey(
                    reader.getFirst(
                        Field.PUBLIC_ENCRYPTION_KEY).asByteArray()),
                new EncryptionPrivateKey(
                    reader.getFirst(
                        Field.PRIVATE_ENCRYPTION_KEY).asByteArray()));
        final var signingKeyPair =
            new SigningKeyPair(
                new SigningPublicKey(
                    reader.getFirst(Field.PUBLIC_SIGNING_KEY).asByteArray()),
                new SigningPrivateKey(
                    reader.getFirst(Field.PRIVATE_SIGNING_KEY).asByteArray()));
        final var artifactId = reader.getFirst(Field.ARTIFACT_ID).asUUID();

        return new PrivateEntity() {
            public UUID getId() {
                return artifactId;
            }

            public EncryptionPublicKey getPublicEncryptionKey() {
                return encryptionKeyPair.getPublicKey();
            }

            public SigningPublicKey getPublicSigningKey() {
                return signingKeyPair.getPublicKey();
            }

            public EncryptionPrivateKey getPrivateEncryptionKey() {
                return encryptionKeyPair.getPrivateKey();
            }

            public SigningPrivateKey getPrivateSigningKey() {
                return signingKeyPair.getPrivateKey();
            }
        };
    }

    /**
     * Create a PrivateEntity from an encrypted Certificate.
     *
     * @param encCert    The encrypted certificate from which this PrivateEntity
     *                   is created.
     * @param passphrase The passphrase used to encrypt this certificate.
     */
    public static PrivateEntity createPrivateEntityFromEncryptedCertificate(
        byte[] encCert, byte[] passphrase) {

        Certificate cert =
            decryptCertificate(
                Initializer.getInstance(), encCert, passphrase);

        return createPrivateEntityFromCertificate(cert);
    }

    /**
     * Decrypt a Certificate using the given passphrase.
     * @param nativeInst    the native instance pointer.
     * @param encCert       the encrypted certificate to decrypt.
     * @param passphrase    the passphrase used to encrypt this certificate.
     *
     * @return the decrypted Certificate.
     */
    private static native Certificate decryptCertificate(
        long nativeInst, byte[] encCert, byte[] passphrase);
}
