import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dam.a48965.pokedex.R

class TypesAdapter(private val types: List<String>, private val context: Context) : RecyclerView.Adapter<TypesAdapter.TypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_types, parent, false)
        return TypeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val type = types[position]
        holder.bind(type)
    }

    override fun getItemCount() = types.size

    inner class TypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        private val typeImageView: ImageView = itemView.findViewById(R.id.typeImageView)

        fun bind(type: String) {
            typeTextView.text = type

            val drawableId = context.resources.getIdentifier(type.toLowerCase(), "drawable", context.packageName)
            if (drawableId != 0) {
                typeImageView.setImageResource(drawableId)
            }
        }
    }
}
