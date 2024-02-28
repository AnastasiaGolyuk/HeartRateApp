package test.createx.heartrateapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.entity.HeartRate

@Dao
interface HeartRateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(heartRate: HeartRate)

    @Update
    suspend fun update(heartRate: HeartRate)

    @Delete
    suspend fun delete(heartRate: HeartRate)

    @Query("SELECT * FROM heart_rate WHERE id = :id ")
    fun getHeartRate(id: Int): Flow<HeartRate>

    @Query("SELECT * FROM heart_rate")
    fun getAllHeartRates(): Flow<List<HeartRate>>
}