package com.ovi.nearby.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ovi.nearby.data.local.dao.LocationDao
import com.ovi.nearby.domain.model.SharedLocation

@Database(
    entities = [SharedLocation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
