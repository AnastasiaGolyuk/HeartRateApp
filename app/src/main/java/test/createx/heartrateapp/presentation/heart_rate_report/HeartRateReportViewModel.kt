package test.createx.heartrateapp.presentation.heart_rate_report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.entity.HeartRate
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.HeartRateRepositoryImpl
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import test.createx.heartrateapp.presentation.heart_rate_measurement.UserState
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

    private val heartRateStatuses = HeartRateStatus.get()

    private var normalHints = mutableStateOf(listOf<String>())

    private var exerciseHints = mutableStateOf(listOf<String>())

    private val _heartRateStatus = mutableStateOf(heartRateStatuses[1])
    val heartRateStatus: State<HeartRateStatus> = _heartRateStatus

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

    fun setHeartRateState(heartRateValue: Int, userState: String?) {
        getRateStatus(heartRateValue, userState)
        _heartRate.value =
            _heartRate.value.copy(
                heartRateValue = heartRateValue,
                userState = userState,
                heartRateStatus = _heartRateStatus.value.title.substringBefore(' ')
            )

    }

    private fun getRateStatus(heartRateValue: Int, userState: String?) {

        if (userState == null || userState == UserState.get()[0].title || userState == UserState.get()[1].title) {
            if (heartRateValue < 60) {
                _heartRateStatus.value = heartRateStatuses[0]
                _heartRateHint.value = normalHints.value[0]
                return
            } else if (heartRateValue > 100) {
                _heartRateStatus.value = heartRateStatuses[2]
                _heartRateHint.value = normalHints.value[2]
                return
            } else {
                _heartRateStatus.value = heartRateStatuses[1]
                _heartRateHint.value = normalHints.value[1]
                return
            }
        } else {
            if (userState == UserState.get()[2].title) {
                if (heartRateValue < 60) {
                    _heartRateStatus.value = heartRateStatuses[0]
                    _heartRateHint.value = normalHints.value[0]
                    return
                } else if (heartRateValue > 140) {
                    _heartRateStatus.value = heartRateStatuses[2]
                    _heartRateHint.value = exerciseHints.value[2]
                    return
                } else {
                    _heartRateStatus.value = heartRateStatuses[1]
                    _heartRateHint.value = normalHints.value[1]
                    return
                }
            } else {
                if (heartRateValue < 60) {
                    _heartRateStatus.value = heartRateStatuses[0]
                    _heartRateHint.value = normalHints.value[0]
                    return
                } else if (heartRateValue > 120) {
                    _heartRateStatus.value = heartRateStatuses[2]
                    _heartRateHint.value = exerciseHints.value[2]
                    return
                } else {
                    _heartRateStatus.value = heartRateStatuses[1]
                    _heartRateHint.value = normalHints.value[1]
                    return
                }
            }
        }
    }

    fun saveUserHeartRate() {
        setUserDataToInstance()
        viewModelScope.launch {
            heartRateRepository.insertHeartRate(_heartRate.value)
        }
    }
}