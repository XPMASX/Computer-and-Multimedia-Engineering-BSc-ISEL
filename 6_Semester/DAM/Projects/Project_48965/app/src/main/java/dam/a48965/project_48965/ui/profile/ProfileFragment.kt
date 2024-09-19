package dam.a48965.project_48965.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.identity.Identity
import dam.a48965.project_48965.MyApplication
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.FragmentProfileBinding
import dam.a48965.project_48965.ui.MainViewModel
import dam.a48965.project_48965.presentation.sign_in.GoogleAuthUiClient
import dam.a48965.project_48965.ui.LoginActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()


    private val googleAuthUiClient by lazy {
        val context = requireContext()
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    private val myGameDao by lazy {
        (activity?.application as MyApplication).dbModule.myGameDao
    }

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(myGameDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user?.let { user ->
            // Load the image
            Glide.with(this)
                .load(user.photoUrl)
                .into(binding.profilePicture)

            // Set the username
            binding.username.text = user.displayName
        }

        lifecycleScope.launch {
            val playedGamesCount = viewModel.countPlayedGames()
            binding.numberPlayedGames.text = playedGamesCount.toString()

            val backlogCount = viewModel.countBacklogGames()
            binding.numberBacklog.text = backlogCount.toString()
        }


        binding.buttonChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.nav_change_profile)
        }

        val signOutButton: Button = view.findViewById(R.id.button_sign_out)
        signOutButton.setOnClickListener {
            lifecycleScope.launch {
                googleAuthUiClient.signOut()
                mainViewModel.signOut()
                mainViewModel.updateFirebaseUser()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

}