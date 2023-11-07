package ru.practicum.android.diploma.details.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailBinding
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.formattedNumber
import ru.practicum.android.diploma.util.setSymbolByCurrency

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

        viewModel.state.observe(viewLifecycleOwner) {result->
            when (result) {
                is DetailState.Success -> {
                    setData(result.data)
                }

                is DetailState.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }

                is DetailState.Loading -> {

                }
            }
        }

        binding.back.setOnClickListener {
            view.findNavController().popBackStack()
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
                "$from ${professionDetail.salaryFrom.formattedNumber()} ${
                    setSymbolByCurrency(
                        professionDetail.salaryCurrency
                    )
                }"
        } else if (professionDetail.salaryFrom == null && professionDetail.salaryTo != null) {
            binding.salary.text =
                "$to ${professionDetail.salaryTo.formattedNumber()} ${
                    setSymbolByCurrency(
                        professionDetail.salaryCurrency
                    )
                }"
        } else if (professionDetail.salaryFrom != null && professionDetail.salaryTo != null) {
            binding.salary.text =
                "$from ${professionDetail.salaryFrom.formattedNumber()} $to ${professionDetail.salaryTo.formattedNumber()} ${
                    setSymbolByCurrency(
                        professionDetail.salaryCurrency
                    )
                }"
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
        binding.contacts.isVisible = !(professionDetail.comment==null
                &&professionDetail.phone==null
                &&professionDetail.email==null
                &&professionDetail.contactName==null)
        if (professionDetail.comment!=null) {
            binding.commentName.isVisible = true
            binding.comment.isVisible = true
            binding.comment.text = professionDetail.comment
        } else {
            binding.commentName.isVisible = false
            binding.comment.isVisible = false
        }

        if (professionDetail.contactName!=null) {
            binding.contactFace.isVisible = true
            binding.contactName.isVisible = true
            binding.contactName.text = professionDetail.contactName
        } else {
            binding.contactFace.isVisible = false
            binding.contactName.isVisible = false
        }
        if (professionDetail.phone!=null) {
            binding.phoneName.isVisible = true
            binding.phone.isVisible = true
            binding.phone.text = professionDetail.phone
        } else {
            binding.phoneName.isVisible = false
            binding.phone.isVisible = false
        }

        if (professionDetail.email!=null) {
            binding.emailName.isVisible = true
            binding.email.isVisible = true
            binding.email.text = professionDetail.email
        } else {
            binding.emailName.isVisible = false
            binding.email.isVisible = false
        }
        binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${professionDetail.email}")
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${professionDetail.phone}"))
            startActivity(intent)
        }
        binding.btSimilar.setOnClickListener {
            val bundle = bundleOf("id_vacancy" to professionDetail.id)
            view?.findNavController()?.navigate(R.id.action_detailFragment_to_similarFragment, bundle)
        }
        binding.share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, professionDetail.url)
            startActivity(Intent.createChooser(shareIntent, "Поделиться ссылкой"))
        }
    }
}