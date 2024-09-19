import com.google.firebase.firestore.FirebaseFirestore
import dam.a48965.project_48965.model.RecGameApi

class RecGameRepository(private val firestore: FirebaseFirestore) {

    fun insertRecGame(recGameApi: RecGameApi, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("rec_game_api")
            .document(recGameApi.id.toString())
            .set(recGameApi)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getRecGameById(id: Int, onSuccess: (RecGameApi?) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("rec_game_api")
            .document(id.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                onSuccess(documentSnapshot.toObject(RecGameApi::class.java))
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
