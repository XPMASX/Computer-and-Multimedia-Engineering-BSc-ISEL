package dam.a48965.project_48965.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.project_48965.domain.MyGameDao
import dam.a48965.project_48965.network.responses.GameAPI
import dam.a48965.project_48965.network.responses.NetworkModule
import dam.a48965.project_48965.model.RecGameApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val myGameDao: MyGameDao) : ViewModel() {

    val gamesLiveData: MutableLiveData<List<RecGameApi>?> = MutableLiveData()
    private val gameAPI: GameAPI = NetworkModule.client
    var lastSearchQuery: String? = null
    private var currentPage = 0
    private val pageSize = 7
    var isLoading = false

    fun searchGames(query: String, page: Int = 0) {
        if (isLoading) return

        Log.d("SearchViewModel", "searchGames called with query: $query, page: $page")
        lastSearchQuery = query
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = NetworkModule.fetchAccessToken()
                val offset = page * pageSize
                val gamesResponse = gameAPI.searchGameByName(
                    authorization = "Bearer $accessToken",
                    fields = "name,cover,id,summary,total_rating,first_release_date, " +
                            "screenshots, similar_games, platforms; " +
                            "where name ~ \"$query\"* & version_parent = null & rating_count > 10 &" +
                            " summary != \"\" & category = 0;sort total_rating desc; limit $pageSize; offset $offset;"
                )

                if (gamesResponse.isSuccessful) {
                    val gamesList = gamesResponse.body()

                    // Sequentially load images for each game
                    gamesList?.forEach { game ->
                        game.imageUrl = accessToken?.let { fetchCoverImage(it, game.cover) }
                    }

                    if (page == 0) {
                        if (gamesList != null) {
                            gamesLiveData.postValue(gamesList.filterIsInstance<RecGameApi>())
                        }
                    } else {
                        val currentList: Set<RecGameApi> = gamesLiveData.value.orEmpty().toSet()
                        if (gamesList != null) {
                            val combinedSet = currentList + gamesList.filterIsInstance<RecGameApi>()
                            gamesLiveData.postValue(combinedSet.toList())
                        }
                    }
                    Log.d("SearchViewModel", "Data fetched successfully")
                } else {
                    Log.e("SearchViewModel", "API call unsuccessful: ${gamesResponse.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Exception during API call", e)
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun fetchCoverImage(accessToken: String, coverId: Int?): String? {
        return withContext(Dispatchers.IO) {
            if (coverId == null) return@withContext null
            val coverResponse = gameAPI.getCoverWithId(
                authorization = "Bearer $accessToken",
                fields = "image_id; where id = $coverId"
            )

            if (coverResponse.isSuccessful) {
                val coverList = coverResponse.body()
                val cover = coverList?.firstOrNull()
                cover?.let { "https://images.igdb.com/igdb/image/upload/t_cover_big/${it.image_id}.png" }
            } else {
                null
            }
        }
    }

    fun loadMoreGames() {
        currentPage++
        lastSearchQuery?.let {
            searchGames(it, currentPage)
        }
    }
}
