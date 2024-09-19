package dam.a48965.project_48965.ui.changeProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dam.a48965.project_48965.databinding.FragmentChangeProfileBinding
import dam.a48965.project_48965.ui.MainActivity

class ChangeProfileFragment : Fragment() {
    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangeProfileViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    private val IMAGE_PICK_CODE = 1000
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            loadImage()
        }

        binding.profilePicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        binding.submitChangesButton.setOnClickListener {
            uploadImageAndSaveProfile()
        }
    }

    private fun uploadImageAndSaveProfile() {
        val user = Firebase.auth.currentUser
        user?.let { currentUser ->
            if (selectedImageUri != null) {
                // Create a reference to the location where you want to upload the image
                val storageRef = Firebase.storage.reference.child("profile_pictures/${currentUser.uid}")

                // Upload the file to Firebase Cloud Storage
                selectedImageUri?.let { uri ->
                    storageRef.putFile(uri)
                        .addOnSuccessListener {
                            // After the upload is successful, get the download URL of the uploaded file
                            storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                // Save the download URL to the user's profile
                                saveProfile(currentUser, downloadUri)
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Handle unsuccessful uploads
                            Log.w("ChangeProfileActivity", "Failed to upload image: $exception")
                            Toast.makeText(
                                requireContext(),
                                "Failed to upload image. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            } else {
                // If the image hasn't been changed, just update the username
                saveProfile(currentUser, null)
            }
        }
    }

    private fun saveProfile(user: FirebaseUser, photoUri: Uri?) {
    val profileUpdates = userProfileChangeRequest {
        displayName = binding.username.text.toString()
        if (photoUri != null) {
            this.photoUri = photoUri // Use the download URL of the uploaded file
        }
    }

    user.updateProfile(profileUpdates)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("ChangeProfileActivity", "User profile updated.")
                Toast.makeText(
                    requireContext(),
                    "Profile updated successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            } else {
                Log.w(
                    "ChangeProfileActivity",
                    "Failed to update profile: ${task.exception}"
                )
                // Handle failure, show message to user
                Toast.makeText(
                    requireContext(),
                    "Failed to update profile. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, load image
                    loadImage()
                } else {
                    // Permission denied, handle accordingly (e.g., show a message)
                    Toast.makeText(
                        requireContext(),
                        "permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            // Handle other permission requests if needed
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data // Store the URI directly
            binding.profilePicture.setImageURI(selectedImageUri) // Use the URI directly when setting the image
        }
    }

    private fun loadImage() {
        viewModel.user?.photoUrl?.let { photoUrl ->
            val photoUri = Uri.parse(photoUrl.toString())
            Glide.with(this)
                .load(photoUri)
                .into(binding.profilePicture)
            binding.username.setText(viewModel.user?.displayName)
        }
    }
}