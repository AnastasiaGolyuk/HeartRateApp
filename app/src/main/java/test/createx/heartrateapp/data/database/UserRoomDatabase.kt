package test.createx.heartrateapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import test.createx.heartrateapp.data.database.dao.UserDao
import test.createx.heartrateapp.data.database.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}