package dam.a48965.pokedex.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a48965.pokedex.domain.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    private lateinit var _repository: RegionRepository
    fun initViewMode(repository: RegionRepository) {
        _repository = repository
    }
    fun fetchRegions() {

        //_regions.value = MockData.regions
        viewModelScope.launch(Dispatchers.Default) {
            val regionsList = _repository.getRegions()
            _regions.postValue(regionsList.value)
        }
    }
}