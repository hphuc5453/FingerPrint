package hphuc.fingerprint.database

class ConfigUtil {
    companion object{
        val isFirstLoginApp: Boolean
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP)
            }

        @JvmStatic
        fun saveIsFirstLoginApp(isFirstLoginApp: Boolean) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            configSaver.save(ConfigSaver.CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP, isFirstLoginApp)
        }

        val fakeToken: String?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_SAVED_FAKE_TOKEN)
            }

        @JvmStatic
        fun saveFakeToken(fakeToken: String?) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            configSaver.save(ConfigSaver.CONFIG_SETTING_SAVED_FAKE_TOKEN, fakeToken)
        }
    }
}