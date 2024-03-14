package test.createx.heartrateapp.presentation.statistics

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.entity.HeartRate
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.HeartRateRepositoryImpl
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import test.createx.heartrateapp.presentation.report.DailyRecords
import test.createx.heartrateapp.presentation.statistics.components.popularStateChart.DonutChartData
import test.createx.heartrateapp.ui.theme.BlueState
import test.createx.heartrateapp.ui.theme.LightBlueState
import test.createx.heartrateapp.ui.theme.OrangeState
import test.createx.heartrateapp.ui.theme.PurpleState
import java.time.DayOfWeek
import java.time.OffsetDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.math.round

private const val DAYS_IN_WEEK = 7

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepositoryImpl,
    userRepository: UserRepositoryImpl
) : ViewModel() {

    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()
    private val userId = mutableIntStateOf(1)

    private val _averageRatePerPeriod = mutableIntStateOf(0)
    val averageRatePerPeriod: State<Int> = _averageRatePerPeriod

    private val _heartRatesAverageList = mutableStateListOf<DailyAverageMeasurements>()
    val heartRatesAverageList: SnapshotStateList<DailyAverageMeasurements> = _heartRatesAverageList

    private val _statesPopularList = mutableStateListOf<DonutChartData>()
    val statesPopularList: SnapshotStateList<DonutChartData> = _statesPopularList

    private val statesColors = listOf(BlueState, OrangeState, LightBlueState, PurpleState)

    private val _periodLabels = mutableStateListOf<String>()
    val periodLabels: SnapshotStateList<String> = _periodLabels

    private val _selectedPeriod = mutableStateOf("")
    val selectedPeriod: State<String> = _selectedPeriod

    private val _selectedValue = mutableIntStateOf(0)
    val selectedValue: State<Int> = _selectedValue

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
                    setWeekHeartRatesList()
                }
            }
        }
        setWeekHeartRatesList()
        setMonthHeartRatesList()
    }

    fun updateSelectedValue(value: Int) {
        _selectedValue.intValue = value
    }

    private fun setAverageHeartRate(list: List<HeartRate>) {
        val sumOfRates = list.sumOf { it.heartRateValue }
        _averageRatePerPeriod.intValue = round(sumOfRates.toFloat() / list.size).toInt()
    }

    private fun getAverageDailyMeasurements(
        dateTime: OffsetDateTime,
        list: List<HeartRate>
    ): DailyAverageMeasurements {
        val normalHeartRateList = list.filter { it.heartRateValue in 60..99 }

        val lowestRate = list.minOfOrNull { it.heartRateValue }!!
        val highestRate = list.maxOfOrNull { it.heartRateValue }!!
        val normalAverageRate = if (normalHeartRateList.isEmpty()) {
            if (lowestRate.isNotNull()) {
                lowestRate
            } else {
                highestRate
            }
        } else normalHeartRateList.sumOf { it.heartRateValue } / normalHeartRateList.size
        val dayOfPeriod = if (_periodLabels.size == DAYS_IN_WEEK) {
            dateTime.dayOfWeek.value
        } else {
            dateTime.dayOfMonth
        }
        return DailyAverageMeasurements(
            dayOfPeriod = dayOfPeriod - 1,
            normalHeartRate = normalAverageRate,
            lowestHeartRate = lowestRate,
            highestHeartRate = highestRate
        )
    }

    private fun setDailyMeasurementsList(list: List<HeartRate>) {
        val groupedItems = list.groupBy { it.dateTime.dayOfMonth }
        val dataList = mutableListOf<DailyAverageMeasurements>()
        for (group in groupedItems) {
            dataList.add(
                getAverageDailyMeasurements(
                    group.value[0].dateTime,
                    group.value
                )
            )
        }
        _heartRatesAverageList.addAll(dataList)
        if (_heartRatesAverageList.isNotEmpty()) {
            _selectedValue.intValue = dataList.first().dayOfPeriod
        }
    }

    private fun setPopularStatesList(list: List<HeartRate>) {
        val listWithNonNullStates = list.filter { it.userState.isNotNull() }
        val groupedItems = listWithNonNullStates.groupBy { it.userState }
        val index = mutableIntStateOf(0)

        for (group in groupedItems) {

            val percentage = group.value.size.toFloat() / listWithNonNullStates.size

            _statesPopularList.add(
                DonutChartData(
                    color = statesColors[index.intValue],
                    data = percentage,
                    userState = group.key!!
                )
            )
            index.intValue = index.intValue + 1
        }
    }


    fun togglePeriod(period: String) {
        if (_selectedPeriod.value != period ) {
            _selectedPeriod.value = period
        }
    }

    fun updateData(periodLabels: List<String>) {
        clearDataLists()
        _periodLabels.addAll(periodLabels)
        changeListItems()
    }

    private fun clearDataLists() {
        _periodLabels.clear()
        _heartRatesAverageList.clear()
        _statesPopularList.clear()
    }

    private fun changeListItems() {
        if (_periodLabels.size == DAYS_IN_WEEK ) {
            setAverageHeartRate(heartRatesWeeklyList)
            setDailyMeasurementsList(heartRatesWeeklyList)
            setPopularStatesList(heartRatesWeeklyList)
        } else {
            setAverageHeartRate(heartRatesMonthlyList)
            setDailyMeasurementsList(heartRatesMonthlyList)
            setPopularStatesList(heartRatesMonthlyList)
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
                    _isLoading.value = false
                }
        }
    }
}