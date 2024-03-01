package test.createx.heartrateapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.entity.HeartRate
import java.time.OffsetDateTime

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

    @Query("SELECT * FROM heart_rate WHERE user_id = :userId")
    fun getAllHeartRates(userId: Int): Flow<List<HeartRate>>

    @Query("SELECT * FROM heart_rate WHERE user_id = :userId AND date_time >= :periodStartDate")
    fun getAllPeriodHeartRates(userId: Int, periodStartDate: OffsetDateTime): Flow<List<HeartRate>>
}