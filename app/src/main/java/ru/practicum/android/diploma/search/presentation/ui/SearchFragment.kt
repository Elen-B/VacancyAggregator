package ru.practicum.android.diploma.search.presentation.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.presentation.ui.FilterFragment
import ru.practicum.android.diploma.search.presentation.ItemClickListener
import ru.practicum.android.diploma.search.presentation.OnEndOfListListener
import ru.practicum.android.diploma.search.presentation.SearchVacancyAdapter
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel
import ru.practicum.android.diploma.util.CHECK_CONNECTION
import ru.practicum.android.diploma.util.ERROR_HAS_OCCURRED
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.NOT_EXIST
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.VACANCY_ID
import ru.practicum.android.diploma.util.getStringCount


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<VacancySearchViewModel>()
    private val adapter = SearchVacancyAdapter(object : ItemClickListener {
        override fun onVacancyClick(vacancy: Vacancy) {
            if (viewModel.clickDebounce()) {
                val bundle = bundleOf(VACANCY_ID to vacancy.id)
                view?.findNavController()
                    ?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
            }
        }
    }, object : OnEndOfListListener {
        override fun onEndOfList() {
            if (!viewModel.isLastPage()) {
                showLoadingUpdate()
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

        setFragmentResultListener(FilterFragment.FILTER_RESULT_KEY) { _, bundle ->
            val filterParameters: FilterParameters? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(
                        FilterFragment.FILTER_RESULT_VAL,
                        FilterParameters::class.java
                    )
                } else {
                    bundle.getParcelable(FilterFragment.FILTER_RESULT_VAL)
                }
            viewModel.isFilterButtonEnable()
            if (filterParameters != null)
                viewModel.forceSearch(filterParameters)
        }

        viewModel.observeisFiltered().observe(viewLifecycleOwner) { isFilterEnable ->
            if (isFilterEnable)
                binding.imageFilter.setImageResource(R.drawable.image_filter_active)
            else
                binding.imageFilter.setImageResource(R.drawable.image_filter_passive)
        }

        viewModel.observeCoverImageVisible().observe(viewLifecycleOwner) {
            binding.imageCover.isVisible = it
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeIconVisible().observe(viewLifecycleOwner) { searchTextIsEmpty ->
            binding.iconSearch.isVisible = searchTextIsEmpty
            binding.iconCross.isVisible = !searchTextIsEmpty
            if (searchTextIsEmpty) {
                getDefaultView()
            }
        }

        binding.searchEditText.setOnFocusChangeListener { _, _ ->
            viewModel.setFocus(binding.searchEditText.text.isEmpty())
        }
        binding.searchEditText.doAfterTextChanged {
            viewModel.setFocus(
                binding.searchEditText.text.toString().isEmpty()
            )
            viewModel.searchDebounce(binding.searchEditText.text.toString())
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchDebounce(binding.searchEditText.text.toString(), true)
            }
            false
        }

        binding.iconCross.setOnClickListener {
            binding.searchEditText.setText("")
            viewModel.setFocus(true)
            getDefaultView()
        }

        binding.imageFilter.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToFilterFragment(
                filter = viewModel.getFilter()
            )
            findNavController().navigate(action)
        }
    }

    private fun render(state: VacancyState) {
        viewModel.setVisibleCoverImage(false)
        when (state) {
            is VacancyState.Update -> showUpdate(state.vacancy, state.count)
            is VacancyState.Content -> showContent(state.vacancy, state.count)
            is VacancyState.Empty -> showEmpty()
            is VacancyState.ServerError -> showError(state.errorMessage)
            is VacancyState.VacancyError -> showError(state.errorMessage)
            is VacancyState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.imageCover.visibility = View.GONE
        binding.groupServerError.isVisible = false
        binding.groupConnectionError.isVisible = false
        binding.groupVacancyError.isVisible = false
        binding.viewElement.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.textVacancyCount.visibility = View.GONE
    }

    private fun showLoadingUpdate() {
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.groupProgressBarBottomUpdate.isVisible = true
        viewModel.searchDebounce(binding.searchEditText.text.toString(), update = true)
    }

    private fun showUpdate(contentTracks: List<Vacancy>, count: String) {
        binding.textVacancyCount.isVisible = true
        binding.textVacancyCount.setText(getStringCount(count))
        binding.groupProgressBarBottomUpdate.isVisible = false
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        adapter.searchVacancyList.addAll(contentTracks)
        adapter.notifyDataSetChanged()
        binding.recyclerViewSearch.visibility = View.VISIBLE
        binding.progressBar.isVisible = false

    }

    private fun showError(errorMessage: String) {
        if (viewModel.getPage() != 0 && !viewModel.isLastPage()) {
            if (errorMessage == NETWORK_ERROR) {
                showToast(CHECK_CONNECTION)
            } else {
                showToast(ERROR_HAS_OCCURRED)
            }
            return
        }
        hideKeyboard()
        if (errorMessage == SERVER_ERROR) {
            binding.groupServerError.isVisible = true
        } else {
            binding.groupConnectionError.isVisible = true
        }
        binding.groupProgressBarBottomUpdate.isVisible = false
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE

    }

    private fun showEmpty() {
        hideKeyboard()
        binding.textVacancyCount.isVisible = true
        binding.textVacancyCount.setText(NOT_EXIST)
        binding.progressBar.visibility = View.GONE
        binding.groupProgressBarBottomUpdate.isVisible = false
        binding.groupVacancyError.isVisible = true
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showContent(contentTracks: List<Vacancy>, count: String) {
        binding.textVacancyCount.setText(getStringCount(count))
        binding.progressBar.visibility = View.GONE
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        binding.imageCover.visibility = View.GONE
        binding.textVacancyCount.visibility = View.VISIBLE
        binding.recyclerViewSearch.scrollToPosition(0)
        adapter.searchVacancyList.clear()
        adapter.searchVacancyList.addAll(contentTracks)
        adapter.notifyDataSetChanged()
        binding.recyclerViewSearch.visibility = View.VISIBLE
        binding.progressBar.isVisible = false
    }

    private fun getDefaultView() {
        viewModel.setVisibleCoverImage(true)
        binding.groupServerError.isVisible = false
        binding.groupConnectionError.isVisible = false
        binding.groupVacancyError.isVisible = false
        binding.viewElement.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textVacancyCount.visibility = View.GONE
        binding.groupProgressBarBottomUpdate.isVisible = false
    }

    private fun hideKeyboard() {
        binding.searchEditText.clearFocus()
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    private fun showToast(text: String) {
        binding.groupProgressBarBottomUpdate.isVisible = false
        Snackbar.make(requireActivity().findViewById(R.id.container), text, Snackbar.LENGTH_SHORT)
            .setTextColor(resources.getColor(R.color.white, requireContext().theme))
            .setBackgroundTint(resources.getColor(R.color.red, requireContext().theme))
            .show()
    }
}