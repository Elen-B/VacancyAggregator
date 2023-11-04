package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.presentation.models.FilterScreenState

class FilterFragment: Fragment() {
    private lateinit var binding: FragmentFilterBinding

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getShowLocationTrigger().observe(viewLifecycleOwner) {filterParameters ->
            showLocation(filterParameters.country, filterParameters.region)
        }

        binding.miFilterLocation.editText?.setOnClickListener {
            viewModel.showLocation()
        }

        binding.miFilterIndustry.editText?.setOnClickListener {
            showIndustry()
        }

        binding.miFilterLocation.setEndIconOnClickListener {
            if (binding.miFilterLocation.editText?.text.isNullOrEmpty())
                viewModel.showLocation()
            else {
                binding.miFilterLocation.editText?.text = null
                setMenuEditTextStyle(binding.miFilterLocation, false)
            }
        }

        binding.miFilterIndustry.setEndIconOnClickListener {
            if (binding.miFilterIndustry.editText?.text.isNullOrEmpty())
                showIndustry()
            else {
                binding.miFilterIndustry.editText?.text = null
                setMenuEditTextStyle(binding.miFilterIndustry, false)
            }

        }

        binding.miFilterSalary.setEndIconOnClickListener {
            binding.miFilterSalary.editText?.text = null
            it.visibility = View.GONE
        }

        binding.miFilterSalary.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.onSalaryChanged(text.toString())
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun render(state: FilterScreenState) {
        when (state) {
            is FilterScreenState.Started -> {
                setViewData(state.data)
                setViewAppearance(state)
            }
            is FilterScreenState.Modified -> {
                if (state.update)
                    setViewData(state.data)
                setViewAppearance(state)
            }
            else -> Unit
        }

    }

    private fun setViewAppearance(state: FilterScreenState) {
        val filterParameters = when (state) {
            is FilterScreenState.Started -> state.data
            is FilterScreenState.Modified -> state.data
            else -> null
        }

        if (filterParameters != null) {
            setMenuEditTextStyle(
                binding.miFilterLocation,
                !filterParameters.country?.name.isNullOrEmpty()
            )

            setMenuEditTextStyle(
                binding.miFilterIndustry,
                !filterParameters.industry?.name.isNullOrEmpty()
            )

            setSalaryEditTextStyle(
                binding.miFilterSalary,
                filterParameters.salary != null
            )
            binding.btFilterClear.isVisible = !filterParameters.isEmpty()
        }

        binding.btFilterApply.isVisible = state is FilterScreenState.Modified
    }

    private fun setMenuEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_menu_label_selector
        } else {
            R.color.filter_menu_hint_selector
        }

        val colorStateList = ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        if (filled) {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_clear)
        } else {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_arrow_forward)
        }
    }

    private fun setSalaryEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_salary_label_selector
        } else {
            R.color.filter_salary_hint_selector
        }

        val colorStateList = ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        textInputLayout.isEndIconVisible = filled
    }

    private fun setViewData(filterParameters: FilterParameters) {
        binding.miFilterLocation.editText?.setText(filterParameters.country?.name)
        binding.miFilterIndustry.editText?.setText(filterParameters.industry?.name)
        binding.miFilterSalary.editText?.setText(filterParameters.salary.toString())
        binding.cbFilterSalaryRequired.isChecked = filterParameters.fSalaryRequired
    }

    private fun showLocation(country: Area?, region: Area?) {
        Log.e("filter", findNavController().currentDestination.toString())
        val action = FilterFragmentDirections.actionFilterFragmentToFilterLocationFragment(
            country,
            //Area("555", "ЙЙЙ"),
            region
        )
       findNavController().navigate(action)
    }

    private fun showIndustry() {
        val action = FilterFragmentDirections.actionFilterFragmentToFilterIndustryFragment()
        findNavController().navigate(action)
    }
}