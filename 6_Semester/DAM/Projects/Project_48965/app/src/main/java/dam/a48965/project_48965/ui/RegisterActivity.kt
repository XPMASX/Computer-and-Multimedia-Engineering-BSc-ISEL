package dam.a48965.project_48965.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dam.a48965.project_48965.databinding.ActivityRegisterBinding
import dam.a48965.project_48965.presentation.sign_in.GoogleAuthUiClient
import dam.a48965.project_48965.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {

    private val viewModel: SignInViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private var firebaseUserLiveData: MutableLiveData<FirebaseUser> =
        MutableLiveData<FirebaseUser>()
    private lateinit var auth: FirebaseAuth

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result -> mainViewModel.updateFirebaseUser() }

    fun startSignIn() {
        val intent = AuthUI.getInstance().createSignInIntentBuilder().build()
        signInLauncher.launch(intent)
    }

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Check if user is already signed in
        if (googleAuthUiClient.getSignedInUser() != null) {
            navigateToMainActivity()
            return
        }

        firebaseUserLiveData = mainViewModel.mutableLiveData

        mainViewModel.updateFirebaseUser()

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                    if (signInResult.data != null) {
                        navigateToMainActivity()
                    }
                }
            }
        }

        binding.signInButton.setOnClickListener {
            lifecycleScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                val signInIntent =
                    IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                launcher.launch(signInIntent)
            }
        }

        binding.submitFormButton.setOnClickListener {
            register()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigateToChangeProfile", true)
        startActivity(intent)
        finish()
    }

    private fun register() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    navigateToMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}