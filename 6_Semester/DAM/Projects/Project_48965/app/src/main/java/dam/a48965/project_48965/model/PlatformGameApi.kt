package dam.a48965.project_48965.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlatformGameApi(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)
