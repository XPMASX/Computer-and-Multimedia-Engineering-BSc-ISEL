package dam.a48965.project_48965.ui.played

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.project_48965.domain.MyGameDao
import dam.a48965.project_48965.model.RecGameApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayedViewModel(private val myGameDao: MyGameDao, private val userId: String) : ViewModel() {

    private val _allMyGames = MutableLiveData<List<RecGameApi>>()
    val allMyGames: LiveData<List<RecGameApi>> = _allMyGames

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            val gameIds = withContext(Dispatchers.IO) {
                myGameDao.getAllMyPlayedIds()
            }

            val games = mutableListOf<RecGameApi>()
            gameIds.forEach { id ->
                val game = withContext(Dispatchers.IO) {
                    myGameDao.getRecGameById(id)
                }
                game?.let { games.add(it) }
            }

            _allMyGames.value = games.sortedBy { it.name }
        }
    }
}