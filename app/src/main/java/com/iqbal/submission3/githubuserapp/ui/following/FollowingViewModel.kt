package com.iqbal.submission3.githubuserapp.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
  private val _listFollowing = MutableLiveData<List<User>>()
  private val listFollowing: LiveData<List<User>> = _listFollowing

  fun setListUserFollowing(username: String) {
    RetrofitClient.apiInstance
      .getUserFollowing(username)
      .enqueue(object : Callback<ArrayList<User>> {
        override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
          val body = response.body()
          if (response.isSuccessful) {
            _listFollowing.value = body!!
          }
        }

        override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
          Log.d("FollowingViewModel", "onFailure: ${t.message}")
        }

      })
  }

  fun getListUserFollowing(): LiveData<List<User>> {
    return listFollowing
  }
}