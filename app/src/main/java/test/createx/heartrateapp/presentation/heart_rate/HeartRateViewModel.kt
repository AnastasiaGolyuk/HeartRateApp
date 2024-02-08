package test.createx.heartrateapp.presentation.heart_rate

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.UserRepositoryImpl
import test.createx.heartrateapp.data.database.entity.User
import javax.inject.Inject

@HiltViewModel
class HeartRateViewModel @Inject constructor(
    userRepository: UserRepositoryImpl
) : ViewModel() {

    private val _users = mutableStateOf(listOf<User>())
    val users : State<List<User>> = _users


    val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    val user: Flow<User?> = userRepository.getUserStream(1)
}