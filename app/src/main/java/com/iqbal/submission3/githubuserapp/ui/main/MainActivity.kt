package com.iqbal.submission3.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.submission3.githubuserapp.R
import com.iqbal.submission3.githubuserapp.adapter.UserAdapter
import com.iqbal.submission3.githubuserapp.database.User
import com.iqbal.submission3.githubuserapp.databinding.ActivityMainBinding
import com.iqbal.submission3.githubuserapp.ui.detail.DetailUserActivity
import com.iqbal.submission3.githubuserapp.ui.favorite.FavoriteUserActivity
import com.iqbal.submission3.githubuserapp.ui.setting.ThemeSettingsActivity

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val viewModel by viewModels<MainViewModel>()
  private lateinit var adapter: UserAdapter

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)
    supportActionBar?.setLogo(R.drawable.ic_logo_white)

    adapter = UserAdapter()
    adapter.notifyDataSetChanged()
    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {
        Intent(this@MainActivity, DetailUserActivity::class.java).also {
          it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
          it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
          it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
          it.putExtra(DetailUserActivity.EXTRA_URL, data.html_url)
          startActivity(it)
        }
      }
    })

    binding.apply {
      rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
      rvUser.setHasFixedSize(true)
      rvUser.adapter = adapter
    }

    viewModel.getUser().observe(this, { user ->
      adapter.setList(user)
      showLoading(false)

      if (adapter.getList().isEmpty()) {
        val errorText = getString(R.string.not_found_text)
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

    binding.fabFavUser.setOnClickListener {
      val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
      startActivity(intent)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    val searchView = menu.findItem(R.id.search).actionView as SearchView

    searchView.setSearchableInfo(
      searchManager.getSearchableInfo(componentName)
    )
    searchView.queryHint = resources.getString(R.string.search_hint)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isEmpty()) {
          return false
        } else {
          searchView.clearFocus()
          binding.apply {
            ivLogo.visibility = View.GONE
            tvInfo.visibility = View.GONE
          }

          showLoading(true)
          viewModel.setSearchUsers(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.setting) {
      val intent = Intent(this@MainActivity, ThemeSettingsActivity::class.java)
      startActivity(intent)
    }

    return super.onOptionsItemSelected(item)
  }

  private fun showLoading(loading: Boolean) {
    if (loading) {
      binding.progressBar.visibility = View.VISIBLE
    } else {
      binding.progressBar.visibility = View.GONE
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }

}