package dam.a48965.project_48965.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dam.a48965.project_48965.MyApplication
import dam.a48965.project_48965.R
import dam.a48965.project_48965.databinding.ActivityMainBinding
import dam.a48965.project_48965.domain.DBModule
import dam.a48965.project_48965.domain.MyGameDao
import dam.a48965.project_48965.presentation.sign_in.GoogleAuthUiClient
import dam.a48965.project_48965.ui.search.SearchFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var myGameDao: MyGameDao

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = Firebase.auth.currentUser
        val dbModule = DBModule.getInstance(this)
        user?.uid?.let { dbModule.userId = it }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)


        if (intent.getBooleanExtra("navigateToChangeProfile", false)) {
            navController.navigate(R.id.nav_change_profile)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_played, R.id.nav_backlog, R.id.nav_change_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        myGameDao = (application as MyApplication).dbModule.myGameDao
    }

    override fun onResume() {
        super.onResume()

        val user = Firebase.auth.currentUser

        updateProfile(user)

        // Set up the navigation item selected listener
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_played -> {
                    navController.navigate(R.id.nav_played)
                    true
                }
                R.id.nav_backlog -> {
                    navController.navigate(R.id.nav_backlog)
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.nav_profile)
                    true
                }
                R.id.nav_about -> {
                    navController.navigate(R.id.nav_about)
                    true
                }
                R.id.nav_sign_out -> {
                    // Launch a coroutine to call the signOut method
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        mainViewModel.signOut()
                        mainViewModel.updateFirebaseUser()
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun updateProfile(user: FirebaseUser?) {
        val headerView = binding.navView.getHeaderView(0)
        val profileImageView = headerView.findViewById<ImageView>(R.id.profile_image)
        val usernameTextView = headerView.findViewById<TextView>(R.id.username_text)

        if (user?.photoUrl != null) {
            Glide.with(this)
                .load(user.photoUrl.toString())
                .override(200, 200) // replace 200, 200 with the desired width and height
                .centerCrop()
                .into(profileImageView)
        }

        usernameTextView.text = user?.displayName ?: "Guest"

        profileImageView.setOnClickListener {
            navController.navigate(R.id.nav_profile)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_button -> {
                // Navigate to SearchFragment

                navController.navigate(R.id.nav_search)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        // Check if the current fragment is SearchFragment
        if (currentFragment is SearchFragment) {
            // If it is, hide the search item
            menu.findItem(R.id.search_button)?.isVisible = false
        } else {
            // If it's not, show the search item
            menu.findItem(R.id.search_button)?.isVisible = true
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}