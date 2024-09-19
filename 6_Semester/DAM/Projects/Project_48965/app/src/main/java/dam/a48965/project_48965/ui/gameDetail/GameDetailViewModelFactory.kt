package dam.a48965.project_48965.ui.gameDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.a48965.project_48965.domain.MyGameDao
import dam.a48965.project_48965.network.responses.GameAPI

class GameDetailViewModelFactory(private val gameAPI: GameAPI, private val myGameDao: MyGameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameDetailViewModel(gameAPI, myGameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}