package dam.a48965.project_48965.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.sql.Date

@kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "MyGame")
data class MyGame(
    @PrimaryKey
    @ColumnInfo(name = "MyGame_id")
    var id: Int,

    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "MyGame_rating")
    var rating: Float? = null,

    @ColumnInfo(name = "MyGame_review")
    var review:String,

    @ColumnInfo(name = "MyGame_played")
    var played: Boolean? = null,

    @ColumnInfo(name = "MyGame_backlog")
    var backlog: Boolean? = null,

) : Parcelable
