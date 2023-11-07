package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.filter.domain.impl.FilterLocalInteractorImpl
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.ItemClickListener
import ru.practicum.android.diploma.search.presentation.SearchVacancyAdapter
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<VacancySearchViewModel>()
    private val adapter = SearchVacancyAdapter(object : ItemClickListener {
        override fun onVacancyClick(vacancy: SearchVacancy) {
            if (viewModel.clickDebounce()) {
                //TODO
                //Переход на экран детализации
                /* val bundle = bundleOf("id" to vacancy.id)
                 view?.findNavController()
                     ?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)*/
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


        viewModel.observeFoundVacanciesCount().observe(viewLifecycleOwner){
            binding.textVacancyCount.setText(getString(R.string.foundVacancies, it))
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
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
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
                viewModel.searchDebounce(binding.searchEditText.text.toString())
                binding.iconSearch.visibility = View.GONE
                binding.iconCross.visibility = View.VISIBLE
            }
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                viewModel.searchDebounce(binding.searchEditText.text.toString(), true)
            }
            false
        }

        binding.iconCross.setOnClickListener {
            binding.searchEditText.setText("")
        }

        binding.imageButton.setOnClickListener {
            // исправить на использование viewModel в задаче применения фильтра к поиску
            val action = SearchFragmentDirections.actionSearchFragmentToFilterFragment(
                filter = null
            )
            findNavController().navigate(action)
        }
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Content -> showContent(state.vacancy)
            is VacancyState.Empty -> showEmpty(state.message)
            is VacancyState.Error -> showError(state.errorMessage)
            is VacancyState.Loading -> showLoading()
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

    private fun showContent(contentTracks: List<SearchVacancy>) {
        binding.progressBar.visibility = View.GONE
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.imageCover.visibility = View.GONE
        binding.textVacancyCount.visibility = View.VISIBLE
        adapter.searchVacancyList.clear()
        adapter.searchVacancyList.addAll(contentTracks)
        adapter.notifyDataSetChanged()
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
}