package dam.a48965.project_48965.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dam.a48965.project_48965.domain.MyGameDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileViewModel(private val myGameDao: MyGameDao) : ViewModel() {
    val user = Firebase.auth.currentUser

    suspend fun countPlayedGames(): Int = withContext(Dispatchers.IO) {
        myGameDao.countPlayedGames()
    }

    suspend fun countBacklogGames(): Int = withContext(Dispatchers.IO) {
        myGameDao.countBacklogGames()
    }
}