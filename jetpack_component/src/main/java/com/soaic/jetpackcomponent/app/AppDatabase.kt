package com.soaic.jetpackcomponent.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.soaic.jetpackcomponent.dao.UserDao
import com.soaic.jetpackcomponent.entity.XUser
import android.arch.persistence.room.Room
import android.content.Context


@Database(entities = [XUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "database-name"
        private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context, AppDatabase::class.java, DATABASE_NAME)
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract fun userDao(): UserDao


}