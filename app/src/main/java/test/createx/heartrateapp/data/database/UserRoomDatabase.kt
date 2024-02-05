package test.createx.heartrateapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.createx.heartrateapp.data.database.dao.UserDao
import test.createx.heartrateapp.data.database.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

//    companion object {
//        @Volatile
//        private var Instance: UserRoomDatabase? = null
//
//        fun getDatabase(context: Context): UserRoomDatabase {
//            // if the Instance is not null, return it, otherwise create a new database instance.
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, UserRoomDatabase::class.java, "user_database")
//                    .build()
//                    .also { Instance = it }
//            }
//        }
//    }
}