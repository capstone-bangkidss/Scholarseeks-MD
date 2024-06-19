package com.bangkidss.scholarseeks.ui.ratedArticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.databinding.FragmentRatedArticleBinding

class RatedArticleFragment : Fragment() {

    private var _binding: FragmentRatedArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RatedArticleViewModel by viewModels()
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatedArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())
        val userModel = userPreference.getUser()
        val jwtToken = userModel.jwt_token ?: ""
        val userId = userModel.user_id.toString()

        val adapter = ListRatedArticleAdapter(emptyList())
        binding.rvJournal.layoutManager = LinearLayoutManager(context)
        binding.rvJournal.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.ratedArticles.observe(viewLifecycleOwner, Observer { articles ->
            adapter.updateArticles(articles)
        })

        viewModel.getRatedArticles(jwtToken, userId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
