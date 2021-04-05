package com.mayank.webkorps_task.room

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mayank.webkorps_task.room.dao.UserDao
import com.mayank.webkorps_task.room.entity.User

@Database(entities = [User::class], version = 1,exportSchema = false)
@TypeConverters(User.DateConverter::class)
abstract class LocaleDataBase : RoomDatabase()  {

    abstract fun getUserDao(): UserDao

    companion object {
        var INSTANCE: LocaleDataBase? = null

        fun getAppDataBase(context: Context): LocaleDataBase {
            if (INSTANCE == null){
                synchronized(LocaleDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LocaleDataBase::class.java, "myDB").allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}