package com.bangkidss.scholarseeks.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

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
        val user_token = userModel.jwt_token.toString()
        val jwtToken = "Bearer $user_token"
        val userId = userModel.user_id.toString()

        viewModel.getRecomendationArticle(jwtToken, userId)
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
                val adapter = ListJournalAdapter(articles)
                binding.rvJournal.adapter = adapter
            }
        })
    }

    private fun showLoadingRecomArticle(isLoading: Boolean) {
        binding.pbRecomArticle.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}