package dam.a48965.project_48965.ui.home

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.project_48965.network.responses.GameAPI
import dam.a48965.project_48965.network.responses.NetworkModule
import dam.a48965.project_48965.model.RecGameApi
import kotlinx.coroutines.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val gameAPI: GameAPI = NetworkModule.client

    private val _topGames = MutableLiveData<List<RecGameApi>>()
    val topGames: LiveData<List<RecGameApi>> get() = _topGames

    private val _recentGames = MutableLiveData<List<RecGameApi>>()
    val recentGames: LiveData<List<RecGameApi>> get() = _recentGames

    private var lastApiCallTime = 0L
    private val rateLimitIntervalMillis = TimeUnit.SECONDS.toMillis(3) / 4 // 4 requests per second

    private val concurrentRequestsSemaphore = Semaphore(8)

    init {
        fetchTopGames()
        fetchRecentGames()
    }

    private fun fetchTopGames() {
        viewModelScope.launch {
            try {
                val accessToken = NetworkModule.fetchAccessToken()
                fetchGames(accessToken, _topGames) {
                    gameAPI.searchTopGames("Bearer $accessToken")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception during API call", e)
                _topGames.postValue(emptyList())
            }
        }
    }

    private fun fetchRecentGames() {
        viewModelScope.launch {
            try {
                val accessToken = NetworkModule.fetchAccessToken()
                val timestamp = System.currentTimeMillis()
                fetchGames(accessToken, _recentGames) {
                    gameAPI.searchRecentGames(
                        "Bearer $accessToken",
                        "name,cover,id,summary,total_rating,first_release_date,screenshots,similar_games,platforms; " +
                                "where first_release_date < $timestamp & total_rating_count > 1;" +
                                " sort first_release_date desc; limit 15;"
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception during API call", e)
                _recentGames.postValue(emptyList())
            }
        }
    }

    private suspend fun fetchGames(
        accessToken: String?,
        liveData: MutableLiveData<List<RecGameApi>>,
        fetchGames: suspend () -> retrofit2.Response<List<RecGameApi>>
    ) {
        if (accessToken == null) return
        val newGames = mutableListOf<RecGameApi>()
        val response = fetchGames()
        if (response.isSuccessful) {
            val gamesList = response.body()
            gamesList?.forEach { game ->
                rateLimitCall()
                concurrentRequestsSemaphore.acquire()
                game.imageUrl = fetchCoverImage(accessToken, game.cover)
                concurrentRequestsSemaphore.release()
                newGames.add(game)
                liveData.postValue(newGames.toList())
            }
        } else {
            Log.e("HomeViewModel", "API call unsuccessful: ${response.errorBody()}")
        }
    }

    private suspend fun rateLimitCall() {
        val currentTime = SystemClock.elapsedRealtime()
        val timeSinceLastCall = currentTime - lastApiCallTime
        if (timeSinceLastCall < rateLimitIntervalMillis) {
            delay(rateLimitIntervalMillis - timeSinceLastCall + 10) // Increase the delay by a small buffer
        }
        lastApiCallTime = SystemClock.elapsedRealtime()
    }

    private suspend fun fetchCoverImage(accessToken: String, coverId: Int?): String? {
        return withContext(Dispatchers.IO) {
            rateLimitCall()
            if (coverId == null) return@withContext null
            val coverResponse = gameAPI.getCoverWithId("Bearer $accessToken", "image_id; where id = $coverId")
            if (coverResponse.isSuccessful) {
                val coverList = coverResponse.body()
                coverList?.firstOrNull()?.let { "https://images.igdb.com/igdb/image/upload/t_cover_big/${it.image_id}.png" }
            } else {
                null
            }
        }
    }
}
