package test.createx.heartrateapp.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import test.createx.heartrateapp.presentation.common.user.BaseUserViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : BaseUserViewModel(userRepository) {

    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    private val isExist = mutableStateOf<Boolean?>(null)

    private val _isLoading = mutableStateOf(true)
    val isLoading : State<Boolean> = _isLoading

    private val _isNameInputVisible = mutableStateOf(false)
    val isNameInputVisible : State<Boolean> = _isNameInputVisible

    private val _isSexPickerVisible = mutableStateOf(false)
    val isSexPickerVisible : State<Boolean> = _isSexPickerVisible

    private val _isAgePickerVisible = mutableStateOf(false)
    val isAgePickerVisible : State<Boolean> = _isAgePickerVisible

    private val _isLifestylePickerVisible = mutableStateOf(false)
    val isLifestylePickerVisible : State<Boolean> = _isLifestylePickerVisible

    private val _areChangesSaved = mutableStateOf(true)
    val areChangesSaved: State<Boolean> = _areChangesSaved

    init {
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    _user.value = res[0]
                    isExist.value = true
                }
                else {
                    isExist.value=false
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SaveChanges -> {
                if (isExist.value!!) {
                    updateUser(_user.value)
                } else {
                    addUser(_user.value)
                }
                _areChangesSaved.value = true
            }
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    override fun setUnitsList(units: List<String>){
        viewModelScope.launch {
            while (isExist.value==null){
                delay(10)
            }
            super.setUnitsList(units)
            _areChangesSaved.value = true
            _isLoading.value=false
        }
    }

    fun onToggleNameVisibility() {
        _isNameInputVisible.value = !_isNameInputVisible.value
       _isSexPickerVisible.value = false
       _isAgePickerVisible.value = false
       _isLifestylePickerVisible.value = false
        _isWeightPickerVisible.value = false
        _isHeightPickerVisible.value = false
    }

    fun onToggleSexVisibility() {
       _isSexPickerVisible.value = !_isSexPickerVisible.value
       _isNameInputVisible.value = false
       _isAgePickerVisible.value = false
       _isLifestylePickerVisible.value = false
        _isWeightPickerVisible.value = false
        _isHeightPickerVisible.value = false
    }

    fun onToggleAgeVisibility() {
       _isAgePickerVisible.value = !_isAgePickerVisible.value
       _isNameInputVisible.value = false
       _isSexPickerVisible.value = false
       _isLifestylePickerVisible.value = false
        _isWeightPickerVisible.value = false
        _isHeightPickerVisible.value = false
    }

    fun onToggleLifestyleVisibility() {
       _isLifestylePickerVisible.value = !_isLifestylePickerVisible.value
       _isNameInputVisible.value = false
       _isAgePickerVisible.value = false
       _isSexPickerVisible.value = false
        _isWeightPickerVisible.value = false
        _isHeightPickerVisible.value = false
    }

    override fun onToggleWeightVisibility() {
        super.onToggleWeightVisibility()
       _isNameInputVisible.value = false
       _isAgePickerVisible.value = false
       _isSexPickerVisible.value = false
       _isLifestylePickerVisible.value = false
    }

    override fun onToggleHeightVisibility() {
        super.onToggleHeightVisibility()
       _isNameInputVisible.value = false
       _isAgePickerVisible.value = false
       _isSexPickerVisible.value = false
       _isLifestylePickerVisible.value = false
    }

    override fun onNameChange(name: String) {
        super.onNameChange(name)
        _areChangesSaved.value = false
    }

    override fun onSexChange(sex: String) {
        super.onSexChange(sex)
        _areChangesSaved.value = false
    }

    override fun onAgeChange(age: String) {
        super.onAgeChange(age)
        _areChangesSaved.value = false
    }

    override fun onLifestyleChange(lifestyle: String) {
        super.onLifestyleChange(lifestyle)
        _areChangesSaved.value = false
    }

    override fun onWeightChange(weight: String) {
        super.onWeightChange(weight)
        _areChangesSaved.value = false
    }

    override fun onHeightChange(height: String) {
        super.onHeightChange(height)
        _areChangesSaved.value = false
    }

    override fun onUnitsChange(units: String) {
        super.onUnitsChange(units)
        _areChangesSaved.value = false
    }
}