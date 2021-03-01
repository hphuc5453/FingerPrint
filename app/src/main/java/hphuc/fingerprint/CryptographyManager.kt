package hphuc.fingerprint

import javax.crypto.Cipher

interface CryptographyManager {
    fun getInitializedCipherForEncryption(keyName: String): Cipher
}

