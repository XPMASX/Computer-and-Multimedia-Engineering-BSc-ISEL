package dam.a48965.pokedex.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.pokedex.domain.PokemonRepository
import dam.a48965.pokedex.domain.RegionRepository
import dam.a48965.pokedex.model.network.responses.NetworkModule
import dam.a48965.pokedex.model.network.responses.PokemonSpeciesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PokemonsViewModel : ViewModel(){
    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?>
        get() = _pokemons

    private lateinit var _repository: PokemonRepository
    fun initViewModel(pokemonRepository: PokemonRepository) {
        _repository = pokemonRepository
    }

    /*fun fetchPokemons(regionId: Int) {

        viewModelScope.launch(Dispatchers.Default) {

            val response = NetworkModule.client.fetchPokemonByRegionId(regionId)

            val pokemonList = response.pokemons.map { pokemonResponse ->

                // Extracting ID from the URL using regex
                val idRegex = "/(\\d+)/$".toRegex()
                val idMatchResult = idRegex.find(pokemonResponse.url ?: "")
                val id = idMatchResult?.groups?.get(1)?.value?.toIntOrNull() ?: 0

                // Extracting name directly from the response
                val name = pokemonResponse.name ?: ""

                // Constructing the image URL
                val imageUrl =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

                Pokemon(id, name, imageUrl)
            }.sortedBy { it.id }

            _pokemons.postValue(pokemonList)
        }

    }*/

    fun fetchPokemons(region: PokemonRegion) {
        viewModelScope.launch(Dispatchers.Default) {
            val pkList = _repository.getPokemonsByRegion(region)
            _pokemons.postValue(pkList.value)
        }
    }
}