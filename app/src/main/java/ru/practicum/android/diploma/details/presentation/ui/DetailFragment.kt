package ru.practicum.android.diploma.details.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.practicum.android.diploma.details.presentation.state.DetailState
import ru.practicum.android.diploma.details.presentation.viewmodel.DetailViewModel
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

        viewModel.state.observe(viewLifecycleOwner) { result ->
            render(result)
        }

        viewModel.observeStateInFavourites().observe(viewLifecycleOwner) { isFavourite ->
            renderFavourite(isFavourite)
        }

        binding.back.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun renderFavourite(isFavourite: Boolean) {
        if (isFavourite) {
            binding.favorite.setImageResource(R.drawable.trailing_icon_2)
        } else {
            binding.favorite.setImageResource(R.drawable.trailing_icon_2__1_)
        }
    }

    private fun render(state: DetailState) {
        when (state) {
            is DetailState.Success -> {
                setData(state.data)
            }

            is DetailState.Error -> {
                showError()
            }

            is DetailState.Loading -> {
                showProgress()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(professionDetail: ProfessionDetail) {
        binding.vacancyName.text = professionDetail.name
        val from = getString(R.string.from)
        val to = getString(R.string.to)
        val notSalary = getString(R.string.not_salary)
        val description =
            Html.fromHtml(professionDetail.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
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

        binding.grExperience.isVisible = !professionDetail.experienceName.isNullOrEmpty()
        binding.experience.text = professionDetail.experienceName.orEmpty()

        binding.employment.isVisible = !professionDetail.employmentName.isNullOrEmpty()
        binding.employment.text = professionDetail.employmentName.orEmpty()
        binding.employerName.text = professionDetail.employerName
        binding.employerCity.text = professionDetail.employerCity.orEmpty()

        Glide
            .with(requireContext())
            .load(professionDetail.employerLogo)
            .placeholder(R.drawable.vacancy_item_search_placeholder)
            .into(binding.employerLogo)

        if (professionDetail.description != null && professionDetail.description != "...") {
            binding.grDescription.isVisible = true
            binding.description.text = description
        } else {
            binding.grDescription.isVisible = false
        }

        binding.grSkills.isVisible = !professionDetail.keySkills.isNullOrEmpty()
        binding.keySkillsContent.text = professionDetail.keySkills.orEmpty()

        binding.contacts.isVisible = !(professionDetail.comment == null
                && professionDetail.phone == null
                && professionDetail.email == null
                && professionDetail.contactName == null)

        binding.grComment.isVisible = !professionDetail.comment.isNullOrEmpty()
        binding.comment.text = professionDetail.comment.orEmpty()

        binding.grContact.isVisible = !professionDetail.contactName.isNullOrEmpty()
        binding.contactName.text = professionDetail.contactName.orEmpty()

        binding.grPhone.isVisible = !professionDetail.phone.isNullOrEmpty()
        binding.phone.text = professionDetail.phone.orEmpty()

        binding.grEmail.isVisible = !professionDetail.email.isNullOrEmpty()
        binding.email.text = professionDetail.email.orEmpty()

        setDetailsContentListeners(professionDetail)

        binding.progress.isVisible = false
        binding.scroll.isVisible = true
        binding.phDetailsError.isVisible = false
    }

    private fun setDetailsContentListeners(professionDetail: ProfessionDetail) {
        binding.email.setOnClickListener {
            actionSendEmail(professionDetail.email)
        }

        binding.phone.setOnClickListener {
            actionDial(professionDetail.phone)
        }

        binding.btSimilar.setOnClickListener {
            val bundle = bundleOf("id_vacancy" to professionDetail.id)
            view?.findNavController()
                ?.navigate(R.id.action_detailFragment_to_similarFragment, bundle)
        }

        binding.share.setOnClickListener {
            actionShare(professionDetail.url)
        }

        binding.favorite.setOnClickListener {
            viewModel.onFavouriteClick()
        }
    }

    private fun actionSendEmail(email: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:${email}")
        try {
            startActivity(intent)
        } catch (_: Exception) {}
    }

    private fun actionDial(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
        try {
            startActivity(intent)
        } catch (_: Exception) {}
    }

    private fun actionShare(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        try {
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.share_message_title)
                )
            )
        } catch (_: Exception) {
        }
    }

    private fun showProgress() {
        binding.progress.isVisible = true
        binding.scroll.isVisible = false
        binding.phDetailsError.isVisible = false
    }

    private fun showError() {
        binding.progress.isVisible = false
        binding.scroll.isVisible = false
        binding.phDetailsError.isVisible = true
    }
}