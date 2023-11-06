package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.filter.presentation.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterIndustryViewModel

class FilterIndustryFragment: Fragment() {
    private lateinit var binding: FragmentFilterIndustryBinding

    private val viewModel: FilterIndustryViewModel by viewModel()

    private val adapter = IndustryAdapter(listOf()).apply {
        clickListener = IndustryAdapter.IndustryClickListener { industry ->
            this.checkedIndustry = industry
            this.notifyDataSetChanged()
        /*    setFragmentResult(
                FilterLocationFragment.REGION_RESULT_KEY,
                bundleOf(FilterLocationFragment.REGION_RESULT_VAL to industry)
            )
            findNavController().navigateUp()*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
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

        binding.rvIndustryList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustryList.adapter = adapter
    }

    private fun render(state: FilterIndustryScreenState) {
        binding.phFilterEmpty.isVisible = state is FilterIndustryScreenState.Empty
        binding.phFilterError.isVisible = state is FilterIndustryScreenState.Error
        binding.rvIndustryList.isVisible = state is FilterIndustryScreenState.Content

        if (state is FilterIndustryScreenState.Content)
            adapter.addItems(state.industryList)
    }
}