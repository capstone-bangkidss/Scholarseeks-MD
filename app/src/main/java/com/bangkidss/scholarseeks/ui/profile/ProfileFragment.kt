package com.bangkidss.scholarseeks.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bangkidss.scholarseeks.GetStartedActivity
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.databinding.FragmentExploreBinding
import com.bangkidss.scholarseeks.databinding.FragmentProfileBinding

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

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.logOut.setOnClickListener {
            logOut()
        }

        val root: View = binding.root

        return root
    }

    private fun logOut() {
        mUserPreference.clearUser()
        val intentGetStarted = Intent(requireContext(), GetStartedActivity::class.java)
        startActivity(intentGetStarted)
        requireActivity().finish()
    }
}