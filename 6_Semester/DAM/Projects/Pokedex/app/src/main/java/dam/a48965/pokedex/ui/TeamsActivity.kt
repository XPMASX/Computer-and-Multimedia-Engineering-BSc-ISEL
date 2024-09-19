package dam.a48965.pokedex.ui

import android.os.Bundle
import dam.a48965.pokedex.R
import menu.BottomNavActivity

class TeamsActivity : BottomNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val contentViewId: Int
        get() = R.layout.activity_teams
    override val navigationMenuItemId: Int
        get() = R.id.navigation_teams
}