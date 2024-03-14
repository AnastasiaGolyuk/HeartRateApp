package test.createx.heartrateapp.presentation.heart_rate_report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import java.time.OffsetDateTime
import java.time.ZoneId
import javax.inject.Inject


@HiltViewModel
class HeartRateReportViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val heartRateRepository: HeartRateRepositoryImpl
) : ViewModel() {
    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    private val user = User(0, "", "", "", "", "", "", "")

    private val _heartRate =
        mutableStateOf(HeartRate(0, 1, 0, "", "", OffsetDateTime.now(ZoneId.systemDefault())))
    val heartRate: State<HeartRate> = _heartRate

    private var normalHints = mutableStateOf(listOf<String>())

    private var exerciseHints = mutableStateOf(listOf<String>())

    private val _heartRateStatus = mutableStateOf(HeartRateStatus.get()[0])
    val heartRateStatus: State<HeartRateStatus?> = _heartRateStatus

    private val _heartRateHint = mutableStateOf("")
    val heartRateHint: State<String> = _heartRateHint


    private fun setUserDataToInstance() {
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    _heartRate.value = _heartRate.value.copy(userId = res[0].id)
                } else {
                    insertUser()
                }
            }
        }
    }

    private fun insertUser() {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun initHintsList(normalHints: List<String>, exerciseHints: List<String>) {
        this.normalHints.value = normalHints
        this.exerciseHints.value = exerciseHints
    }

    fun setHeartRateState(
        heartRateValue: Int,
        userState: String?,
        userStatesList: List<String>,
        heartRateStatuses: List<HeartRateStatus>,
        heartRatesMap: Map<Int, String>
    ) {
        getRateStatus(heartRateValue, userState, userStatesList, heartRateStatuses)
        val heartRateStatus = heartRatesMap[_heartRateStatus.value.title]
        _heartRate.value =
            _heartRate.value.copy(
                heartRateValue = heartRateValue,
                userState = userState,
                heartRateStatus = if (heartRateStatus.isNotNull()) heartRateStatus!!.substringBefore(' ') else ""
            )
    }

    private fun getRateStatus(
        heartRateValue: Int,
        userState: String?,
        userStatesList: List<String>,
        heartRateStatuses: List<HeartRateStatus>
    ) {

        val listIndex: Int =
            if (heartRateValue < 60) {
                0
            } else {
                when (userState) {
                    null, userStatesList[0], userStatesList[1] -> {
                        if (heartRateValue > 100) 2 else 1
                    }

                    userStatesList[2] -> {
                        if (heartRateValue > 140) 2 else 1
                    }

                    else -> {
                        if (heartRateValue > 120) 2 else 1
                    }
                }
            }

        _heartRateStatus.value = heartRateStatuses[listIndex]
        _heartRateHint.value =
            if (userState == userStatesList[2] || userState == userStatesList[3])
                exerciseHints.value[listIndex]
            else
                normalHints.value[listIndex]
    }

    fun saveUserHeartRate() {
        setUserDataToInstance()
        viewModelScope.launch {
            heartRateRepository.insertHeartRate(_heartRate.value)
        }
    }
}