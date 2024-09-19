package dam.a48965.project_48965.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dam.a48965.project_48965.model.Game
import dam.a48965.project_48965.model.MyGame
import dam.a48965.project_48965.model.RecGameApi

@Database(entities = [MyGame::class, Game::class, RecGameApi::class], version = 11, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyGameDatabase : RoomDatabase() {

    abstract fun myGameDao(): MyGameDao

    companion object {
        @Volatile
        private var instances: MutableMap<String, MyGameDatabase> = mutableMapOf()

        fun getDatabase(context: Context, userId: String): MyGameDatabase {
            return instances[userId] ?: synchronized(this) {
                instances[userId] ?: buildDatabaseInstance(context, userId).also { instances[userId] = it }
            }
        }

        private fun buildDatabaseInstance(context: Context, userId: String): MyGameDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyGameDatabase::class.java,
                "MyGame_database_$userId"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
