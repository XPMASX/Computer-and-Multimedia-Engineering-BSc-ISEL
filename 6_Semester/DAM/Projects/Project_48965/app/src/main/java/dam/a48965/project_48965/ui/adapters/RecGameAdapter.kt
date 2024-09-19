package dam.a48965.project_48965.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.ItemRecGameBinding
import dam.a48965.project_48965.model.RecGameApi

class RecGameAdapter : ListAdapter<RecGameApi, RecGameAdapter.RecGameViewHolder>(RecGameComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecGameViewHolder {
        val binding = ItemRecGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecGameViewHolder, position: Int) {
        val currentGame = getItem(position)
        holder.bind(currentGame)
    }

    inner class RecGameViewHolder(private val binding: ItemRecGameBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val game = getItem(position)
                    val bundle = Bundle().apply {
                        putParcelable("game", game)
                    }
                    it.findNavController().navigate(R.id.gameDetailFragment, bundle)
                }
            }
        }

        fun bind(game: RecGameApi) {
            binding.apply {
                Glide.with(imageGameCover.context).load(game.imageUrl).into(imageGameCover)
                textGameTitle.text = game.name
            }
        }
    }

    class RecGameComparator : DiffUtil.ItemCallback<RecGameApi>() {
        override fun areItemsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem == newItem
        }
    }
}
