package com.bangkidss.scholarseeks.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkidss.scholarseeks.GetStartedActivity
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.databinding.FragmentExploreBinding
import com.bangkidss.scholarseeks.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         TODO: Use the ViewModel
        mUserPreference = UserPreference(requireContext())
        userModel = mUserPreference.getUser()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.logOut.setOnClickListener {
            logOut()
        }

        val root: View = binding.root

        if (userModel.user_name.isNullOrEmpty() && userModel.user_email.isNullOrEmpty()) {
            binding.userName.text = "Guest account"
            binding.userEmail.text = "You need an account to get full access"
        } else {
            binding.userName.text = userModel.user_name
            binding.userEmail.text = userModel.user_email
        }

        val ImageViewuserPhoto: ImageView = binding.userPhoto

        if (!userModel.user_photo.isNullOrEmpty()) {
            Glide.with(this)
                .load(userModel.user_photo)
                .into(ImageViewuserPhoto)
        }

        return root
    }

    private fun logOut() {
        mUserPreference.clearUser()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Google Sign out successful, navigate to GetStartedActivity
                val intentGetStarted = Intent(requireContext(), GetStartedActivity::class.java)
                startActivity(intentGetStarted)
                requireActivity().finish()
            } else {
                // Handle sign out failure (optional)
                Toast.makeText(requireContext(), "Failed to log out from Google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}