package dam.a48965.project_48965.ui.gameDetail

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.project_48965.domain.MyGameDao
import dam.a48965.project_48965.network.responses.GameAPI
import dam.a48965.project_48965.network.responses.NetworkModule
import dam.a48965.project_48965.model.RecGameApi
import kotlinx.coroutines.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class GameDetailViewModel(private val gameAPI: GameAPI, private val myGameDao: MyGameDao) : ViewModel() {

    private val _game = MutableLiveData<RecGameApi?>()
    val game: LiveData<RecGameApi?> get() = _game

    private val _similarGames = MutableLiveData<List<RecGameApi>?>()
    val similarGames: LiveData<List<RecGameApi>?> get() = _similarGames

    private var lastApiCallTime = 0L
    private val rateLimitIntervalMillis = TimeUnit.SECONDS.toMillis(4) / 4 // 4 requests per second

    private val concurrentRequestsSemaphore = Semaphore(8)

    fun setGame(game: RecGameApi) {
        _game.value = game
        if (game.platformNames.isNullOrEmpty()) {
            fetchPlatformNames(game.platforms)
        } else if (game.screenshotsUrls.isNullOrEmpty()) {
            Log.d("GameDetailViewModel", "Screenshots URLs are already set:" + game.screenshotsUrls)
            fetchScreenshots(game.screenshots ?: emptyList())
        } else if (_similarGames.value.isNullOrEmpty()) {
            fetchSimilarGames(game.similarGamesIds ?: emptyList())
        }
    }

    private fun fetchPlatformNames(platformIds: List<Int>?) {
        viewModelScope.launch {
            val accessToken = NetworkModule.fetchAccessToken()
            platformIds?.let {
                val platformNames = fetchPlatformNames(accessToken, it)
                platformNames?.let {
                    _game.postValue(_game.value?.apply {
                        this.platformNames = it
                    })
                    if (_game.value?.screenshotsUrls.isNullOrEmpty()) {
                        fetchScreenshots(_game.value?.screenshots ?: emptyList())
                    } else {
                        fetchSimilarGames(_game.value?.similarGamesIds ?: emptyList())
                    }
                }
            }
        }
    }

    private suspend fun fetchPlatformNames(accessToken: String?, platformIds: List<Int>): List<String>? {
        return withContext(Dispatchers.IO) {
            platformIds.mapNotNull { platformId ->
                val response = gameAPI.getPlatformWithId(
                    authorization = "Bearer $accessToken",
                    fields = "name; where id = $platformId;"
                )
                if (response.isSuccessful) {
                    response.body()?.firstOrNull()?.name
                } else {
                    Log.e("GameDetailViewModel", "Failed to fetch platform name: ${response.errorBody()?.string()}")
                    null
                }
            }
        }
    }

    private fun fetchScreenshots(screenshotIds: List<Int>) {
        viewModelScope.launch {
            try {
                    val topScreenshotIds = screenshotIds.take(1)
                    val accessToken = NetworkModule.fetchAccessToken()
                    topScreenshotIds.map { id ->
                        launch {
                            rateLimitCall()
                            try {
                                val url = fetchScreenshotUrl(accessToken ?: return@launch, id)
                                url?.let {
                                    _game.postValue(_game.value?.apply {
                                        screenshotsUrls = (screenshotsUrls ?: emptyList()) + it
                                    })
                                    myGameDao.updateScreenshotUrl(_game.value?.id ?: return@launch, listOf(it))
                                }
                            } finally {
                            }
                        }
                    }
                    fetchSimilarGames(_game.value?.similarGamesIds ?: emptyList())
            } catch (e: Exception) {
                Log.e("GameDetailViewModel", "Exception in fetching screenshots", e)
            }
        }
    }

    private suspend fun fetchScreenshotUrl(accessToken: String, screenshotId: Int): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = gameAPI.getScreenshotWithId(
                    authorization = "Bearer $accessToken",
                    fields = "image_id; where id = $screenshotId;"
                )
                if (response.isSuccessful) {
                    response.body()?.firstOrNull()?.let {
                        "https://images.igdb.com/igdb/image/upload/t_screenshot_big/${it.image_id}.jpg"
                    }
                } else {
                    Log.e("GameDetailViewModel", "Failed to fetch screenshot: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("GameDetailViewModel", "Exception during API call", e)
                null
            }
        }
    }

    private fun fetchSimilarGames(similarGameIds: List<Int>) {
        viewModelScope.launch {
            try {
                val topSimilarGameIds = similarGameIds.take(5)
                    val accessToken = NetworkModule.fetchAccessToken()
                    topSimilarGameIds.map { id ->
                        launch {
                            rateLimitCall()
                            concurrentRequestsSemaphore.acquire()
                            try {
                                val game = fetchGameDetails(accessToken ?: return@launch, id)
                                game?.let {
                                    _similarGames.postValue((_similarGames.value ?: emptyList()) + it)
                                }
                            } finally {
                                concurrentRequestsSemaphore.release()
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("GameDetailViewModel", "Exception in fetching similar games", e)
            }
        }
    }

    private suspend fun fetchGameDetails(accessToken: String, gameId: Int): RecGameApi? {
        return withContext(Dispatchers.IO) {
            try {
                val response = gameAPI.searchGameById(
                    authorization = "Bearer $accessToken",
                    fields = "name,cover,id,summary,total_rating,first_release_date,screenshots,similar_games,platforms;where id = $gameId;"
                )
                if (response.isSuccessful) {
                    val game = response.body()?.firstOrNull()
                    game?.let {
                        it.imageUrl = fetchCoverImage(accessToken, it.cover)
                    }
                    game
                } else {
                    Log.e("GameDetailViewModel", "Failed to fetch game details: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("GameDetailViewModel", "Exception during API call", e)
                null
            }
        }
    }

    private suspend fun fetchCoverImage(accessToken: String, coverId: Int?): String? {
        return withContext(Dispatchers.IO) {
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

    private suspend fun rateLimitCall() {
        val currentTime = SystemClock.elapsedRealtime()
        val timeSinceLastCall = currentTime - lastApiCallTime
        if (timeSinceLastCall < rateLimitIntervalMillis) {
            delay(rateLimitIntervalMillis - timeSinceLastCall + 10) // Add a small buffer to the delay
        }
        lastApiCallTime = SystemClock.elapsedRealtime()
    }
}
