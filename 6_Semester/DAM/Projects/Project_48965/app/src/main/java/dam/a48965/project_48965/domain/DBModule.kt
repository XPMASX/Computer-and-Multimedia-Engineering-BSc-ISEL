package dam.a48965.project_48965.domain

import android.content.Context

class DBModule private constructor(private val context: Context) {

    var userId: String? = null
        set(value) {
            field = value
            myGameDatabase = value?.let { MyGameDatabase.getDatabase(context, it) }
        }

    private var myGameDatabase: MyGameDatabase? = null
    val myGameDao: MyGameDao
        get() = myGameDatabase?.myGameDao() ?: throw IllegalStateException("DBModule is not initialized with userId")

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: DBModule? = null

        fun getInstance(context: Context): DBModule {
            return instance ?: synchronized(this) {
                instance ?: DBModule(context).also { instance = it }
            }
        }
    }
}