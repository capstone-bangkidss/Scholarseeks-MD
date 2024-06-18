package com.bangkidss.scholarseeks.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.data_dummy.JournalCollab
import com.bangkidss.scholarseeks.data_dummy.JournalRfy
import com.bangkidss.scholarseeks.data_dummy.getJournal
import com.bangkidss.scholarseeks.data_dummy.getJournalCollab
import com.bangkidss.scholarseeks.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    private val listJournalRecommendation = ArrayList<JournalRfy>()
    private val listJournalCollaborative = ArrayList<JournalCollab>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val layoutManagerRecommendation = LinearLayoutManager(requireActivity())
        binding.rvJournal.layoutManager = layoutManagerRecommendation
        binding.rvJournal.setHasFixedSize(true)

        listJournalRecommendation.addAll(getJournal())
        showRecyclerListJournalRecommendation()

        val layoutManagerCollaborative = LinearLayoutManager(requireActivity())
        binding.rvJournalCollaborative.layoutManager = layoutManagerCollaborative
        binding.rvJournalCollaborative.setHasFixedSize(true)

        listJournalCollaborative.addAll(getJournalCollab())
        showRecyclerListJournalCollaborative()
    }

    private fun showRecyclerListJournalRecommendation() {
        val adapter = ListJournalAdapter(listJournalRecommendation)
        binding.rvJournal.adapter = adapter
    }

    private fun showRecyclerListJournalCollaborative() {
        val adapter = ListJournalCollabAdapter(listJournalCollaborative)
        binding.rvJournalCollaborative.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
