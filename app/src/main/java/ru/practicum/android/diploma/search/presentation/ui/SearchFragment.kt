package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.ItemClickListener
import ru.practicum.android.diploma.search.presentation.SearchPagerAdapter
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<VacancySearchViewModel>()
    private val adapter = SearchPagerAdapter(object : ItemClickListener {
        override fun onVacancyClick(vacancy: SearchVacancy) {
            if (viewModel.clickDebounce()) {//Переход на экран детализации
                val bundle = bundleOf("id" to vacancy.id)
                view?.findNavController()
                    ?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter


        viewModel.observeFoundVacanciesCount().observe(viewLifecycleOwner) {
            binding.textVacancyCount.setText(getString(R.string.foundVacancies, it))
        }
        viewModel.observeisFiltered().observe(viewLifecycleOwner) { isFilterEnable ->
            if (isFilterEnable)
                binding.imageFilter.setImageResource(R.drawable.image_filter_active)
            else
                binding.imageFilter.setImageResource(R.drawable.image_filter_passive)
        }

        viewModel.getVacancies().observe(viewLifecycleOwner) { pagingData ->
            Log.i("Errrror","observe2")
            adapter.submitData(lifecycle, pagingData)
        }
        viewModel.observeVacanciesList().observe(viewLifecycleOwner) { pager ->
            Log.i("Errrror","observe")
            adapter.submitData(lifecycle, pager)
        }
        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                showContent()
            //binding.progressDialog.isVisible = true
            else {
                //binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        /*
                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                    if (binding.searchEditText.hasFocus()) {
                        binding.searchEditText.clearFocus()
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
        */


        binding.searchEditText.setOnFocusChangeListener { view, hasFocus ->
            binding.iconSearch.isVisible = !hasFocus && binding.searchEditText.text.isBlank()
            binding.iconCross.isVisible = hasFocus || binding.searchEditText.text.isNotBlank()
        }
        binding.searchEditText.doAfterTextChanged {
            if (binding.searchEditText.hasFocus() && binding.searchEditText.text.toString()
                    .isBlank()
            ) {
                getDefaultView()
                binding.searchEditText.clearFocus()
                binding.iconSearch.visibility = View.VISIBLE
                binding.iconCross.visibility = View.GONE
            } else if (binding.searchEditText.hasFocus() && binding.searchEditText.text.toString()
                    .isNotEmpty()
            ) {
                viewModel.getVacancies(hashMapOf("text" to binding.searchEditText.text.toString()))
                binding.iconSearch.visibility = View.GONE
                binding.iconCross.visibility = View.VISIBLE
            }
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getVacancies(hashMapOf("text" to binding.searchEditText.text.toString()))
            }
            false
        }

        binding.iconCross.setOnClickListener {
            binding.searchEditText.setText("")
        }

        binding.imageFilter.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToFilterFragment(
                filter = viewModel.getFilter()
            )
            findNavController().navigate(action)
        }
    }



    private fun showLoading() {
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.imageCover.visibility = View.GONE
        binding.imageConnectionError.visibility = View.GONE
        binding.textConnectionError.visibility = View.GONE
        binding.imageVacancyError.visibility = View.GONE
        binding.textVacancyError.visibility = View.GONE
        binding.viewElement.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.textVacancyCount.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageConnectionError.visibility = View.VISIBLE
        binding.textConnectionError.visibility = View.VISIBLE
        binding.textConnectionError.setText(errorMessage)
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
    }

    private fun showEmpty(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageVacancyError.visibility = View.VISIBLE
        binding.textVacancyError.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.imageCover.visibility = View.GONE
        binding.textVacancyCount.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.VISIBLE
    }

    private fun getDefaultView() {
        binding.imageCover.visibility = View.VISIBLE
        binding.imageConnectionError.visibility = View.GONE
        binding.textConnectionError.visibility = View.GONE
        binding.imageVacancyError.visibility = View.GONE
        binding.textVacancyError.visibility = View.GONE
        binding.viewElement.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.isFilterButtonEnable()
    }
}