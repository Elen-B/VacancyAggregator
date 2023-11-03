package ru.practicum.android.diploma.details.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailBinding
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.formattedNumber

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModel()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is DetailState.Success -> {
                    setData(it.data)
                }

                is DetailState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

                is DetailState.Loading -> {

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(professionDetail: ProfessionDetail) {
        binding.vacancyName.text = professionDetail.name
        val from = getString(R.string.from)
        val to = getString(R.string.to)
        val notSalary = getString(R.string.not_salary)
        val description = Html.fromHtml(professionDetail.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        if (professionDetail.salaryFrom == null && professionDetail.salaryTo == null) {
            binding.salary.text = notSalary
        } else if (professionDetail.salaryFrom != null && professionDetail.salaryTo == null) {
            binding.salary.text =
                "$from ${professionDetail.salaryFrom.formattedNumber()} ${professionDetail.salaryCurrency}"
        } else if (professionDetail.salaryFrom == null && professionDetail.salaryTo != null) {
            binding.salary.text =
                "$to ${professionDetail.salaryTo.formattedNumber()} ${professionDetail.salaryCurrency}"
        } else if (professionDetail.salaryFrom != null && professionDetail.salaryTo != null) {
            binding.salary.text =
                "$from ${professionDetail.salaryFrom.formattedNumber()} $to ${professionDetail.salaryTo.formattedNumber()} ${professionDetail.salaryCurrency}"
        }
        if (professionDetail.experienceName == null) {
            binding.experience.isVisible = false
            binding.requiredExperience.isVisible = false
        } else {
            binding.experience.isVisible = true
            binding.requiredExperience.isVisible = true
            binding.experience.text = professionDetail.experienceName
        }
        if (professionDetail.employmentName == null) {
            binding.employment.isVisible = false
        } else {
            binding.employment.isVisible = true
            binding.employment.text = professionDetail.employmentName
        }
        binding.employerName.text = professionDetail.employerName
        binding.employerCity.text = professionDetail.employerCity
        if (professionDetail.employerLogo!=null) {
            Glide
                .with(requireContext())
                .load(professionDetail.employerLogo)
                .into(binding.employerLogo)
        }
        if (professionDetail.description!=null&&professionDetail.description!="...") {
            binding.vacancyDescription.isVisible = true
            binding.description.isVisible = true
            binding.description.text = description
        } else {
            binding.vacancyDescription.isVisible = false
            binding.description.isVisible = false
        }
        if (professionDetail.keySkills!=null&&professionDetail.keySkills!="") {
            binding.keySkills.isVisible = true
            binding.keySkillsContent.isVisible = true
            binding.keySkillsContent.text = professionDetail.keySkills
        } else {
            binding.keySkills.isVisible = false
            binding.keySkillsContent.isVisible = false
        }
    }
}