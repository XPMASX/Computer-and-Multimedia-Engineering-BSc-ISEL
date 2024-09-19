package dam.a48965.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels

import dam.a48965.pokedex.R
import dam.a48965.pokedex.databinding.ActivityRegionsBinding
import dam.a48965.pokedex.domain.DBModule
import dam.a48965.pokedex.model.PokemonRegion
import dam.a48965.pokedex.model.RegionAdapter
import dam.a48965.pokedex.model.RegionsViewModel
import menu.BottomNavActivity

class RegionActivity : BottomNavActivity() {
    val viewModel: RegionsViewModel by  viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val regionBinding = binding as ActivityRegionsBinding
        val listView = regionBinding.regionsRecyclerView

        viewModel.initViewMode(DBModule.getInstance(this).regionRepository)

        viewModel.regions.observe(this) { regions ->
            listView.adapter = regions?.let {
                RegionAdapter(
                    pkRegionList = it,
                    context = this,
                    itemClickedListener = { region -> navigateToPokemonList(region) }
                )
            }
        }
        viewModel.fetchRegions()
    }


    private fun navigateToPokemonList(region: PokemonRegion) {
        // Navigate to PokemonListActivity, passing necessary data
        val intent = Intent(this, PokemonListActivity::class.java)
        intent.putExtra("region_id", region.id) // Assuming you need to pass region id
        intent.putExtra("region", region) // Assuming you need to pass region id
        startActivity(intent)
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}