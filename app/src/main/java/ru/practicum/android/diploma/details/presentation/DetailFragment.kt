package ru.practicum.android.diploma.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val viewModel by viewModels<DetailViewModel>()
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

        viewModel.shopData.observe(viewLifecycleOwner) {
            when (it) {
                is StateHome.Success -> {
                    initHotSalesRecyclerView(it.data.homeStore)
                    initBestSellerRecyclerView(it.data.bestSeller)
                }
                is StateHome.Error -> {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
                is StateHome.Loading -> {}
            }
        }
        binding.filterBt.setOnClickListener {
            showFilter()
        }

        binding.itemFilter.cancelBt.setOnClickListener {
            hideFilter()
        }
    }
}