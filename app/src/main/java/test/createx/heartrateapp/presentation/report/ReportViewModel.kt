package test.createx.heartrateapp.presentation.report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.entity.HeartRate
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.HeartRateRepositoryImpl
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import java.time.DayOfWeek
import java.time.OffsetDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepositoryImpl,
    userRepository: UserRepositoryImpl
) : ViewModel() {
    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()
    private val userId = mutableIntStateOf(1)

    private var _heartRatesDailyList = mutableStateListOf<DailyRecords>()
    val heartRatesDailyList: SnapshotStateList<DailyRecords> = _heartRatesDailyList

    val periods = mutableListOf<String>()

    private val _selectedPeriod = mutableStateOf("")
    val selectedPeriod: State<String> = _selectedPeriod

    private val _heartRatesWeeklyList = mutableStateListOf<HeartRate>()
    val heartRatesWeeklyList : SnapshotStateList<HeartRate> = _heartRatesWeeklyList

    private var heartRatesMonthlyList = mutableStateListOf<HeartRate>()

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    userId.intValue = res[0].id
                }
            }
        }
        setWeekHeartRatesList()
        setMonthHeartRatesList()
    }

    fun setPeriodsList(periods: List<String>){
        this.periods.clear()
        this.periods.addAll(periods)
        if (_heartRatesWeeklyList.isEmpty()) {
            togglePeriod(periods[1])
        } else {
            togglePeriod(periods[0])
        }
    }

    private fun setDailyRecordsList(list: List<HeartRate>) {
        val groupedItems = list.groupBy { it.dateTime.dayOfMonth }
        val dataList = mutableListOf<DailyRecords>()
        for (group in groupedItems) {
            dataList.add(
                DailyRecords(
                    dateTime = group.value[0].dateTime,
                    heartRateList = group.value
                )
            )
        }
        _heartRatesDailyList.clear()
        _heartRatesDailyList.addAll(dataList)
    }

    fun togglePeriod(period: String) {
        if (_selectedPeriod.value != period) {
            _selectedPeriod.value = period
            changeListItems()
        }
    }

    private fun changeListItems() {
        if (_selectedPeriod.value == periods[0]) {
            setDailyRecordsList(heartRatesWeeklyList)
        } else {
            setDailyRecordsList(heartRatesMonthlyList)
        }
    }

    private fun setWeekHeartRatesList() {
        val now = OffsetDateTime.now()
        val firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startDateOfTheWeek = firstDayOfWeek.withHour(0).withMinute(0).withSecond(0).withNano(0)
        viewModelScope.launch {
            heartRateRepository.getAllPeriodHeartRatesStream(userId.intValue, startDateOfTheWeek)
                .collect { res ->
                    _heartRatesWeeklyList.addAll(res)
                    _isLoading.value = false
                }
        }
    }

    private fun setMonthHeartRatesList() {
        val now = OffsetDateTime.now()
        val firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
        val startDateOfTheMonth =
            firstDayOfMonth.withHour(0).withMinute(0).withSecond(0).withNano(0)
        viewModelScope.launch {
            heartRateRepository.getAllPeriodHeartRatesStream(userId.intValue, startDateOfTheMonth)
                .collect { res ->
                    heartRatesMonthlyList.addAll(res)
                }
        }
    }
}