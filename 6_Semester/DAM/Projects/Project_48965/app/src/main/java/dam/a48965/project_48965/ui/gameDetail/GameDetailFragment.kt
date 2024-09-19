package dam.a48965.project_48965.ui.gameDetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dam.a48965.project_48965.MyApplication
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.FragmentGameBinding
import dam.a48965.project_48965.model.MyGame
import dam.a48965.project_48965.network.responses.NetworkModule
import dam.a48965.project_48965.model.RecGameApi
import dam.a48965.project_48965.ui.adapters.RecGameAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val myGameDao by lazy {
        (activity?.application as MyApplication).dbModule.myGameDao
    }
    private val viewModel: GameDetailViewModel by viewModels {
        GameDetailViewModelFactory(NetworkModule.client, myGameDao)
    }

    private val dbModule by lazy {
        (activity?.application as MyApplication).dbModule
    }

    private lateinit var similarGamesAdapter: RecGameAdapter

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        // Hide the toolbar
        (activity as AppCompatActivity).supportActionBar?.hide()

        // Enable the back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        // Initialize the adapter and set it to the RecyclerView early
        similarGamesAdapter = RecGameAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarGamesAdapter
        }

        // Get the game object from the bundle
        var game = arguments?.getParcelable<RecGameApi>("game")

        lifecycleScope.launch {
            val gameExists = withContext(Dispatchers.IO) {
                myGameDao.isGameExists(game?.id ?: 0)
            }
            if (!gameExists) {
                game?.let { viewModel.setGame(it) }
            } else {
                lifecycleScope.launch {
                    val existingGame = withContext(Dispatchers.IO) {
                        myGameDao.getRecGameById(game?.id ?: 0)
                    }
                    existingGame?.let { viewModel.setGame(it) }
                    Log.d("GameDetailFragment", "Game: $existingGame")
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val userId = dbModule.userId ?: ""

        // Observe the game LiveData
        viewModel.game.observe(viewLifecycleOwner) { game ->
            // Populate the views with the game details
            binding.textGameTitle.text = game?.name
            binding.textGameReleaseDate.text = game?.getFormattedDate()
            binding.textGameSummary.text = game?.summary
            binding.textGamePlatforms.text = game?.platformNames?.joinToString(", ")
            binding.textRating.text = game?.rating.toString()

            // Load the game cover image into the ImageView
            Glide.with(requireContext())
                .load(game?.imageUrl)
                .placeholder(R.drawable.placeholder_cover)
                .into(binding.imageGameCover)

            Glide.with(requireContext())
                .load(game?.screenshotsUrls?.firstOrNull())
                .placeholder(R.drawable.loading)
                .into(binding.imageGameArtwork)
        }

        viewModel.similarGames.observe(viewLifecycleOwner, Observer { games ->
            games?.let { similarGamesAdapter.submitList(it) }
        })

        // Get the game ID
        val gameId = game?.id ?: 0

        // Initialize the states of the ImageButtons
        var isPlayed = false
        var isBacklog = false

        // Check if a MyGame with the same ID already exists
        lifecycleScope.launch {
            val existingMyGame = withContext(Dispatchers.IO) {
                myGameDao.getMyGameByIdAndUserId(gameId, userId)
            }

            // If it does, update the UI accordingly
            existingMyGame?.let { myGame ->
                isPlayed = myGame.played ?: false
                isBacklog = myGame.backlog ?: false
                binding.ratingBar.rating = myGame.rating ?: 0f

                // Update the 'Played' ImageButton
                val playedImageResource = if (isPlayed) R.drawable.ic_played else R.drawable.no_play
                binding.buttonPlayed.setImageResource(playedImageResource)

                // Update the 'Backlog' ImageButton
                val backlogImageResource =
                    if (isBacklog) R.drawable.ic_backlog else R.drawable.no_backlog
                binding.buttonBacklog.setImageResource(backlogImageResource)

                binding.editTextReview.text =
                    Editable.Factory.getInstance().newEditable(myGame.review)
            }
        }

        // Set a listener for the RatingBar
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

            isPlayed = true
            // Create a new MyGame object with the game ID and rating
            val myGame = MyGame(
                id = game?.id ?: 0,
                userId = userId,
                rating = rating,
                review = binding.editTextReview.text.toString(), // Add your review here
                played = isPlayed, // Set as played
                backlog = isBacklog
            )

            // Update the 'Played' ImageButton
            binding.buttonPlayed.setImageResource(R.drawable.ic_played)

            insertGame(viewModel.game.value)

            // Insert the MyGame object into the database in a background thread
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    myGameDao.insert(myGame)
                }
            }
        }

        // Set a listener for the 'Played' ImageButton
        binding.buttonPlayed.setOnClickListener {
            // Cast 'it' to ImageButton
            val button = it as ImageButton

            // Flip the state and update the image
            isPlayed = !isPlayed
            val imageResource = if (isPlayed) R.drawable.ic_played else R.drawable.no_play
            button.setImageResource(imageResource)

            // Create a new MyGame object with the game ID and button information
            val myGame = MyGame(
                id = game?.id ?: 0,
                userId = userId,
                rating = binding.ratingBar.rating,
                review = "", // Add your review here
                played = isPlayed,
                backlog = isBacklog
            )

            insertGame(viewModel.game.value)

            // Insert the MyGame object into the database in a background thread
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    myGameDao.insert(myGame)
                }
            }
        }

        binding.editTextReview.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val reviewText = s.toString()
                if (reviewText.isNotBlank()) {
                    isPlayed = true // Set as played if review text is not blank

                    // Create a new MyGame object with the game ID and review text
                    val myGame = MyGame(
                        id = game?.id ?: 0,
                        userId = userId,
                        rating = binding.ratingBar.rating,
                        review = reviewText,
                        played = isPlayed,
                        backlog = isBacklog
                    )

                    // Update the 'Played' ImageButton
                    binding.buttonPlayed.setImageResource(R.drawable.ic_played)

                    insertGame(viewModel.game.value)

                    // Insert the MyGame object into the database in a background thread
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            myGameDao.insert(myGame)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed here
            }
        })

        // Set a listener for the 'Backlog' ImageButton
        binding.buttonBacklog.setOnClickListener {
            // Cast 'it' to ImageButton
            val button = it as ImageButton

            // Flip the state and update the image
            isBacklog = !isBacklog
            val imageResource = if (isBacklog) R.drawable.ic_backlog else R.drawable.no_backlog
            button.setImageResource(imageResource)

            // Create a new MyGame object with the game ID and button information
            val myGame = MyGame(
                id = game?.id ?: 0,
                userId = userId,
                rating = binding.ratingBar.rating,
                review = "", // Add your review here
                played = isPlayed,
                backlog = isBacklog
            )

            insertGame(game)
            // Insert the MyGame object into the database in a background thread
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    myGameDao.insert(myGame)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Clear all menu items
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = null
    }

    override fun onStop() {
        super.onStop()

        // Check if the current destination is not another GameDetailFragment
        if (navController.currentDestination?.id != R.id.gameDetailFragment) {
            // Show the toolbar
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }

    private fun insertGame(game: RecGameApi?) {
        val gameToInsert = RecGameApi(
            id = game?.id ?: 0,
            name = game?.name ?: "",
            summary = game?.summary ?: "",
            rating = game?.rating ?: 0f,
            releaseDate = game?.releaseDate ?: 0L,
            imageUrl = game?.imageUrl ?: "",
            screenshotsUrls = game?.screenshotsUrls ?: emptyList(),
            similarGamesIds = game?.similarGamesIds ?: emptyList(),
            platforms = game?.platforms ?: emptyList(),
            platformNames = game?.platformNames ?: emptyList(),
            cover = null
        )
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                myGameDao.insertRecGame(gameToInsert)
            }
        }
    }
}
