package ru.practicum.android.diploma.details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding
import ru.practicum.android.diploma.details.presentation.state.SimilarState
import ru.practicum.android.diploma.details.presentation.viewmodel.SimilarViewModel
import ru.practicum.android.diploma.util.VACANCY_ID

class SimilarFragment : Fragment() {
    private var _binding: FragmentSimilarBinding? = null
    private val viewModel: SimilarViewModel by viewModel()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.btTopBarBack.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    private fun renderState(state: SimilarState) {
        when (state) {
            is SimilarState.Success -> {
                setData(state.data)
            }

            is SimilarState.Error -> {
                setError(
                    message = state.message,
                    imagePath = state.errorImagePath
                )
            }

            is SimilarState.Loading -> {
                setLoad()
            }
        }
    }

    private fun setError(
        message: String,
        imagePath: Int
    ) {
        with(binding.include) {
            imError.setImageResource(imagePath)
            tvError.text = message
        }
        binding.include.root.isVisible = true
        binding.rvSimilar.isVisible = false
        binding.progress.isVisible = false
    }

    private fun setData(data: List<Vacancy>) {
        binding.rvSimilar.isVisible = true
        binding.rvSimilar.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val stateClickListener: SimilarAdapter.OnStateClickListener =
            object : SimilarAdapter.OnStateClickListener {
                override fun onStateClick(item: Vacancy, position: Int) {
                    val bundle = bundleOf(VACANCY_ID to item.id)
                    view?.findNavController()
                        ?.navigate(R.id.action_similarFragment_to_detailFragment, bundle)
                }
            }
        val adapter = SimilarAdapter(data, stateClickListener)
        binding.rvSimilar.adapter = adapter
        binding.progress.isVisible = false
        binding.include.root.isVisible =  false
    }

    private fun setLoad() {
        binding.rvSimilar.isVisible = false
        binding.progress.isVisible = true
        binding.include.root.isVisible =  false
    }
}