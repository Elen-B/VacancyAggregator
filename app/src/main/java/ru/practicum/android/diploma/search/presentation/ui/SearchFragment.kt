package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.ItemClickListener
import ru.practicum.android.diploma.search.presentation.SearchVacancyAdapter
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<VacancySearchViewModel>()
    private var isClickAllowed = true
    private val adapter = SearchVacancyAdapter(object : ItemClickListener {
        override fun onVacancyClick(vacancy: SearchVacancy) {
            if (clickDebounce()) {
                //TODO
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.searchEditText.hasFocus()) {
                binding.searchEditText.clearFocus()
            } else {
                requireActivity().onBackPressed()
            }
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.searchEditText.doAfterTextChanged {
            if (binding.searchEditText.hasFocus() && binding.searchEditText.text.toString()
                    .isBlank()
            ) {
                getDefaultView()
                binding.iconSearch.visibility = View.VISIBLE
                binding.iconCross.visibility = View.GONE
            } else if (binding.searchEditText.hasFocus() && binding.searchEditText.text.toString()
                    .isNotEmpty()
            ) {
                binding.iconSearch.visibility = View.GONE
                binding.iconCross.visibility = View.VISIBLE
            }
        }

        binding.iconCross.setOnClickListener {
            binding.searchEditText.setText("")
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
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
        binding.textVacancyCount.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageConnectionError.visibility = View.VISIBLE
        binding.textConnectionError.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showEmpty(emptyMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageVacancyError.visibility = View.VISIBLE
        binding.textVacancyError.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showContent(contentTracks: List<SearchVacancy>) {
        binding.progressBar.visibility = View.GONE
        if (binding.searchEditText.text.isBlank()) {
            return
        }
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
    }
}