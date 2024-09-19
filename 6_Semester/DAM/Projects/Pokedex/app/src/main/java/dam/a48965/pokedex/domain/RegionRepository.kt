package dam.a48965.pokedex.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam.a48965.pokedex.model.PokemonRegion
import dam.a48965.pokedex.model.network.responses.PokemonApi

class RegionRepository(private val pokemonApi: PokemonApi,
                       private val regionDao: PokemonRegionDao
)
{
    suspend fun getRegions() : LiveData<List<PokemonRegion>>
    {
        val hasRegions = regionDao.count()
        if(hasRegions > 0)
        {
            val regions = regionDao.getRegions()
            return MutableLiveData(regions)
        }
        try {
            val regionsResponse = pokemonApi.fetchRegionList()
            val regions = regionsResponse.results?.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()
                var regionId = regexToGetId.find(it.url!!)?.value
                regionId = regionId?.removeSurrounding("/")
                PokemonRegion(regionId?.toInt() ?: 0, it.name.toString())

            }
            regions?.forEach {
                regionDao.insertRegion(it) }
            return MutableLiveData(regions)
        }catch (e: java.lang.Exception)
        {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }

}