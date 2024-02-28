package test.createx.heartrateapp.data.database.repository

import kotlinx.coroutines.flow.Flow
import test.createx.heartrateapp.data.database.dao.UserDao
import test.createx.heartrateapp.data.database.entity.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) {
     fun getUserStream(id: Int): Flow<User?> {
        return userDao.getUser(id)
    }

    fun getAllUsersStream(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

     suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

     suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

     suspend fun updateUser(user: User) {
       userDao.update(user)
    }
}