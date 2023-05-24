package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FavoriteAdapter
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        favoriteViewModel.getFavoriteUser()?.observe(this  ){user ->
                setUser(user)
        }

    }


    private fun setUser(user: List<FavoriteUser>) {
        val favoriteAdapter= FavoriteAdapter(user) { userItem ->
            val intent = Intent(this, DetailUserActivity::class.java).apply {
                putExtra(DetailUserActivity.EXTRA_USERNAME, userItem.username)
            }
            startActivity(intent)
        }
        binding.rvFavorite.adapter=favoriteAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}