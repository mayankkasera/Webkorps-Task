package com.mayank.webkorps_task.room.entity

import androidx.room.*
import java.util.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var email: String = "",
    var dob: Calendar = Calendar.getInstance(),
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray
) {
    class DateConverter {
        @TypeConverter
        fun toCalendar(l: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = l }

        @TypeConverter
        fun fromCalendar(c: Calendar): Long = c.time.time
    }
}
