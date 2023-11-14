package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.models.FilterScreenState

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val args: FilterFragmentArgs by navArgs()

    private val viewModel: FilterViewModel by viewModel {
        parametersOf(args.filter)
    }

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

        setFragmentResultListener(FilterLocationFragment.LOCATION_RESULT_KEY) { _, bundle ->
            val country: Area? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(FilterLocationFragment.COUNTRY_RESULT_VAL, Area::class.java)
            } else {
                bundle.getParcelable(FilterLocationFragment.COUNTRY_RESULT_VAL)
            }
            val region: Area? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(FilterLocationFragment.REGION_RESULT_VAL, Area::class.java)
            } else {
                bundle.getParcelable(FilterLocationFragment.REGION_RESULT_VAL)
            }
            viewModel.onLocationChanged(country, region)
        }

        setFragmentResultListener(FilterIndustryFragment.INDUSTRY_RESULT_KEY) { _, bundle ->
            val industry: Industry? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(
                    FilterIndustryFragment.INDUSTRY_RESULT_VAL,
                    Industry::class.java
                )
            } else {
                bundle.getParcelable(FilterIndustryFragment.INDUSTRY_RESULT_VAL)
            }
            viewModel.onIndustryChanged(industry)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getShowLocationTrigger().observe(viewLifecycleOwner) { filterParameters ->
            showLocation(filterParameters.country, filterParameters.region)
        }

        viewModel.getShowIndustryTrigger().observe(viewLifecycleOwner) { industry ->
            showIndustry(industry)
        }

        viewModel.getSaveFilterTrigger().observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.miFilterLocation.editText?.setOnClickListener {
            viewModel.showLocation()
        }

        binding.miFilterIndustry.editText?.setOnClickListener {
            viewModel.showIndustry()
        }

        binding.miFilterLocation.setEndIconOnClickListener {
            if (binding.miFilterLocation.editText?.text.isNullOrEmpty())
                viewModel.showLocation()
            else {
                viewModel.onLocationChanged(null, null)
            }
        }

        binding.miFilterIndustry.setEndIconOnClickListener {
            if (binding.miFilterIndustry.editText?.text.isNullOrEmpty())
                viewModel.showIndustry()
            else {
                viewModel.onIndustryChanged(null)
            }
        }

        binding.miFilterSalary.setEndIconOnClickListener {
            binding.miFilterSalary.editText?.text = null
            it.visibility = View.GONE
        }

        binding.miFilterSalary.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.onSalaryChanged(text.toString())
        }

        binding.miFilterSalary.editText?.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.miFilterSalary.editText?.clearFocus()
                binding.miFilterSalary.isEndIconVisible = false
            }
            false
        }

        binding.miFilterSalary.editText?.setOnFocusChangeListener { _, hasFocus ->
            binding.miFilterSalary.isEndIconVisible =
                hasFocus && (binding.miFilterSalary.editText?.text?.isNotEmpty() ?: false)
        }

        binding.cbFilterSalaryRequired.setOnCheckedChangeListener { _, checked ->
            viewModel.onFSalaryRequiredChanged(checked)
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btFilterApply.setOnClickListener {
            viewModel.saveFilterParameters()
            findNavController().navigateUp()
        }

        binding.btFilterClear.setOnClickListener {
            viewModel.onClearFilterClick()
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
        }
    }

    private fun setViewAppearance(state: FilterScreenState) {
        val filterParameters = when (state) {
            is FilterScreenState.Started -> state.data
            is FilterScreenState.Modified -> state.data
        }

        setMenuEditTextStyle(
            binding.miFilterLocation,
            !filterParameters.country?.name.isNullOrEmpty() || !filterParameters.region?.name.isNullOrEmpty()
        )
        if (filterParameters != null) {
            setMenuEditTextStyle(
                binding.miFilterLocation,
                !filterParameters.country?.name.isNullOrEmpty() || !filterParameters.region?.name.isNullOrEmpty()
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

            binding.btFilterApply.isVisible = state is FilterScreenState.Modified
        }
    }

    private fun setMenuEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_menu_label_selector
        } else {
            R.color.filter_menu_hint_selector
        }

        val colorStateList =
            ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
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

        val colorStateList =
            ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        textInputLayout.isEndIconVisible =
            filled && (textInputLayout.editText?.hasFocus() ?: false)
    }

    private fun setViewData(filterParameters: FilterParameters) {
        val location =
            (filterParameters.country?.name.orEmpty() + ", " + filterParameters.region?.name.orEmpty())
                .trim(',', ' ', ',')
        binding.miFilterLocation.editText?.setText(location)
        binding.miFilterIndustry.editText?.setText(filterParameters.industry?.name)
        if (filterParameters.salary == null)
            binding.miFilterSalary.editText?.text = null
        else
            binding.miFilterSalary.editText?.setText(filterParameters.salary.toString())
        binding.cbFilterSalaryRequired.isChecked = filterParameters.fSalaryRequired
    }

    private fun showLocation(country: Area?, region: Area?) {
        val action = FilterFragmentDirections.actionFilterFragmentToFilterLocationFragment(
            country,
            region
        )
        findNavController().navigate(action)
    }

    private fun showIndustry(industry: Industry?) {
        val action = FilterFragmentDirections.actionFilterFragmentToFilterIndustryFragment(
            industry
        )
        findNavController().navigate(action)
    }
}