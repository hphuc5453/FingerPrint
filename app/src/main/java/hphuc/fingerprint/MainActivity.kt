package hphuc.fingerprint

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import hphuc.fingerprint.AppConstants.Companion.CIPHER_TEXT_WRAPPER
import hphuc.fingerprint.AppConstants.Companion.SHARED_PREFS_FILENAME

class MainActivity : AppCompatActivity() {

    lateinit var ivFinger: AppCompatImageView
    private var cipherTextWrapper: CipherTextWrapper? = null
    private lateinit var biometricPrompt: BiometricPrompt
    private val cryptographyManager = CryptographyManagerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cipherTextWrapper = cryptographyManager.getCipherTextWrapperFromSharedPrefs(
            applicationContext,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHER_TEXT_WRAPPER
        )

        ivFinger = findViewById(R.id.fingerPrint)
        ivFinger.setOnClickListener {
            if (checkAuthenticate()) {
                if(cipherTextWrapper != null){
                    showBiometricPromptForDecryption()
                }
            }
        }
    }

    private fun showBiometricPromptForDecryption() {
        cipherTextWrapper.let { textWrapper ->
            val secretKeyName = ""
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                secretKeyName, textWrapper!!.initializationVector
            )
            biometricPrompt = BiometricPromptUtils.createBiometricPrompt(
                this,
                ::decryptServerTokenFromStorage
            )
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(
                promptInfo,
                BiometricPrompt.CryptoObject(cipher)
            )
        }
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