package com.soaic.jetpackcomponent.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.soaic.jetpackcomponent.model.User

@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): List<User>


}