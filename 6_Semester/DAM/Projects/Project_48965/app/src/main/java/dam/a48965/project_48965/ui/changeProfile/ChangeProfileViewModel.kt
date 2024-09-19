package dam.a48965.project_48965.ui.changeProfile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangeProfileViewModel : ViewModel() {
    val user = Firebase.auth.currentUser
}