package com.iqbal.submission3.githubuserapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iqbal.submission3.githubuserapp.preference.SettingPreferences
import com.iqbal.submission3.githubuserapp.ui.setting.ThemeSettingsViewModel

class PreferencesViewModelFactory(private val pref: SettingPreferences) :
  ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
      return ThemeSettingsViewModel(pref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }
}