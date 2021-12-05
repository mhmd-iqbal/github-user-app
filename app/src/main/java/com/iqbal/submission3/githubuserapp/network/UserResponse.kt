package com.iqbal.submission3.githubuserapp.network

import com.iqbal.submission3.githubuserapp.database.User

data class UserResponse(
  val items : List<User>
)
