package hphuc.fingerprint

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

object BiometricPromptUtils {
    fun createBiometricPrompt(
        activity: AppCompatActivity,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                processSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    fun createPromptInfo(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
//            setTitle(activity.getString(R.string.prompt_info_title))
//            setSubtitle(activity.getString(R.string.prompt_info_subtitle))
//            setDescription(activity.getString(R.string.prompt_info_description))
            setConfirmationRequired(false)
//            setNegativeButtonText(activity.getString(R.string.prompt_info_use_app_password))
        }.build()
}