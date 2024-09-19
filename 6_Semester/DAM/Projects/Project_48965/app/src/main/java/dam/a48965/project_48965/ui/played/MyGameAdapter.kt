package dam.a48965.project_48965.ui.played

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.ItemMygameBinding
import dam.a48965.project_48965.model.RecGameApi

class MyGameAdapter : ListAdapter<RecGameApi, MyGameAdapter.MyGameViewHolder>(MyGameComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGameViewHolder {
        val binding = ItemMygameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyGameViewHolder, position: Int) {
        val currentGame = getItem(position)
        holder.bind(currentGame)
    }

    inner class MyGameViewHolder(private val binding: ItemMygameBinding) : RecyclerView.ViewHolder(binding.root) {
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
                Glide.with(imageGameCover.context)
                    .load(game.imageUrl)
                    .into(imageGameCover)
                textGameTitle.text = game.name
            }
        }
    }

    class MyGameComparator : DiffUtil.ItemCallback<RecGameApi>() {
        override fun areItemsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecGameApi, newItem: RecGameApi): Boolean {
            return oldItem == newItem
        }
    }
}