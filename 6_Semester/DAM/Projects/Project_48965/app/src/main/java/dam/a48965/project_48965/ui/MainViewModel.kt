package dam.a48965.project_48965.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainViewModel: ViewModel() {

    val mutableLiveData = MutableLiveData<FirebaseUser>()

    fun updateFirebaseUser() {
        mutableLiveData.value = FirebaseAuth.getInstance().currentUser
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        updateFirebaseUser()
    }

    fun setMutableLiveData(user: FirebaseUser) {
        mutableLiveData.value = user
    }
}