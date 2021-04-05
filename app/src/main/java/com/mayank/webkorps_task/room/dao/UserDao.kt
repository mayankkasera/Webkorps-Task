package com.mayank.webkorps_task.room.dao

import androidx.room.*
import com.mayank.webkorps_task.room.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User")
    fun getUsers(): List<User>

    @Query("SELECT * FROM User WHERE id == (:id)")
    fun getUser(id : Long): User
}