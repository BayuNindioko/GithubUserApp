package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUserByUsername(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.username = :username")
    suspend fun checkUser(username: String):Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.username= :username")
    suspend fun removeFromFavorite(username: String):Int
}