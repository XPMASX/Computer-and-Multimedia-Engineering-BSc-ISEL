package dam.a48965.project_48965.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dam.a48965.project_48965.databinding.FragmentHomeBinding
import dam.a48965.project_48965.ui.adapters.RecGameAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var topGamesAdapter: RecGameAdapter
    private lateinit var recentGamesAdapter: RecGameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topGamesAdapter = RecGameAdapter()
        recentGamesAdapter = RecGameAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topGamesAdapter
        }

        binding.recyclerViewRecentGames.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentGamesAdapter
        }

        viewModel.topGames.observe(viewLifecycleOwner, Observer { games ->
            games?.let { topGamesAdapter.submitList(it) }
        })

        viewModel.recentGames.observe(viewLifecycleOwner, Observer { games ->
            games?.let { recentGamesAdapter.submitList(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
