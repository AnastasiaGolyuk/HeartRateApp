package test.createx.heartrateapp.presentation.heart_rate

import android.view.SurfaceView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.kibotu.heartrateometer.HeartRateOmeter
import test.createx.heartrateapp.data.database.entity.HeartRate
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class HeartRateViewModel @Inject constructor(
    userRepository: UserRepositoryImpl
) : ViewModel() {

    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    private val user = User(0, "", "", "", "", "", "", "")

    private val _heartRate = mutableStateOf(HeartRate(0, 1, "", "", OffsetDateTime.MIN))
    val heartRate: State<HeartRate> = _heartRate

    private val _subscription: MutableState<CompositeDisposable?> = mutableStateOf(null)
    val subscription: State<CompositeDisposable?> = _subscription

    private val _bpmUpdates: MutableState<Disposable?> = mutableStateOf(null)
    val bpmUpdates: State<Disposable?> = _bpmUpdates

    private val _isPaused = mutableStateOf(true)
    var isPaused: State<Boolean> = _isPaused

    private val _rate = mutableStateOf("--")
    val rate: State<String> = _rate

    private val _hints = Hint.get()

    private val _hint = mutableStateOf(_hints[0])
    val hint: State<Hint> = _hint

    private val _timeLeft = mutableFloatStateOf(30f)
    val timeLeft: State<Float> = _timeLeft

    init {
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    _heartRate.value = _heartRate.value.copy(userId = res[0].id)
                } else {
                    userRepository.insertUser(user)
                }
            }
        }
    }

    fun startMeasurement() {
        viewModelScope.launch {
            while (!_isPaused.value) {
                delay(100)
                _timeLeft.floatValue -= 0.1f
                if (_timeLeft.floatValue <= 0){
                    disposeSubscription()
                    return@launch
                }
            }
        }
    }

    fun disposeSubscription() {
        if (_subscription.value?.isDisposed == false) {
            _subscription.value?.clear()
            _subscription.value?.dispose()
        }
    }

    fun initBpmUpdates(surfaceView: SurfaceView) {
        _subscription.value = CompositeDisposable()
        _bpmUpdates.value = HeartRateOmeter()
            .withAverageAfterSeconds(3)
            .setFingerDetectionListener { isFingerDetected ->
                _isPaused.value = !isFingerDetected
            }.bpmUpdates(surfaceView = surfaceView)
            .subscribe(
                { bpm: HeartRateOmeter.Bpm? -> _rate.value = bpm?.value.toString() },
                { it -> println("${it.message}") }
            )
        _subscription.value?.add(_bpmUpdates.value!!)
    }

    fun flushMeasurementData(){
        disposeSubscription()
        _isPaused.value=true
        _timeLeft.floatValue=30f
        _rate.value="--"
        _hint.value=_hints[0]
    }
}