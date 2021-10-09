package com.velopayments.blockchain.util;

import com.velopayments.blockchain.crypt.EncryptionPrivateKey;
import com.velopayments.blockchain.crypt.SigningPrivateKey;

public interface PrivateEntity extends Entity {

    /**
     * Get the private encryption key associated with the given PrivateEntity.
     */
    EncryptionPrivateKey getPrivateEncryptionKey();

    /**
     * Get the private signing key associated with the given PrivateEntity.
     */
    SigningPrivateKey getPrivateSigningKey();
}
