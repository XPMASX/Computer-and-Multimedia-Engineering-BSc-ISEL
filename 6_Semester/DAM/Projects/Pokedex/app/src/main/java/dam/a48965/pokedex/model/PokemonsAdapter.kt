package dam.a48965.pokedex.model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import dam.a48965.pokedex.R
import com.bumptech.glide.request.target.Target
import dam.a48965.pokedex.databinding.ItemPokemonBinding


class PokemonsAdapter(
    private val pokemonList: List<Pokemon>,
    private val context: Context,
    private val itemClickedListener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pkNameTextView = itemView.findViewById<AppCompatTextView>(R.id.pkName)
        val pkIDTextView = itemView.findViewById<AppCompatTextView>(R.id.pkID)
        private val pokemonItemBinding = ItemPokemonBinding.bind(itemView)
        fun bindView(pokemon: Pokemon, itemClickedListener: (Pokemon) -> Unit) {
            pokemonItemBinding.pokemon = pokemon
            itemView.setOnClickListener {
                itemClickedListener.invoke(pokemon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.bindView(pokemon, itemClickedListener)

        holder.pkNameTextView.text = pokemon.name
        holder.pkIDTextView.text = "#" + pokemon.id
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}