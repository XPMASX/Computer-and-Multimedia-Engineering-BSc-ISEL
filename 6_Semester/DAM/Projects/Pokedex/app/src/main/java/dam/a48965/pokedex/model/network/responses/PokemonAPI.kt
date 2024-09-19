package dam.a48965.pokedex.model.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi
{
    @GET("region")
    suspend fun fetchRegionList(): PokemonListBaseResponse<PokemonRegionsResponse>

    @GET("generation/{id}")
    suspend fun fetchPokemonByRegionId(@Path("id") id:Int): PokemonByRegionResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id:Int): PokemonDetailResponse

    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpeciesDetailById(@Path("id") id: Int): PokemonSpeciesResponse


     /*@GET("type")
     suspend fun fetchPokemonTypes(): PokemonListBaseResponse<PokemonGenericResponse>*/

}

@JsonClass(generateAdapter = true)
data class PokemonListBaseResponse<T>(
    @field:Json(name = "count") val count: Int?,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "results") val results: List<T>?
)

@JsonClass(generateAdapter = true)
data class PokemonRegionsResponse(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class PokemonByRegionResponse(
    @field:Json(name = "pokemon_species") val pokemons: List<PokemonResponse>,
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "species") val species: PokemonSpeciesResponse?
)
@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @field:Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>?
)

@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    @field:Json(name = "flavor_text") val flavorText: String?,
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "height") val height: Float?,
    @field:Json(name = "weight") val weight: Float?,
    @field:Json(name = "types") val types: List<PokemonType>?,
)

@JsonClass(generateAdapter = true)
data class PokemonType(
    @field:Json(name = "type") val type: Type?
)

@JsonClass(generateAdapter = true)
data class Type(
    @field:Json(name = "name") val name: String?,
){}