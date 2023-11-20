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
import ru.practicum.android.diploma.util.VACANCY_ID

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

        viewModel.getShareVacancyTrigger().observe(viewLifecycleOwner) { url ->
            actionShare(url)
        }

        viewModel.getShowSimilarVacanciesTrigger().observe(viewLifecycleOwner) { id ->
            showSimilarVacancies(id)
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
                setData(state.data, state.fromDb)
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
    private fun setData(professionDetail: ProfessionDetail, fromDB: Boolean) {
        binding.vacancyName.text = professionDetail.name
        binding.salary.text = professionDetail.salary?.getSalaryToText() ?: getString(R.string.not_salary)

        binding.grExperience.isVisible = professionDetail.experience != null
        binding.experience.text = professionDetail.experience?.name.orEmpty()

        binding.employment.isVisible = professionDetail.employment != null
        binding.employment.text = professionDetail.employment?.name.orEmpty()
        binding.employerName.text = professionDetail.employer?.name.orEmpty()
        binding.employerCity.text = professionDetail.address.orEmpty()

        Glide
            .with(requireContext())
            .load(professionDetail.employer?.logo)
            .placeholder(R.drawable.ic_logo)
            .into(binding.employerLogo)

        if (professionDetail.description != null && professionDetail.description != "...") {
            binding.grDescription.isVisible = true
            binding.description.text =
                Html.fromHtml(professionDetail.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
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
        binding.btSimilar.isVisible = !fromDB
    }

    private fun setDetailsContentListeners(professionDetail: ProfessionDetail) {
        binding.email.setOnClickListener {
            actionSendEmail(professionDetail.email)
        }

        binding.phone.setOnClickListener {
            actionDial(professionDetail.phone)
        }

        binding.btSimilar.setOnClickListener {
            viewModel.showSimilarVacancies(professionDetail.id)
        }

        binding.share.setOnClickListener {
            professionDetail.url?.let { url -> viewModel.shareVacancy(url) }
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
        } catch (_: Exception) {
        }
    }

    private fun actionDial(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
        try {
            startActivity(intent)
        } catch (_: Exception) {
        }
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

    private fun showSimilarVacancies(id: String) {
        val bundle = bundleOf(VACANCY_ID to id)
        view?.findNavController()
            ?.navigate(R.id.action_detailFragment_to_similarFragment, bundle)
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