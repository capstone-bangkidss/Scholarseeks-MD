package com.bangkidss.scholarseeks.ui.explore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkidss.scholarseeks.AuthDialogUtils
import com.bangkidss.scholarseeks.AuthResultCallback
import com.bangkidss.scholarseeks.R
import com.bangkidss.scholarseeks.UserModel
import com.bangkidss.scholarseeks.UserPreference
import com.bangkidss.scholarseeks.adapter.SearchAdapter
import com.bangkidss.scholarseeks.api.ApiConfig
import com.bangkidss.scholarseeks.api.RecomArticleResponseItem
import com.bangkidss.scholarseeks.databinding.FragmentExploreBinding
import com.bangkidss.scholarseeks.ui.detailJournal.DetailJournalActivity
import com.bangkidss.scholarseeks.ui.detailJournal.JournalModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExploreFragment : Fragment(), AuthResultCallback {

    private var _binding: FragmentExploreBinding? = null
    private lateinit var adapter: SearchAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: UserModel

    private lateinit var exploreViewModel: ExploreViewModel

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val apiService = ApiConfig.getApiService()
        val viewModelFactory = ExploreViewModelFactory(apiService)
        exploreViewModel = ViewModelProvider(this, viewModelFactory).get(ExploreViewModel::class.java)

        setupSearch()
        observeViewModel()

        return root
    }

    private fun setupSearch() {

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResult.layoutManager = layoutManager

        adapter = SearchAdapter(
            itemClickListener = { journalItem ->
                Log.d("itemClick", "$userModel")
                val login = false
                if (!userModel.id_token.isNullOrEmpty()) {

                    val dataJournal = RecomArticleResponseItem(
                        articleId = journalItem.articleId ?: 0,
                        title = (journalItem.title) ?: "",
                        dOI = journalItem.dOI ?: "",
                        authors = journalItem.authors ?: "",
                        year = journalItem.year ?: 0,
                        abstract = journalItem.jsonMemberAbstract ?: "",
                        indexKeywords = journalItem.indexKeywords ?: ""
                    )

                    val intentDetail = Intent(requireContext(), DetailJournalActivity::class.java)
                    intentDetail.putExtra(
                        DetailJournalActivity.EXTRA_DETAIL,
                        dataJournal
                    )
                    startActivity(intentDetail)
                } else {
                    val dialogTitle = "Register for access"
                    val skip = true
                    AuthDialogUtils.showDialog(requireContext(), title = dialogTitle, skip = skip, signInResultLauncher = googleSignInResultLauncher, callback = this@ExploreFragment)
                }
            }
        )
        binding.rvSearchResult.adapter = adapter

        mUserPreference = UserPreference(requireContext())
        userModel = mUserPreference.getUser()
        val jwt_token = userModel.jwt_token

        binding.searchViewExplore.setupWithSearchBar(binding.searchBarExplore)

        binding.searchViewExplore.addTransitionListener { searchView, previousState, newState ->
            if (newState == com.google.android.material.search.SearchView.TransitionState.SHOWING) {
                setupChipListener()
            }
        }

        binding.searchViewExplore.editText.setOnEditorActionListener { textView, actionId, event ->
            val query = binding.searchViewExplore.text.toString()
            val sortBy = when {
                binding.chipSortByTitle.isChecked -> "title"
                binding.chipSortByYear.isChecked -> "year"
                binding.chipSortByCitedBy.isChecked -> "cited_by"
                else -> "title"
            }
            Log.d("sortBy", "$sortBy")

            val categories = mutableListOf<String>()
            if (binding.chipCategoryCompSci.isChecked) categories.add("omputer science")
            if (binding.chipCategoryLaw.isChecked) categories.add("law")
            if (binding.chipCategoryAgricultural.isChecked) categories.add("Agricultural")
            if (binding.chipCategoryPharmacy.isChecked) categories.add("pharmacy")
            if (binding.chipCategoryBiology.isChecked) categories.add("Biology")
            if (binding.chipCategoryArtsAndHumanity.isChecked) categories.add("Arts and Humanity")
            if (binding.chipCategoryBioChemistry.isChecked) categories.add("Biochemistry")
            if (binding.chipCategoryGenetics.isChecked) categories.add("Genetics")
            if (binding.chipCategoryMolecularBiology.isChecked) categories.add("Molecular Biology")
            if (binding.chipCategoryBusiness.isChecked) categories.add("Business")
            if (binding.chipCategoryManagement.isChecked) categories.add("Management")
            if (binding.chipCategoryAccounting.isChecked) categories.add("Accounting")
            if (binding.chipCategoryChemicalEngineering.isChecked) categories.add("Chemical Engineering")
            if (binding.chipCategoryChemistry.isChecked) categories.add("Chemistry")
            if (binding.chipCategoryEnergy.isChecked) categories.add("Energy")
            if (binding.chipCategoryEngineering.isChecked) categories.add("Engineering")
            if (binding.chipCategoryEnviromentalScience.isChecked) categories.add("Environmental Science")
            if (binding.chipCategoryCulture.isChecked) categories.add("Culture")
            Log.d("cat", "${categories}")

            jwt_token?.let {
                lifecycleScope.launch {
                    exploreViewModel.search(query = query, sortBy = sortBy, categories = categories, jwt_token = "Bearer $it").collectLatest {
                        adapter.submitData(it)
                    }
                }
            } ?: run {
                Toast.makeText(context, "JWT token is missing", Toast.LENGTH_SHORT).show()
            }
            binding.searchViewExplore.hide()
            false
        }

    }

    private fun setupChipListener() {
        Log.d("setupChipListener", "called")
        val chips = listOf(
            binding.chipSortByTitle, binding.chipSortByYear, binding.chipSortByCitedBy,
            binding.chipCategoryCompSci, binding.chipCategoryLaw, binding.chipCategoryPharmacy
        )

        chips.forEach { chip ->
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Log.d("chip", "clicked")
                } else {
                    Log.d("chip", "clicked again")
                }
            }
        }
    }

    private fun observeViewModel() {
//        exploreViewModel.searchResult.observe(viewLifecycleOwner) { results ->
//            (binding.rvSearchResult.adapter as SearchAdapter).submitList(results)
//        }

        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { loadStates ->
            }
        }

        exploreViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Handle error
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == AuthDialogUtils.RC_SIGN_IN) {
//            AuthDialogUtils.handleSignInResult(data, { idToken ->
//                Log.d("Subject", "idToken: $idToken")
//            }, { exception ->
//                Log.e("Subject", "Failed")
//            })
//        }
//    }

//    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == AuthDialogUtils.RC_SIGN_IN) {
//            AuthDialogUtils.handleSignInResult(result.data, { idToken ->
//                Log.d("Subject", "idToken: $idToken")
//            }, { exception ->
//                Log.e("Subject", "Failed")
//            })
//        }
//    }

//    private fun showRegisterDialog() {
//        val dialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.dialog_auth, null)
//        val dialog = AlertDialog.Builder(binding.root.context)
//            .setView(dialogView)
//            .setPositiveButton(null, null)
//            .create()
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.show()
//    }

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