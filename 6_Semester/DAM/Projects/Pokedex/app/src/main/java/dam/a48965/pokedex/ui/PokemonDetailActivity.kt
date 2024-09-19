package dam.a48965.pokedex.ui

import TypesAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dam.a48965.pokedex.databinding.ActivityPokemondetailBinding // Import the generated binding class
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dam.a48965.pokedex.model.Pokemon
import dam.a48965.pokedex.model.PokemonDetail
import dam.a48965.pokedex.model.PokemonDetailViewModel


class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemondetailBinding // Declare a binding variable
    private val viewModel: PokemonDetailViewModel by viewModels()
    private lateinit var typesAdapter: TypesAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Retrieve data passed from the previous activity
            val pokemonName = intent.getStringExtra("pokemon_name")
            val pokemonId = intent.getIntExtra("pokemon_id", 0)
            val pokemonImageUrl = intent.getStringExtra("pokemon_image_url")

            // Create a Pokemon object
            val pokemon = pokemonImageUrl?.let { pokemonName?.let { it1 ->
                Pokemon(pokemonId,
                    it1, it)
            } }

            // Set the data binding variable
            binding = ActivityPokemondetailBinding.inflate(layoutInflater)

            pokemon?.let { viewModel.fetchPokemonDetail(it) }

            // Observe the LiveData for changes in PokemonDetail
            viewModel.pokemonDetail.observe(this) { pokemonDetail ->
                // Set the PokemonDetail object to the binding
                binding.pokemonDetail = pokemonDetail

                typesAdapter = TypesAdapter(pokemonDetail.types ?: emptyList(), this)
                binding.typesRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@PokemonDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    adapter = typesAdapter
                }

                setContentView(binding.root)

                // Set click listener for back arrow
                binding.arrowBack.setOnClickListener {
                    finish() // Go back to the previous page
                }
            }

    }
}
