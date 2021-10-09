package com.velopayments.blockchain.util;

import com.velopayments.blockchain.crypt.EncryptionPublicKey;
import com.velopayments.blockchain.crypt.SigningPublicKey;
import java.util.UUID;

public interface Entity {

    /**
     * Get the UUID associated with the given Entity.
     */
    UUID getId();

    /**
     * Get the public encryption key associated with the given Entity.
     */
    EncryptionPublicKey getPublicEncryptionKey();

    /**
     * Get the public signing key associated with the given Entity.
     */
    SigningPublicKey getPublicSigningKey();
}
