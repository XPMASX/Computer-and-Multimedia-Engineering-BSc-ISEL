package dam.a48965.project_48965.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.a48965.project_48965.domain.MyGameDao

class SearchViewModelFactory(private val myGameDao: MyGameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(myGameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
