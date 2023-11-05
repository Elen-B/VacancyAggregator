package ru.practicum.android.diploma.filter.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentLocationRegionBinding
import ru.practicum.android.diploma.filter.presentation.models.LocationRegionScreenState
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationRegionViewModel

class LocationRegionFragment: Fragment() {
    private lateinit var binding: FragmentLocationRegionBinding
    private val args: LocationRegionFragmentArgs by navArgs()

    private val viewModel: LocationRegionViewModel by viewModel {
        parametersOf(args.country)
    }

    private val adapter = LocationViewAdapter(listOf()).apply {
        clickListener = LocationViewAdapter.CountryClickListener { area ->
            setFragmentResult(
                FilterLocationFragment.REGION_RESULT_KEY,
                bundleOf(FilterLocationFragment.REGION_RESULT_VAL to area)
            )
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationRegionBinding.inflate(inflater, container, false)
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

        binding.edRegionSearch.doOnTextChanged { text, _, before, count ->
            if (before == 0 && !text.isNullOrEmpty()) {
                binding.ibtRegionSearch.setImageResource(R.drawable.ic_filter_clear)
            }
            if (before > 0 && text.isNullOrEmpty()) {
                binding.ibtRegionSearch.setImageResource(R.drawable.icon_search)
            }

            viewModel.onEditTextChanged(text.toString())
        }

        binding.ibtRegionSearch.setOnClickListener {
            binding.edRegionSearch.setText("")
            val imm =
                binding.edRegionSearch.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.edRegionSearch.windowToken, 0)
        }

        binding.rvRegionList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRegionList.adapter = adapter
    }

    private fun render(state: LocationRegionScreenState) {
        binding.phFilterEmpty.isVisible = state is LocationRegionScreenState.Empty
        binding.phFilterError.isVisible = state is LocationRegionScreenState.Error
        binding.rvRegionList.isVisible = state is LocationRegionScreenState.Content

        if (state is LocationRegionScreenState.Content)
            adapter.addItems(state.regionList)
    }
}