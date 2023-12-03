package ir.millennium.sampleprojectcompose.core.ui

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.millennium.sampleprojectcompose.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var auxiliaryFunctionsManager: AuxiliaryFunctionsManager

    override fun attachBaseContext(newBase: Context) {
        val newConfiguration = Configuration(newBase.resources?.configuration)
        val locale = Locale(SharedPreferencesManager(newBase).getLanguageApp())
        newConfiguration.fontScale = 1.0f
        Locale.setDefault(locale)
        newConfiguration.setLocale(locale)
        newConfiguration.setLayoutDirection(locale)
        applyOverrideConfiguration(newConfiguration)
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferencesManager.getLanguageApp() == "en")
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        else
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

}
