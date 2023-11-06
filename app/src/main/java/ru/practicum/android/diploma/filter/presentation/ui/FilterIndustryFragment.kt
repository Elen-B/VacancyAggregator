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
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterIndustryViewModel

class FilterIndustryFragment: Fragment() {
    private lateinit var binding: FragmentFilterIndustryBinding
    private val args: FilterIndustryFragmentArgs by navArgs()

    private val viewModel: FilterIndustryViewModel by viewModel {
        parametersOf(args.industry)
    }

    private val adapter = IndustryAdapter(listOf()).apply {
        clickListener = IndustryAdapter.IndustryClickListener { industry ->
            viewModel.onIndustryChecked(industry)
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

        viewModel.getApplyFilterTrigger().observe(viewLifecycleOwner) { industry ->
            applyFilter(industry)
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btFilterChoose.setOnClickListener {
            viewModel.applyFilter()
        }

        binding.edIndustrySearch.doOnTextChanged { text, _, before, _ ->
            if (before == 0 && !text.isNullOrEmpty()) {
                binding.ibtIndustrySearch.setImageResource(R.drawable.ic_filter_clear)
            }
            if (before > 0 && text.isNullOrEmpty()) {
                binding.ibtIndustrySearch.setImageResource(R.drawable.icon_search)
            }

            viewModel.onEditTextChanged(text.toString())
        }

        binding.ibtIndustrySearch.setOnClickListener {
            binding.edIndustrySearch.setText("")
            val imm =
                binding.edIndustrySearch.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.edIndustrySearch.windowToken, 0)
        }

        binding.rvIndustryList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustryList.adapter = adapter
    }

    private fun render(state: FilterIndustryScreenState) {
        binding.phFilterEmpty.isVisible = state is FilterIndustryScreenState.Empty
        binding.phFilterError.isVisible = state is FilterIndustryScreenState.Error
        binding.rvIndustryList.isVisible = state is FilterIndustryScreenState.Content


        if (state is FilterIndustryScreenState.Content) {
            adapter.addItems(state.industryList, state.checkedIndustry)
            binding.btFilterChoose.isVisible = state.checkedIndustry != null
        } else
            binding.btFilterChoose.isVisible = false
    }

    private fun applyFilter(industry: Industry?) {
        setFragmentResult(
            INDUSTRY_RESULT_KEY,
            bundleOf(INDUSTRY_RESULT_VAL to industry)
        )
        findNavController().navigateUp()
    }

    companion object {
        const val INDUSTRY_RESULT_KEY = "industry_key"
        const val INDUSTRY_RESULT_VAL = "industry_val"
    }
}