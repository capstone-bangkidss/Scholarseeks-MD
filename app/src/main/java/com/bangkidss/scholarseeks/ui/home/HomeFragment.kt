package com.bangkidss.scholarseeks.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.AuthDialogUtils
import com.bangkidss.scholarseeks.AuthResultCallback
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), AuthResultCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    private val googleSignInResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result from Google Sign-In
            AuthDialogUtils.handleSignInResult(result.data)
        } else {
            Log.e("YourFragment", "Google Sign-In failed")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        // Initialize UserPreference and get UserModel
        mUserPreference = UserPreference(requireContext())
        userModel = mUserPreference.getUser()

        // Ensure userModel is properly initialized before accessing its properties
        val userToken = userModel.jwt_token.toString()
        val jwtToken = "Bearer $userToken"
        val userId = userModel.user_id.toString()

        viewModel.getRecomendationArticle(jwtToken, userId)

        viewModel.getCollaborativeArticle(jwtToken, userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvJournal.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.recomArticleIsLoading.observe(viewLifecycleOwner) {
            showLoadingRecomArticle(it)
        }

        viewModel.recommendedArticles.observe(viewLifecycleOwner, Observer { articles ->
            articles?.let {
                Log.d("HomeFragment", "Articles updated: $articles")
                val adapter = ListJournalAdapter(requireContext(), googleSignInResultLauncher, this,  articles)
                binding.rvJournal.adapter = adapter
            }
        })

        binding.rvJournalCollaborative.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.collaborativeArticleIsLoading.observe(viewLifecycleOwner) {
            showLoadingCollaborativeArticle(it)
        }

        viewModel.collaborativeArticle.observe(viewLifecycleOwner, Observer { articles ->
            articles?.let {
                Log.d("HomeFragment", "Articles updated: $articles")
                val adapter = ListJournalCollabAdapter(requireContext(), googleSignInResultLauncher, this , articles)
                binding.rvJournalCollaborative.adapter = adapter
            }
        })
    }

    private fun showLoadingRecomArticle(isLoading: Boolean) {
        binding.pbRecomArticle.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingCollaborativeArticle(isLoading: Boolean) {
        binding.pbArticleCollab.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onAuthSuccess(userModel: UserModel) {
        this.userModel = userModel
    }

    override fun onAuthFailure() {
        Log.e("ExploreFragment", "Auth failure")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}