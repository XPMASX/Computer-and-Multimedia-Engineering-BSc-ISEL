package dam.a48965.project_48965.ui.played

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dam.a48965.project_48965.MyApplication
import dam.a48965.project_48965.databinding.FragmentPlayedBinding



class PlayedFragment : Fragment() {

    private var _binding: FragmentPlayedBinding? = null
    private val binding get() = _binding!!

    private val myGameDao by lazy { (activity?.application as MyApplication).dbModule.myGameDao }
    private val userId by lazy { Firebase.auth.currentUser?.uid }

    private val viewModel: PlayedViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PlayedViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PlayedViewModel(myGameDao, userId ?: "") as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchGames()
        val adapter = MyGameAdapter()
        val spanCount = 2
        val spacing = 20 // Adjust the spacing as needed
        val includeEdge = true

        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        binding.recyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchGames()
        }

        viewModel.allMyGames.observe(viewLifecycleOwner) { games ->
            // Update the cached copy of the games in the adapter.
            adapter.submitList(games)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}