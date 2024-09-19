package dam.a48965.project_48965.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dam.a48965.project_48965.MyApplication
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.FragmentSearchBinding
import dam.a48965.project_48965.model.RecGameApi
import dam.a48965.project_48965.ui.adapters.GameSearchAdapter

class SearchFragment : Fragment(), GameSearchAdapter.OnGameClickListener {

    private lateinit var viewModel: SearchViewModel
    private lateinit var gameListAdapter: GameSearchAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val myGameDao = (activity?.application as MyApplication).dbModule.myGameDao
        val viewModelFactory = SearchViewModelFactory(myGameDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchGames(it)
                    viewModel.lastSearchQuery = it
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchItem.expandActionView()
        searchView?.requestFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameListAdapter = GameSearchAdapter(requireActivity(), this, viewModel)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gameListAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!viewModel.isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                        viewModel.loadMoreGames()
                    }
                }
            })
        }

        viewModel.gamesLiveData.observe(viewLifecycleOwner, { games ->
            games?.let {
                gameListAdapter.submitList(it)
            }
        })
        viewModel.lastSearchQuery?.let {
            searchView?.setQuery(it, false)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.let {
            it.isIconified = false
            it.requestFocus()
        }
    }

    override fun onGameClick(game: RecGameApi) {
        val bundle = Bundle().apply {
            putParcelable("game", game)
        }
        findNavController().navigate(R.id.gameDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
