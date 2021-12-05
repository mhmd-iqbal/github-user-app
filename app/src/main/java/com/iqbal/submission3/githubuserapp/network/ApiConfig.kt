package com.iqbal.submission3.githubuserapp.network

import com.iqbal.submission3.githubuserapp.BuildConfig
import com.iqbal.submission3.githubuserapp.database.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConfig {
  @GET("search/users")
  @Headers("Authorization: token ${BuildConfig.GithubUserApi}")
  fun getUser(
    @Query("q") query: String
  ): Call<UserResponse>

  @GET("users/{username}")
  @Headers("Authorization: token ${BuildConfig.GithubUserApi}")
  fun getUserDetail(
    @Path("username") username: String
  ): Call<DetailUserResponse>

  @GET("users/{username}/followers")
  @Headers("Authorization: token ${BuildConfig.GithubUserApi}")
  fun getUserFollowers(
    @Path("username") username: String
  ): Call<ArrayList<User>>

  @GET("users/{username}/following")
  @Headers("Authorization: token ${BuildConfig.GithubUserApi}")
  fun getUserFollowing(
    @Path("username") username: String
  ): Call<ArrayList<User>>
}