package dam.a48965.project_48965.ui.played

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.a48965.project_48965.domain.DBModule

class PlayedViewModelFactory(private val dbModule: DBModule) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayedViewModel(dbModule.myGameDao, dbModule.userId ?: "") as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}