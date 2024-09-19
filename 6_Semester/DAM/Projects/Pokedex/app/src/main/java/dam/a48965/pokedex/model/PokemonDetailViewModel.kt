package dam.a48965.pokedex.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.pokedex.model.network.responses.NetworkModule
import dam.a48965.pokedex.model.network.responses.PokemonSpeciesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PokemonDetailViewModel : ViewModel(){
    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail>
        get() = _pokemonDetail

    fun fetchPokemonDetail(pokemon: Pokemon) {

        viewModelScope.launch(Dispatchers.Default) {

            val detailResponse = NetworkModule.client.fetchPokemonDetailById(pokemon.id)

            val speciesResponse =
                NetworkModule.client.fetchPokemonSpeciesDetailById(pokemon.id)
            val description =
                speciesResponse.flavorTextEntries?.getOrNull(6)?.flavorText ?: "No description available"

            val height = detailResponse.height ?: 0f
            val weight = detailResponse.weight ?: 0f

            val types = detailResponse.types?.map { it.type?.name ?: "" } ?: emptyList()

            val pokemonDetail = PokemonDetail(pokemon.id, pokemon, description, height, weight, types)
            _pokemonDetail.postValue(pokemonDetail)

        }

    }
}