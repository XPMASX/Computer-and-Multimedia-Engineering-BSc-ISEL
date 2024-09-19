package dam.a48965.pokedex.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class PokemonDetail(var id: Int,
                         var pokemon: Pokemon,
                         var description: String,
                         val weight: Float,
                         val height: Float,
                         val types: List<String>) : Parcelable
