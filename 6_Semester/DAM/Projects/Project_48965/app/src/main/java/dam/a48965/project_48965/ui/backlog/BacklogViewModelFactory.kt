package dam.a48965.project_48965.ui.backlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.a48965.project_48965.domain.DBModule

class BacklogViewModelFactory(private val dbModule: DBModule) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BacklogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BacklogViewModel(dbModule.myGameDao, dbModule.userId ?: "") as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}