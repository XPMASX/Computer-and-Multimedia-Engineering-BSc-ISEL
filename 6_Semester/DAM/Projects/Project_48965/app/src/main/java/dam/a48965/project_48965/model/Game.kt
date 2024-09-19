// File: Game.kt
package dam.a48965.project_48965.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date
import java.util.Locale

@kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "game")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "game_id")
    var id: Int,

    @ColumnInfo(name = "game_name")
    var name:String,

    @ColumnInfo(name = "game_summary")
    var summary:String,

    @ColumnInfo(name = "game_imgUrl")
    var imageUrl: String,

    @ColumnInfo(name = "game_rating")
    var rating: Float? = null,

    @ColumnInfo(name = "game_releaseDate")
    var releaseDate: Long? = null,  // Changed from java.sql.Date to String

    @ColumnInfo(name = "game_company")
    var company: String? = null

) : Parcelable {
    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(releaseDate!! * 1000L))
    }
}