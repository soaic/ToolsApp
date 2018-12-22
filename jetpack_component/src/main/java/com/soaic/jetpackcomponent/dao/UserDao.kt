package com.soaic.jetpackcomponent.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.soaic.jetpackcomponent.entity.XUser
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Update

@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): List<XUser>

    @Insert
    fun insert(users: XUser)

    @Insert
    fun insertAll(vararg users: XUser)

    @Update
    fun updateUser(users: XUser)

    @Delete
    fun delete(user: XUser)
}