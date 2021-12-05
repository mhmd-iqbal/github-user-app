package com.iqbal.submission3.githubuserapp.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
  private val _listFollowers = MutableLiveData<List<User>>()
  private val listFollowers: LiveData<List<User>> = _listFollowers

  fun setListUserFollowers(username: String) {
    RetrofitClient.apiInstance
      .getUserFollowers(username)
      .enqueue(object : Callback<ArrayList<User>> {
        override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
          val body = response.body()
          if (response.isSuccessful)
            _listFollowers.value = body!!
        }

        override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
          Log.d("FollowersViewModel", "onFailure: ${t.message}")
        }

      })
  }

  fun getListUserFollowers(): LiveData<List<User>> {
    return listFollowers
  }
}