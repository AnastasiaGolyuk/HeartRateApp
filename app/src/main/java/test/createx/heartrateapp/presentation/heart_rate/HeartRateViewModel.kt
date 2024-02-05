package test.createx.heartrateapp.presentation.heart_rate

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

    val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    val user: Flow<User?> = userRepository.getUserStream(1)
}