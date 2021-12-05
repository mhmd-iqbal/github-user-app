package com.iqbal.submission3.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.database.UserDao
import com.iqbal.submission3.githubuserapp.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
  private val mUsersDao: UserDao
  private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

  init {
    val db = UserRoomDatabase.getDatabase(application)
    mUsersDao = db.userDao()
  }

  fun getAllFavoriteUser(): LiveData<List<User>> = mUsersDao.getAllFavoriteUser()

  fun addToFavorite(user: User) {
    executorService.execute { mUsersDao.addToFavorite(user) }
  }

  fun deleteFromFavorite(user: User) {
    executorService.execute { mUsersDao.deleteFromFavorite(user) }
  }

}