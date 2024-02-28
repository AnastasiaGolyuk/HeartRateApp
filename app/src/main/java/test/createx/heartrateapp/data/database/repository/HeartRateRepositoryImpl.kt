package test.createx.heartrateapp.data.database.repository

import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.dao.HeartRateDao
import test.createx.heartrateapp.data.database.entity.HeartRate
import javax.inject.Inject

class HeartRateRepositoryImpl @Inject constructor(private val heartRateDao: HeartRateDao){
    fun getUserStream(id: Int): Flow<HeartRate?> {
        return heartRateDao.getHeartRate(id)
    }

    fun getAllHeartRatesStream():Flow<List<HeartRate>> {
        return heartRateDao.getAllHeartRates()
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