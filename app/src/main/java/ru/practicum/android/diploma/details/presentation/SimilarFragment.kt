package ru.practicum.android.diploma.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
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
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }

                is SimilarState.Loading -> {

                }
            }
        }
        binding.back.setOnClickListener {

        }

    }

    private fun setData(data: List<ProfessionSimillar>) {
        binding.rvSimilar.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val stateClickListener: SimilarAdapter.OnStateClickListener =
            object : SimilarAdapter.OnStateClickListener {
                override fun onStateClick(item: ProfessionSimillar, position: Int) {

                }
            }
        val adapter = SimilarAdapter(data, stateClickListener, requireContext())
        binding.rvSimilar.adapter = adapter
    }
}