package dam.a48965.project_48965.ui.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.FragmentAboutBinding
import dam.a48965.project_48965.ui.MainViewModel
import dam.a48965.project_48965.presentation.sign_in.GoogleAuthUiClient
import dam.a48965.project_48965.ui.LoginActivity
import kotlinx.coroutines.launch

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()


    private val googleAuthUiClient by lazy {
        val context = requireContext()
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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