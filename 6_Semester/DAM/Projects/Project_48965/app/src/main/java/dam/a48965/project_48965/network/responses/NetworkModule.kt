package dam.a48965.project_48965.network.responses

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dam.a48965.project_48965.domain.IntListAdapter
import dam.a48965.project_48965.domain.StringListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NetworkModule {

    private const val BASE_URL = "https://api.igdb.com/v4/"
    private const val CLIENT_ID = "sru4hc4f27oh5znn6lzunws7q2lf7d"
    private const val CLIENT_SECRET = "v3akel331pw4fxl9ekrk3o6bo9ld2a"

    private var accessToken: String? = null

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(IntListAdapter())
        .add(StringListAdapter())
        .build()

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .header("Client-ID", CLIENT_ID)
                accessToken?.let {
                    requestBuilder.header("Authorization", "Bearer $it")
                }
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun provideGameApiService(retrofit: Retrofit): GameAPI {
        return retrofit.create(GameAPI::class.java)
    }

    val client: GameAPI by lazy {
        val okHttpClient = provideOkHttpClient()
        val retrofit = provideRetrofit(okHttpClient)
        provideGameApiService(retrofit)
    }

    suspend fun fetchAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            val url = URL("https://id.twitch.tv/oauth2/token?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET&grant_type=client_credentials")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                // Parse the response to get the access token
                val jsonObject = org.json.JSONObject(response)
                accessToken = jsonObject.getString("access_token")

                // Log the response
                Log.d("NetworkModule", "Access Token: $accessToken")
            } else {
                // Handle error
                accessToken = null
                Log.e("NetworkModule", "Failed to get access token. Response Code: $responseCode")
            }

            connection.disconnect()
            accessToken
        }
    }
}
