package com.ardian.tarecommendation.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardian.tarecommendation.adapter.SearchAdapter
import com.ardian.tarecommendation.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private lateinit var adapter: SearchAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val exploreViewModel =
            ViewModelProvider(this).get(ExploreViewModel::class.java)

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            searchViewExplore.setupWithSearchBar(searchBarExplore)
            searchViewExplore
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBarExplore.setText(searchViewExplore.text)
                    searchViewExplore.hide()
                    Toast.makeText(context, searchViewExplore.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResult.layoutManager = layoutManager

        adapter = SearchAdapter()

        exploreViewModel.sampleData.observe(viewLifecycleOwner) { sampleData ->
            adapter.submitList(sampleData)
        }

        binding.rvSearchResult.adapter = adapter

//        val textView: TextView = binding.textDashboard
//        exploreViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}