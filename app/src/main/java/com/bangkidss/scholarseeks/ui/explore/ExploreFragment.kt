package com.bangkidss.scholarseeks.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.AuthDialogUtils
import com.bangkidss.scholarseeks.adapter.SearchAdapter
import com.bangkidss.scholarseeks.databinding.FragmentExploreBinding

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

        val login = false

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

        adapter = SearchAdapter(
            itemClickListener = { journalItem ->
                val dialogTitle = "Register for access"
                val skip = true
                if (!login) {
                    AuthDialogUtils.showDialog(binding.root.context, title = dialogTitle, skip = skip)
                }
            }
        )

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

//    private fun showRegisterDialog() {
//        val dialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.dialog_auth, null)
//        val dialog = AlertDialog.Builder(binding.root.context)
//            .setView(dialogView)
//            .setPositiveButton(null, null)
//            .create()
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.show()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}