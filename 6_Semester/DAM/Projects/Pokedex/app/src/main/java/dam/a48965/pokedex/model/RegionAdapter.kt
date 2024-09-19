package dam.a48965.pokedex.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam.a48965.pokedex.R
import dam.a48965.pokedex.databinding.ItemRegionBinding

class RegionAdapter(
    private val pkRegionList: List<PokemonRegion>,
    private val context: Context,
    private val itemClickedListener: (PokemonRegion) -> Unit
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val regionItemBinding = ItemRegionBinding.bind(itemView)
        fun bindView(region: PokemonRegion, itemClickedListener: (PokemonRegion) -> Unit) {
            regionItemBinding.region = region
            itemView.setOnClickListener {
                itemClickedListener.invoke(region)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = pkRegionList[position]
        holder.bindView(region, itemClickedListener)
    }

    override fun getItemCount(): Int {
        return pkRegionList.size
    }


}