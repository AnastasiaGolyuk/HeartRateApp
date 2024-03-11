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
import test.createx.heartrateapp.presentation.heart_rate_measurement.UserState
import test.createx.heartrateapp.presentation.report.DailyRecords
import test.createx.heartrateapp.presentation.statistics.components.DailyAverageMeasurements
import test.createx.heartrateapp.presentation.statistics.components.DonutChartData
import test.createx.heartrateapp.ui.theme.BlueState
import test.createx.heartrateapp.ui.theme.LightBlueState
import test.createx.heartrateapp.ui.theme.OrangeState
import test.createx.heartrateapp.ui.theme.PurpleState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepositoryImpl,
    userRepository: UserRepositoryImpl
) : ViewModel() {
    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()
    private val userId = mutableIntStateOf(1)

    private val _heartRatesAverageList = mutableStateListOf<DailyAverageMeasurements>()
    val heartRatesAverageList: SnapshotStateList<DailyAverageMeasurements> = _heartRatesAverageList

    private val _statesPopularList = mutableStateListOf<DonutChartData>()
    val statesPopularList: SnapshotStateList<DonutChartData> = _statesPopularList

    private val statesColors = listOf(BlueState, OrangeState, LightBlueState, PurpleState)

    val periods = listOf("Week", "Month")

    private val weekLabels = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    private val monthLabels = mutableListOf<String>()

    private val _periodLabels = mutableStateOf(weekLabels)
    val periodLabels: State<List<String>> = _periodLabels

    private val _selectedPeriod = mutableStateOf(periods[0])
    val selectedPeriod: State<String> = _selectedPeriod

    init {
        initMonthList()
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    userId.intValue = res[0].id
                    setWeekHeartRatesList()
                }
            }
        }
    }

    private fun initMonthList() {
        val daysInCurrentMonth = LocalDate.now().lengthOfMonth()
        for (day in 1..daysInCurrentMonth) {
            monthLabels.add(day.toString())
        }
    }

    private fun setAverageDailyMeasurements(
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
        val dayOfPeriod = if (_selectedPeriod.value == periods[0]) {
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

    private fun setDailyRecordsList(list: List<HeartRate>) {
        val groupedItems = list.groupBy { it.dateTime.dayOfMonth }
        for (group in groupedItems) {
            _heartRatesAverageList.add(
                setAverageDailyMeasurements(
                    group.value[0].dateTime,
                    group.value
                )
            )
        }
    }

    private fun setPopularStatesList(list: List<HeartRate>) {
        val listWithNonNullStates = list.filter { it.userState.isNotNull() }
        val groupedItems = listWithNonNullStates.groupBy { it.userState }
        val index = mutableIntStateOf(0)
        for (group in groupedItems) {

            val percentage = group.value.size.toFloat() / listWithNonNullStates.size
            val iconRes = UserState.get().first { it.title==group.key!! }.image
            _statesPopularList.add(
                DonutChartData(
                    color = statesColors[index.intValue],
                    data = percentage,
                    state = group.key!!,
                    iconRes = iconRes
                )
            )
            index.intValue = index.intValue + 1
        }
    }

    fun togglePeriod(period: String) {
        if (_selectedPeriod.value != period) {
            _selectedPeriod.value = period
            _periodLabels.value = if (period == periods[0])
                weekLabels
            else
                monthLabels

            changeListItems()
        }
    }

    private fun changeListItems() {
        _heartRatesAverageList.clear()
        _statesPopularList.clear()
        if (_selectedPeriod.value == periods[0]) {
            setWeekHeartRatesList()
        } else {
            setMonthHeartRatesList()
        }
    }

    private fun setWeekHeartRatesList() {
        val now = OffsetDateTime.now()
        val firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startDateOfTheWeek = firstDayOfWeek.withHour(0).withMinute(0).withSecond(0).withNano(0)
        viewModelScope.launch {
            heartRateRepository.getAllPeriodHeartRatesStream(userId.intValue, startDateOfTheWeek)
                .collect { res ->
                    setDailyRecordsList(res)
                    setPopularStatesList(res)
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
                    setDailyRecordsList(res)
                    setPopularStatesList(res)
                }
        }
    }
}