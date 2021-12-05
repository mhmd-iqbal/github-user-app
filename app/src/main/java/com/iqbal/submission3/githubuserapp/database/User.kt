package com.iqbal.submission3.githubuserapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Int = 0,

  @ColumnInfo(name = "username")
  var login: String? = null,

  var html_url: String? = null,

  @ColumnInfo(name = "avatar_url")
  var avatar_url: String? = null
) : Parcelable
