package hphuc.fingerprint

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import hphuc.fingerprint.AppConstants.Companion.CIPHER_TEXT_WRAPPER
import hphuc.fingerprint.AppConstants.Companion.SHARED_PREFS_FILENAME
import hphuc.fingerprint.AppConstants.Companion.secretKeyName
import hphuc.fingerprint.database.ConfigUtil

class MainActivity : AppCompatActivity() {

    lateinit var ivFinger: AppCompatImageView
    private var cipherTextWrapper: CipherTextWrapper? = null
    private lateinit var biometricPrompt: BiometricPrompt
    private var cryptographyManager = CryptographyManagerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fakeTokenAndInitBiometric()

//        cipherTextWrapper = cryptographyManager.getCipherTextWrapperFromSharedPrefs(
//            applicationContext,
//            SHARED_PREFS_FILENAME,
//            Context.MODE_PRIVATE,
//            CIPHER_TEXT_WRAPPER
//        )

        ivFinger = findViewById(R.id.fingerPrint)
        ivFinger.setOnClickListener {
            if (checkAuthenticate()) {
//                if (cipherTextWrapper != null) {
                    showBiometricPromptForDecryption()
//                }
            }
        }
    }

    private fun fakeTokenAndInitBiometric() {
        ConfigUtil.saveFakeToken(null)
        val fakeToken = java.util.UUID.randomUUID().toString()
        ConfigUtil.saveFakeToken(fakeToken)
    }

    private fun showBiometricPromptForDecryption() {
        cryptographyManager = CryptographyManagerImpl()
        val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
        val biometricPrompt =
            BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken)
        val promptInfo = BiometricPromptUtils.createPromptInfo(
            "title",
            "sub title",
            "description",
            "close text"
        )
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
//        authResult.cryptoObject?.cipher?.apply {
//                val encryptedServerTokenWrapper = cryptographyManager.encryptData(ConfigUtil.fakeToken!!, this)
//                cryptographyManager.persistCipherTextWrapperToSharedPrefs(
//                    encryptedServerTokenWrapper,
//                    applicationContext,
//                    SHARED_PREFS_FILENAME,
//                    Context.MODE_PRIVATE,
//                    CIPHERTEXT_WRAPPER
//                )
//        }
//        finish()
    }

    private fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        cipherTextWrapper.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
//                val plaintext =
//                    cryptographyManager.decryptData(textWrapper.cipherText, it)
//                SampleAppUser.fakeToken = plaintext
                // Now that you have the token, you can query server for everything else
                // the only reason we call this fakeToken is because we didn't really get it from
                // the server. In your case, you will have gotten it from the server the first time
                // and therefore, it's a real token.

//                updateApp(getString(R.string.already_signedin))
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun checkAuthenticate(): Boolean {
        val canAuthenticate = BiometricManager.from(applicationContext).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        return canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
    }
}