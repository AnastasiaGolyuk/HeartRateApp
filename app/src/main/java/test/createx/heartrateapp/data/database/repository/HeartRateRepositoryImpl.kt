package test.createx.heartrateapp.data.database.repository

import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.dao.HeartRateDao
import test.createx.heartrateapp.data.database.entity.HeartRate
import java.time.OffsetDateTime
import javax.inject.Inject

class HeartRateRepositoryImpl @Inject constructor(private val heartRateDao: HeartRateDao) {
    fun getUserStream(id: Int): Flow<HeartRate?> {
        return heartRateDao.getHeartRate(id)
    }

    fun getAllHeartRatesStream(userId: Int): Flow<List<HeartRate>> {
        return heartRateDao.getAllHeartRates(userId)
    }

    fun getAllPeriodHeartRatesStream(
        userId: Int,
        periodStartDate: OffsetDateTime
    ): Flow<List<HeartRate>> {
        return heartRateDao.getAllPeriodHeartRates(userId, periodStartDate)
    }

    suspend fun insertHeartRate(heartRate: HeartRate) {
        heartRateDao.insert(heartRate)
    }

    suspend fun deleteHeartRate(heartRate: HeartRate) {
        heartRateDao.delete(heartRate)
    }

    suspend fun updateHeartRate(heartRate: HeartRate) {
        heartRateDao.update(heartRate)
    }
}