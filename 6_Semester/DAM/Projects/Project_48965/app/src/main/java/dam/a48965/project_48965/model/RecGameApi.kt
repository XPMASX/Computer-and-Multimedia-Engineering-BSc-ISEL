package dam.a48965.project_48965.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
@Entity(tableName = "rec_game_api")
@JsonClass(generateAdapter = true)
data class RecGameApi(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "summary")
    val summary: String,

    @Json(name = "total_rating")
    val rating: Float? = null,

    @Json(name = "first_release_date")
    val releaseDate: Long? = null,

    @Json(name = "screenshots")
    val screenshots: List<Int>? = null,

    var screenshotsUrls: List<String>? = null,

    @Json(name = "similar_games")
    val similarGamesIds: List<Int>? = null,

    @Json(name = "platforms")
    val platforms: List<Int>? = null,

    var platformNames: List<String>? = null,

    @Json(name = "cover")
    val cover: Int?,

    var imageUrl: String? = null
) : Parcelable {

    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(releaseDate!! * 1000L))
    }
}
