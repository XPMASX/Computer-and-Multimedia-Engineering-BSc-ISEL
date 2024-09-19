package dam.a48965.project_48965

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dam.a48965.project_48965.domain.DBModule

class MyApplication : Application() {
    val dbModule: DBModule by lazy { DBModule.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    fun getFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}