package com.iqbal.submission3.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.databinding.ItemRowUserBinding
import com.iqbal.submission3.githubuserapp.helper.UserDiffCallback

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

  private val list = ArrayList<User>()
  private var onItemClickCallback: OnItemClickCallback? = null

  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }

  fun setList(users: List<User>) {
    val diffCallback = UserDiffCallback(this.list, users)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    this.list.clear()
    this.list.addAll(users)
    diffResult.dispatchUpdatesTo(this)
  }

  fun getList(): List<User> {
    return list
  }

  inner class ListViewHolder(private val binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
      binding.root.setOnClickListener {
        onItemClickCallback?.onItemClicked(user)
      }

      binding.apply {
        Glide.with(itemView)
          .load(user.avatar_url)
          .transition(DrawableTransitionOptions.withCrossFade())
          .circleCrop()
          .into(ivAvatar)
        tvUsername.text = user.login
        tvUrl.text = user.html_url
      }
    }
  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ListViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    holder.bind(list[position])
  }

  override fun getItemCount(): Int = list.size

  interface OnItemClickCallback {
    fun onItemClicked(data: User)
  }
}