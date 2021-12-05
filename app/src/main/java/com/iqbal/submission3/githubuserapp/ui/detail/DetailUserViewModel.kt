package com.iqbal.submission3.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.database.UserDao
import com.iqbal.submission3.githubuserapp.database.UserRoomDatabase
import com.iqbal.submission3.githubuserapp.network.DetailUserResponse
import com.iqbal.submission3.githubuserapp.network.RetrofitClient
import com.iqbal.submission3.githubuserapp.repository.FavoriteUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
  private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
  private val _user = MutableLiveData<DetailUserResponse>()
  val user: LiveData<DetailUserResponse> = _user

  private var userDao: UserDao?
  private var userDb: UserRoomDatabase? = UserRoomDatabase.getDatabase(application)

  init {
    userDao = userDb?.userDao()
  }

  fun setUserDetail(username: String) {
    RetrofitClient.apiInstance
      .getUserDetail(username)
      .enqueue(object : Callback<DetailUserResponse> {
        override fun onResponse(
          call: Call<DetailUserResponse>,
          response: Response<DetailUserResponse>
        ) {
          val body = response.body()
          if (response.isSuccessful) {
            _user.value = body!!
          }
        }

        override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
          Log.d("DetailUserViewModel", "onFailure: ${t.message}")
        }
      })
  }

  fun getUserDetail(): LiveData<DetailUserResponse> {
    return user
  }

  fun addToFavorite(user: User) {
    CoroutineScope(Dispatchers.IO).launch {
      mFavoriteUserRepository.addToFavorite(user)
    }
  }

  fun deleteFromFavorite(user: User) {
    CoroutineScope(Dispatchers.IO).launch {
      mFavoriteUserRepository.deleteFromFavorite(user)
    }
  }

  fun checkUser(id: Int) = userDao?.checkUser(id)
}