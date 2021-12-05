package com.iqbal.submission3.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.network.RetrofitClient
import com.iqbal.submission3.githubuserapp.network.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

  companion object {
    private const val TAG = "MainViewModel"
  }

  private val _listUsers = MutableLiveData<List<User>>()
  private val listUsers: LiveData<List<User>> = _listUsers

  fun setSearchUsers(query: String) {
    RetrofitClient.apiInstance
      .getUser(query)
      .enqueue(object : Callback<UserResponse> {
        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
          val body = response.body()
          if (response.isSuccessful) {
            _listUsers.value = body?.items
          }
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
          Log.d(TAG, "onFailure: ${t.message}")
        }

      })
  }

  fun getUser(): LiveData<List<User>> {
    return listUsers
  }

}