package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentLocationCountryBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationCountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.presentation.models.LocationCountryScreenState

class LocationCountryFragment: Fragment() {
    private lateinit var binding: FragmentLocationCountryBinding

    private val viewModel: LocationCountryViewModel by viewModel()

    private val adapter = LocationCountryAdapter(listOf()).apply {
        clickListener = LocationCountryAdapter.CountryClickListener { area ->
            setFragmentResult(FilterLocationFragment.COUNTRY_RESULT_KEY, bundleOf(FilterLocationFragment.COUNTRY_RESULT_VAL to area))
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvCountryList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCountryList.adapter = adapter
    }

    private fun render(state: LocationCountryScreenState) {
        binding.rvCountryList.isVisible = state is LocationCountryScreenState.Content
        binding.phFilterError.isVisible = state is LocationCountryScreenState.Error

        if (state is LocationCountryScreenState.Content)
            adapter.addItems(state.countryList)
    }
}