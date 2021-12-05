package com.iqbal.submission3.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.adapter.UserAdapter
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.databinding.ActivityMainBinding
import com.iqbal.submission3.githubuserapp.ui.detail.DetailUserActivity

class FavoriteUserActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val viewModel by viewModels<FavoriteUserViewModel>()
  private lateinit var adapter: UserAdapter

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    adapter = UserAdapter()
    adapter.notifyDataSetChanged()
    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {
        Intent(this@FavoriteUserActivity, DetailUserActivity::class.java).also {
          it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
          it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
          it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
          it.putExtra(DetailUserActivity.EXTRA_URL, data.html_url)
          startActivity(it)
        }
      }
    })

    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = getString(R.string.favorite_list)
    showLoading(true)


    binding.apply {
      rvUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
      rvUser.setHasFixedSize(true)
      rvUser.adapter = adapter
      fabFavUser.visibility = View.GONE
      ivLogo.setImageResource(R.drawable.ic_data_not_found)
    }

    viewModel.getAllFavoriteUser().observe(this, { user ->
      adapter.setList(user)
      showLoading(false)

      if (adapter.getList().isEmpty()) {
        val errorText = getString(R.string.no_data_found)
        binding.apply {
          ivLogo.visibility = View.VISIBLE
          tvInfo.visibility = View.VISIBLE
          tvInfo.text = errorText
        }
      } else {
        binding.apply {
          ivLogo.visibility = View.GONE
          tvInfo.visibility = View.GONE
        }
      }
    })

  }

  private fun showLoading(loading: Boolean) {
    if (loading) {
      binding.progressBar.visibility = View.VISIBLE
    } else {
      binding.progressBar.visibility = View.GONE
    }
  }

}