package dam.a48965.project_48965.network.responses

import dam.a48965.project_48965.model.CoverGameApi
import dam.a48965.project_48965.model.PlatformGameApi
import dam.a48965.project_48965.model.RecGameApi
import dam.a48965.project_48965.model.ScreenshotGameApi
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GameAPI {
    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("games")
    suspend fun searchTopGames(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String = "name,cover,id,summary,total_rating," +
                "first_release_date,screenshots, similar_games, platforms;" +
                " sort total_rating desc; where rating_count > 200 & version_parent = null & category = 0;" +
                " limit 25;"
    ): Response<List<RecGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("games")
    suspend fun searchRecentGames(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<RecGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("games")
    suspend fun searchGameByName(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<RecGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("games")
    suspend fun searchGameById(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<RecGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("covers")
    suspend fun getCoverWithId(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<CoverGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("screenshots")
    suspend fun getScreenshotWithId(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<ScreenshotGameApi>>

    @Headers("Client-ID: sru4hc4f27oh5znn6lzunws7q2lf7d")
    @POST("platforms")
    suspend fun getPlatformWithId(
        @Header("Authorization") authorization: String,
        @Query("fields") fields: String
    ): Response<List<PlatformGameApi>>
}
