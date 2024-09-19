package dam.a48965.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dam.a48965.pokedex.R
import dam.a48965.pokedex.model.Pokemon
import dam.a48965.pokedex.model.PokemonsAdapter
import androidx.activity.viewModels
import dam.a48965.pokedex.domain.DBModule
import dam.a48965.pokedex.model.PokemonDetail
import dam.a48965.pokedex.model.PokemonRegion
import dam.a48965.pokedex.model.PokemonsViewModel


class PokemonListActivity : AppCompatActivity() {
    val viewModel: PokemonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemonlist)
        val region = intent.getParcelableExtra<PokemonRegion>("region")

        val listView = findViewById<RecyclerView>(R.id.pksRecyclerView)

        viewModel.initViewModel(DBModule.getInstance(this).pokemonRepository)

        viewModel.pokemons.observe(this) {pokemon  ->
            val pokemons = pokemon?.map { it } ?: emptyList()
            listView.adapter = pokemon ?.let {
                PokemonsAdapter(
                    pokemonList = pokemons,
                    context = this,
                    itemClickedListener = { pokemon -> navigateToPokemonDetail(pokemon) }
                )
            }
        }
        if (region != null) {
            viewModel.fetchPokemons(region)
        }

    }

    private fun navigateToPokemonDetail(pokemon: Pokemon) {
        // Create an Intent to navigate to the Pokemon detail activity
        val intent = Intent(this, PokemonDetailActivity::class.java)
        // Pass the clicked Pokemon's data to the detail activity using Intent extras
        intent.putExtra("pokemon_name", pokemon.name)
        intent.putExtra("pokemon_id", pokemon.id)
        intent.putExtra("pokemon_image_url", pokemon.imageUrl)
        // Start the detail activity
        startActivity(intent)

    }
}
