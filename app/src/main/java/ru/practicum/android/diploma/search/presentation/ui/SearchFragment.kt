package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.VacancyState


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.searchEditText.hasFocus()) {
                binding.searchEditText.clearFocus()
            } else {
                requireActivity().onBackPressed()
            }
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
        binding.textVacancyCount.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageConnectionError.visibility = View.VISIBLE
        binding.textConnectionError.visibility = View.VISIBLE
    }

    private fun showEmpty(emptyMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.imageVacancyError.visibility = View.VISIBLE
        binding.textVacancyError.visibility = View.VISIBLE
    }

    private fun showContent(contentTracks: List<SearchVacancy>) {
        binding.progressBar.visibility = View.GONE
        if (binding.searchEditText.text.isBlank()) {
            return
        }
        //Остальная логика в следующей задаче
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
    }
}