package ru.practicum.android.diploma.favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.favourites.presentation.adapter.FavouritesVacancyAdapter
import ru.practicum.android.diploma.favourites.presentation.state.FavouritesState
import ru.practicum.android.diploma.favourites.presentation.viewModel.FavouritesViewModel

class FavouritesFragment : Fragment() {


    private val viewModel by viewModel<FavouritesViewModel>()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val favouritesVacancyAdapter = FavouritesVacancyAdapter { vacancy ->
        val bundle = bundleOf("id" to vacancy.id)
        view?.findNavController()
            ?.navigate(R.id.action_favouritesFragment_to_detailFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFavouriteVacancyList()
        viewModel.observeState().observe(viewLifecycleOwner) {
            favouritesVacancyAdapter.setVacancyList(null)
            render(it)
        }

        binding.rvFavouriteList.adapter = favouritesVacancyAdapter
    }

    private fun render(state: FavouritesState) {
        binding.imageListEmpty.isVisible = state is FavouritesState.Empty
        binding.textListEmpty.isVisible = state is FavouritesState.Empty
        binding.imageNoList.isVisible = state is FavouritesState.Error
        binding.textNoList.isVisible = state is FavouritesState.Error
        binding.rvFavouriteList.isVisible = state is FavouritesState.Content
        binding.progress.isVisible = state is FavouritesState.Loading

        if (state is FavouritesState.Content) {
            favouritesVacancyAdapter.setVacancyList(state.vacancyList)
        }
    }
}