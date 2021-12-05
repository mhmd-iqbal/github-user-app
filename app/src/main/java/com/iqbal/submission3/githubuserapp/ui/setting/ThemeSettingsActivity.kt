package com.iqbal.submission3.githubuserapp.ui.setting

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.databinding.ActivityThemeSettingsBinding
import com.iqbal.submission3.githubuserapp.helper.PreferencesViewModelFactory
import com.iqbal.submission3.githubuserapp.preference.SettingPreferences

class ThemeSettingsActivity : AppCompatActivity(), View.OnClickListener {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
  private lateinit var binding: ActivityThemeSettingsBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityThemeSettingsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = getString(R.string.setting)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val switchButton: SwitchCompat = binding.themeSwitch

    val pref = SettingPreferences.getInstance(dataStore)
    val themeViewModel = ViewModelProvider(
      this,
      PreferencesViewModelFactory(pref)
    )[ThemeSettingsViewModel::class.java]
    themeViewModel.getThemeSettings().observe(this,
      { isDarkModeActive: Boolean ->
        if (isDarkModeActive) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
          switchButton.isChecked = true
          switchButton.setText(R.string.turn_dark_mode_on)
        } else {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
          switchButton.isChecked = false
          switchButton.setText(R.string.turn_dark_mode_off)
        }
      })

    switchButton.setOnCheckedChangeListener { _, isChecked ->
      themeViewModel.saveThemeSetting(isChecked)
    }

    binding.cvInstagram.setOnClickListener(this)
    binding.cvGithub.setOnClickListener(this)
    binding.cvWhatsapp.setOnClickListener(this)

  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.cv_github -> {
        val uri = Uri.parse("https://www.github.com/mhmd-iqbal")
        val githubIntent = Intent(Intent.ACTION_VIEW, uri)
        githubIntent.setPackage("com.github.android")
        try {
          startActivity(githubIntent)
        } catch (e: ActivityNotFoundException) {
          startActivity(
            Intent(
              Intent.ACTION_VIEW,
              Uri.parse("https://www.github.com/mhmd-iqbal")
            )
          )
        }
      }

      R.id.cv_instagram -> {
        val uri = Uri.parse("https://www.instagram.com/mhmd.iqbaale_")
        val instagramIntent = Intent(Intent.ACTION_VIEW, uri)
        instagramIntent.setPackage("com.instagram.android")
        try {
          startActivity(instagramIntent)
        } catch (e: ActivityNotFoundException) {
          startActivity(
            Intent(
              Intent.ACTION_VIEW,
              Uri.parse("https://www.instagram.com/mhmd.iqbaale_")
            )
          )
        }
      }

      R.id.cv_whatsapp -> {
        val uri = Uri.parse("https://wa.me/62895323755721")
        val whatsappIntent = Intent(Intent.ACTION_VIEW, uri)
        whatsappIntent.setPackage("com.whatsapp")
        try {
          startActivity(whatsappIntent)
        } catch (e: ActivityNotFoundException) {
          startActivity(
            Intent(
              Intent.ACTION_VIEW,
              Uri.parse("https://wa.me/62895323755721")
            )
          )
        }
      }
    }
  }

}