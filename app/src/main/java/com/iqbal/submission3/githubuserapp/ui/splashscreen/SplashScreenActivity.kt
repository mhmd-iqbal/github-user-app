package com.iqbal.submission3.githubuserapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.helper.PreferencesViewModelFactory
import com.iqbal.submission3.githubuserapp.preference.SettingPreferences
import com.iqbal.submission3.githubuserapp.ui.main.MainActivity
import com.iqbal.submission3.githubuserapp.ui.setting.ThemeSettingsViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    val pref = SettingPreferences.getInstance(dataStore)
    val themeViewModel = ViewModelProvider(
      this,
      PreferencesViewModelFactory(pref)
    )[ThemeSettingsViewModel::class.java]

    themeViewModel.getThemeSettings().observe(this,
      { isDarkModeActive: Boolean ->
        if (isDarkModeActive) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
      })

    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }, 2000)
  }
}