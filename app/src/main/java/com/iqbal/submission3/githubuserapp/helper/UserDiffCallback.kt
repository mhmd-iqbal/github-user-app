package com.iqbal.submission3.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.iqbal.submission3.githubuserapp.database.User

class UserDiffCallback(private val mOldUserList: List<User>, private val mNewUserList: List<User>) :
  DiffUtil.Callback() {
  override fun getOldListSize(): Int {
    return mOldUserList.size
  }

  override fun getNewListSize(): Int {
    return mNewUserList.size
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldUser = mOldUserList[oldItemPosition]
    val newUser = mNewUserList[newItemPosition]
    return oldUser.login == newUser.login && oldUser.id == newUser.id
  }
}