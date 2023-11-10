package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.databinding.FragmentFilterLocationBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.state.FilterLocationScreenState

class FilterLocationFragment: Fragment() {
    private lateinit var binding: FragmentFilterLocationBinding
    private val args: FilterLocationFragmentArgs by navArgs()

    private val viewModel: FilterLocationViewModel by viewModel {
        parametersOf(args.country, args.region)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(COUNTRY_RESULT_KEY) { _, bundle ->
            val country: Area? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(COUNTRY_RESULT_VAL, Area::class.java)
            } else {
                bundle.getParcelable(COUNTRY_RESULT_VAL)
            }
            viewModel.onCountryChanged(country)
        }

        setFragmentResultListener(REGION_RESULT_KEY) { _, bundle ->
            val region: Area? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(REGION_RESULT_VAL, Area::class.java)
            } else {
                bundle.getParcelable(REGION_RESULT_VAL)
            }
            viewModel.onRegionChanged(region)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getShowCountryTrigger().observe(viewLifecycleOwner) {
            showCountry()
        }

        viewModel.getShowRegionTrigger().observe(viewLifecycleOwner) { country ->
            showRegion(country)
        }

        viewModel.getApplyFilterTrigger().observe(viewLifecycleOwner) { list ->
            applyFilter(list)
        }

        binding.miLocationCountry.editText?.setOnClickListener {
            viewModel.showCountry()
        }

        binding.miLocationRegion.editText?.setOnClickListener {
            viewModel.showRegion()
        }

        binding.miLocationCountry.setEndIconOnClickListener {
            if (binding.miLocationCountry.editText?.text.isNullOrEmpty())
                viewModel.showCountry()
            else {
                viewModel.onCountryChanged(null)
            }
        }

        binding.miLocationRegion.setEndIconOnClickListener {
            if (binding.miLocationRegion.editText?.text.isNullOrEmpty())
                viewModel.showRegion()
            else {
                viewModel.onRegionChanged(null)
            }
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btFilterChoose.setOnClickListener {
            viewModel.applyFilter()
        }
    }

    private fun render(state: FilterLocationScreenState) {
        val country = (state as FilterLocationScreenState.Content).country?.name
        if (country.isNullOrEmpty())
            binding.miLocationCountry.editText?.text = null
        else
            binding.miLocationCountry.editText?.setText(country)
        setMenuEditTextStyle(binding.miLocationCountry, !country.isNullOrEmpty())

        val region = (state).region?.name
        if (region.isNullOrEmpty())
            binding.miLocationRegion.editText?.text = null
        else
            binding.miLocationRegion.editText?.setText(region)
        setMenuEditTextStyle(binding.miLocationRegion, !region.isNullOrEmpty())

        binding.btFilterChoose.isVisible = !country.isNullOrEmpty() || !region.isNullOrEmpty()
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

    private fun showCountry() {
        val action =
            FilterLocationFragmentDirections.actionFilterLocationFragmentToLocationCountryFragment(
            )
        findNavController().navigate(action)
    }

    private fun showRegion(country: Area?) {
        val action =
            FilterLocationFragmentDirections.actionFilterLocationFragmentToLocationRegionFragment(
                country
            )
        findNavController().navigate(action)
    }

    private fun applyFilter(list: List<Area?>) {
        setFragmentResult(
            LOCATION_RESULT_KEY,
            bundleOf(
                COUNTRY_RESULT_VAL to list[0],
                REGION_RESULT_VAL to list[1]
            )
        )
        findNavController().navigateUp()
    }

    companion object {
        const val LOCATION_RESULT_KEY = "location_key"
        const val COUNTRY_RESULT_KEY = "country_key"
        const val REGION_RESULT_KEY = "region_key"
        const val COUNTRY_RESULT_VAL = "country_value"
        const val REGION_RESULT_VAL = "region_value"
    }
}