package dam.a48965.project_48965.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.ItemSearchBinding
import dam.a48965.project_48965.model.RecGameApi
import dam.a48965.project_48965.ui.search.SearchViewModel

class GameSearchAdapter(
    private val context: FragmentActivity,
    private val listener: OnGameClickListener,
    private val viewModel: SearchViewModel
) : ListAdapter<RecGameApi, GameSearchAdapter.GameViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    interface OnGameClickListener {
        fun onGameClick(game: RecGameApi)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)

        if (position == itemCount - 1) {
            viewModel.loadMoreGames()
        }
    }

    inner class GameViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val game = getItem(position)
                    listener.onGameClick(game)
                }
            }
        }

        fun bind(game: RecGameApi) {
            binding.game = game
            Glide.with(context)
                .load(game.imageUrl)
                .placeholder(R.drawable.placeholder_cover)
                .into(binding.gameImage)
            binding.executePendingBindings()
        }
    }

    class GameDiffCallback : DiffUtil.ItemCallback<RecGameApi>() {
        override fun areItemsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem == newItem
        }
    }
}
