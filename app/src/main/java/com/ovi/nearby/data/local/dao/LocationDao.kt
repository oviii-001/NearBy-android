package com.ovi.nearby.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ovi.nearby.domain.model.SharedLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: SharedLocation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<SharedLocation>)

    @Query("SELECT * FROM SharedLocation WHERE groupId = :groupId ORDER BY timestamp DESC")
    fun getLocationsByGroup(groupId: String): Flow<List<SharedLocation>>

    @Query("SELECT * FROM SharedLocation WHERE userId = :userId AND groupId = :groupId")
    fun getUserLocation(userId: String, groupId: String): Flow<SharedLocation?>

    @Query("DELETE FROM SharedLocation WHERE groupId = :groupId")
    suspend fun deleteLocationsByGroup(groupId: String)

    @Query("DELETE FROM SharedLocation WHERE timestamp < :expiryTime")
    suspend fun deleteExpiredLocations(expiryTime: Long)
}
