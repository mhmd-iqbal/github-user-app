package com.iqbal.submission3.githubuserapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iqbal.submission3.githubuserapp.preference.SettingPreferences
import kotlinx.coroutines.launch

class ThemeSettingsViewModel(private val pref: SettingPreferences) : ViewModel() {
  fun getThemeSettings(): LiveData<Boolean> {
    return pref.getThemeSetting().asLiveData()
  }

  fun saveThemeSetting(isDarkModeActive: Boolean) {
    viewModelScope.launch {
      pref.saveThemeSetting(isDarkModeActive)
    }
  }
}