package com.iqbal.submission3.githubuserapp.ui.following

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.adapter.UserAdapter
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.databinding.FragmentFollowBinding
import com.iqbal.submission3.githubuserapp.ui.detail.DetailUserActivity

class FollowingFragment : Fragment(R.layout.fragment_follow) {
  private var _binding: FragmentFollowBinding? = null
  private val binding get() = _binding!!
  private val viewModel by viewModels<FollowingViewModel>()
  private lateinit var adapter: UserAdapter
  private lateinit var username: String

  @SuppressLint("NotifyDataSetChanged")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val args = arguments
    username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
    _binding = FragmentFollowBinding.bind(view)

    adapter = UserAdapter()
    adapter.notifyDataSetChanged()
    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {
        Intent(activity, DetailUserActivity::class.java).also {
          it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
          it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
          it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
          it.putExtra(DetailUserActivity.EXTRA_URL, data.html_url)
          startActivity(it)
        }
      }
    })

    binding.apply {
      rvUser.layoutManager = LinearLayoutManager(activity)
      rvUser.setHasFixedSize(true)
      rvUser.adapter = adapter
    }

    showLoading(true)
    viewModel.setListUserFollowing(username)
    viewModel.getListUserFollowing().observe(viewLifecycleOwner, { users ->
      adapter.setList(users)
      showLoading(false)
      if (adapter.getList().isEmpty()) {
        binding.tvNoData.visibility = View.VISIBLE
      }
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun showLoading(loading: Boolean) {
    if (loading) {
      binding.progressBar.visibility = View.VISIBLE
    } else {
      binding.progressBar.visibility = View.GONE
    }
  }
}