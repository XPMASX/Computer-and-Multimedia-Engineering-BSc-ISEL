package dam.a48965.project_48965.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.a48965.project_48965.domain.MyGameDao

class ProfileViewModelFactory(private val myGameDao: MyGameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(myGameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}