package ru.practicum.android.diploma.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar

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

        viewModel.state.observe(viewLifecycleOwner) {result->
            when (result) {
                is SimilarState.Success -> {
                    setData(result.data)
                }

                is SimilarState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        getString(result.message.toInt()),
                        Toast.LENGTH_LONG
                    ).show()
                    setError(
                        message = result.message.toInt(),
                        imagePath = result.errorImagePath
                    )
                }

                is SimilarState.Loading -> {
                        setLoad()
                }
            }
        }
        binding.back.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

    private fun setError(
        message: Int,
        imagePath: Int
    ) {
        with(binding.include) {
            imError.isVisible = true
            tvError.isVisible = true
            imError.setImageResource(imagePath)
            tvError.text = getString(message)
        }
        binding.rvSimilar.isVisible = false
        binding.progress.isVisible = false
    }

    private fun setData(data: List<ProfessionSimillar>) {
        binding.rvSimilar.isVisible = true
        binding.rvSimilar.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val stateClickListener: SimilarAdapter.OnStateClickListener =
            object : SimilarAdapter.OnStateClickListener {
                override fun onStateClick(item: ProfessionSimillar, position: Int) {
                    val bundle = bundleOf("id" to item.id)
                    view?.findNavController()
                        ?.navigate(R.id.action_similarFragment_to_detailFragment, bundle)
                }
            }
        val adapter = SimilarAdapter(data, stateClickListener, requireContext())
        binding.rvSimilar.adapter = adapter
        binding.progress.isVisible = false
        with(binding.include) {
            imError.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun setLoad() {
        binding.rvSimilar.isVisible = false
        binding.progress.isVisible = true
        with(binding.include) {
            imError.isVisible = false
            tvError.isVisible = false
        }
    }
}