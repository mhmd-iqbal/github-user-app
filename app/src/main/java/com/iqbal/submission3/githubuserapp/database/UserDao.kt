package com.iqbal.submission3.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun addToFavorite(user: User)

  @Delete
  fun deleteFromFavorite(user: User)

  @Query("SELECT * FROM user ORDER BY id ASC")
  fun getAllFavoriteUser(): LiveData<List<User>>

  @Query("SELECT count(*) FROM User WHERE User.id = :id")
  fun checkUser(id: Int): Int
}