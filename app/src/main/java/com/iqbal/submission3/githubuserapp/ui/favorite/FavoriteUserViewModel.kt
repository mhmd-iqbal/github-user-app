package com.iqbal.submission3.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
  private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

  fun getAllFavoriteUser(): LiveData<List<User>> {
    return mFavoriteUserRepository.getAllFavoriteUser()
  }
}