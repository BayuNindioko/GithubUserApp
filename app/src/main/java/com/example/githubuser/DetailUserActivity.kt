package com.example.githubuser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPref = getSharedPreferences("NAME", Context.MODE_PRIVATE)
        sharedPref.edit().putString("USERNAME_KEY", username).apply()

        val detailUserViewModel = ViewModelProvider(
            this
        )[DetailViewModel::class.java]

        username?.let { detailUserViewModel.setDetailUsers(it) }

        detailUserViewModel.detailUsers.observe(this) {
            if (it !=null){
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollowers.text = resources.getString(R.string.followers, it.followers)
                binding.tvFollowing.text = resources.getString(R.string.following, it.following)
                binding.tvRepo.text = resources.getString(R.string.repository, it.publicRepos)
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var _isCheked = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = username?.let { detailUserViewModel.checkUser(it) }
            withContext(Dispatchers.Main){
                if (count!=null){
                    if (count> 0){
                        binding.toggleFavorite.isChecked=true
                        _isCheked=true
                    }else{
                        binding.toggleFavorite.isChecked=false
                        _isCheked=false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            _isCheked = !_isCheked
            if(_isCheked){
                if (username != null) {
                    val avatarUrl = detailUserViewModel.detailUsers.value?.avatarUrl ?: ""
                    detailUserViewModel.addToFavorite(username,avatarUrl)
                }
            }else{
                if (username != null) {
                    detailUserViewModel.removeFromFavorite(username)
                }
            }
            binding.toggleFavorite.isChecked =_isCheked
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position == 1) {
                tab.text = resources.getString(TAB_CODES[position])
            } else {
                tab.text = resources.getString(TAB_CODES[position])
            }
        }.attach()

    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
            binding.tvFollowers.visibility = View.GONE
        } else {
            binding.progressBarDetail.visibility = View.GONE
            binding.tvFollowers.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_CODES = intArrayOf(
            R.string.Followers,
            R.string.Following
        )
    }
}