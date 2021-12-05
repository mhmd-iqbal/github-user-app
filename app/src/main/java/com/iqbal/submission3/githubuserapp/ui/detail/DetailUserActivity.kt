package com.iqbal.submission3.githubuserapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.adapter.SectionsPagerAdapter
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.databinding.ActivityDetailUserBinding
import com.iqbal.submission3.githubuserapp.helper.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
  companion object {
    const val EXTRA_USERNAME = "extra_username"
    const val EXTRA_ID = "extra_id"
    const val EXTRA_AVATAR = "extra_avatar"
    const val EXTRA_URL = "extra_url"

    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.tab_text_1,
      R.string.tab_text_2
    )
  }

  private var _binding: ActivityDetailUserBinding? = null
  private val binding get() = _binding!!
  private var user: User? = null
  private var _isChecked = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityDetailUserBinding.inflate(layoutInflater)
    setContentView(binding.root)
    showLoading(true)

    binding.btnBack.setOnClickListener {
      finish()
    }

    user = User()
    val username = intent.getStringExtra(EXTRA_USERNAME)
    val id = intent.getIntExtra(EXTRA_ID, 0)
    val avatar = intent.getStringExtra(EXTRA_AVATAR)
    val url = intent.getStringExtra(EXTRA_URL)

    val bundle = Bundle()
    bundle.putString(EXTRA_USERNAME, username)

    val detailUserViewModel = obtainViewModel(this)

    if (username != null) {
      detailUserViewModel.setUserDetail(username)
      detailUserViewModel.getUserDetail().observe(this, { user ->
        if (user != null) {
          showLoading(false)
          binding.apply {
            tvDetailUsername.text = user.login
            tvName.text = user.name ?: getString(R.string.no_name)
            tvCompany.text = user.company ?: getString(R.string.no_company)
            tvLocation.text = user.location ?: getString(R.string.no_location)
            "${user.followers} Followers".also { tvFollowers.text = it }
            "${user.following} Following".also { tvFollowing.text = it }
            "${user.public_repos} Repository".also { tvRepos.text = it }
            Glide.with(this@DetailUserActivity)
              .load(user.avatar_url)
              .transition(DrawableTransitionOptions.withCrossFade())
              .centerCrop()
              .into(ivDetailAvatar)
          }
        }
      })
    }

    CoroutineScope(Dispatchers.IO).launch {
      val count = detailUserViewModel.checkUser(id)
      withContext(Dispatchers.Main) {
        if (count != null) {
          if (count > 0) {
            binding.toggleFavorite.isChecked = true
            _isChecked = true
          } else {
            binding.toggleFavorite.isChecked = false
            _isChecked = false
          }
        }
      }
    }

    binding.toggleFavorite.setOnClickListener {
      user?.let {
        user?.id = id
        user?.login = username
        user?.avatar_url = avatar
        user?.html_url = url
      }
      _isChecked = !_isChecked
      if (_isChecked) {
        detailUserViewModel.addToFavorite(user as User)
        Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
      } else {
        detailUserViewModel.deleteFromFavorite(user as User)
        Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show()
      }
      binding.toggleFavorite.isChecked = _isChecked
    }

    val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
    val viewPager: ViewPager2 = findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = findViewById(R.id.tabs)
    TabLayoutMediator(tabs, viewPager) { tab, position ->
      tab.text = resources.getString(TAB_TITLES[position])
    }.attach()
  }

  private fun showLoading(loading: Boolean) {
    if (loading) {
      binding.loading.visibility = View.VISIBLE
    } else {
      binding.loading.visibility = View.GONE
    }
  }

  private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }
}


