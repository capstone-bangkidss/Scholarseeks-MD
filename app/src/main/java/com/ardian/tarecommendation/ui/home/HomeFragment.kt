package com.ardian.tarecommendation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardian.tarecommendation.data_dummy.JournalRfy
import com.ardian.tarecommendation.data_dummy.getJournal
import com.ardian.tarecommendation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    // dummy data (hapus nanti)
    private val list = ArrayList<JournalRfy>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvJournal.layoutManager = layoutManager
        binding.rvJournal.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvJournal.addItemDecoration(itemDecoration)

        list.addAll(getJournal())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val adapter = ListJournalAdapter(list)
        binding.rvJournal.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}