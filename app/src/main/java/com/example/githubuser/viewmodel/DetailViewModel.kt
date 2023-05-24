package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.UserDatabase
import com.example.githubuser.models.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application): AndroidViewModel(application) {
    companion object{
        private const val TAG = "DetailViewModel"
    }

    private var userDao:FavoriteUserDao?
    private var userDb : UserDatabase?

    init{
        userDb =UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    private val _detailUsers = MutableLiveData<DetailUserResponse>()
    val detailUsers: LiveData<DetailUserResponse> = _detailUsers


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun setDetailUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailUsers.postValue(responseBody!!)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun addToFavorite(username: String,avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(username: String)=userDao?.checkUser(username)

    fun removeFromFavorite(username: String){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(username)
        }
    }
}

