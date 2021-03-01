package hphuc.fingerprint

import android.security.keystore.KeyProperties

class AppConstants {
    companion object{
        const val KEY_SIZE = 256
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        const val SHARED_PREFS_FILENAME = "biometric_prefs"
        const val CIPHER_TEXT_WRAPPER = "cipher_text_wrapper"
    }
}