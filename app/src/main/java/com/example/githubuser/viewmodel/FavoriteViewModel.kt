package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.UserDatabase

class FavoriteViewModel(application: Application):AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb : UserDatabase?

    init{
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser():LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUserByUsername()
    }


}