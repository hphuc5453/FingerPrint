package hphuc.fingerprint

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private var cryptographyManager = CryptographyManagerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fakeTokenAndInitBiometric()

        ivFinger = findViewById(R.id.fingerPrint)
        ivFinger.setOnClickListener {
            if (checkAuthenticate()) {
                if (ConfigUtil.fakeToken != null) {
                    showBiometricPromptForDecryption()
                }
            }
        }
    }

    private fun handleAfterLoginWithFingerPrint(){
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
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
            BiometricPromptUtils.createBiometricPrompt(this, ::handleAfterLoginWithFingerPrint)
        val promptInfo = BiometricPromptUtils.createPromptInfo(
            "title",
            "sub title",
            "description",
            "close text"
        )
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    @SuppressLint("WrongConstant")
    private fun checkAuthenticate(): Boolean {
        val canAuthenticate = BiometricManager.from(applicationContext).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        return canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
    }
}