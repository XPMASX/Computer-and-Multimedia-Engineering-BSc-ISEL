package menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dam.a48965.pokedex.R
import dam.a48965.pokedex.ui.RegionActivity
import dam.a48965.pokedex.ui.TeamsActivity

abstract class BottomNavActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var binding : ViewBinding

    abstract val contentViewId: Int
    abstract val navigationMenuItemId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(contentViewId)
        this.binding = DataBindingUtil.setContentView(this, contentViewId)
        navigationView = findViewById(R.id.navigation)
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_regions -> {
                    // Navigate to RegionsActivity
                    startActivity(Intent(this, RegionActivity::class.java))
                    true
                }
                R.id.navigation_teams -> {
                    // Navigate to TeamActivity
                    startActivity(Intent(this, TeamsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }


    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item = navigationView!!.menu.findItem(itemId)
        item.setChecked(true)
    }


}