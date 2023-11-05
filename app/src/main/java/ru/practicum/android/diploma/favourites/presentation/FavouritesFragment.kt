package ru.practicum.android.diploma.favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.favourites.presentation.model.FavouritesState
import ru.practicum.android.diploma.favourites.presentation.viewModel.FavouritesViewModel
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesFragment: Fragment() {


    private val viewModel by viewModel<FavouritesViewModel>()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

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
         render(it)
     }

    }

    private fun render(state: FavouritesState){
        when(state){
            is FavouritesState.Empty -> showEmpty()
            is FavouritesState.Error -> showError()
            is FavouritesState.Content -> showContent(state.vacancyList)
        }
    }

    private fun showEmpty(){
        binding.imageListEmpty.isVisible = true
        binding.textListEmpty.isVisible = true
        binding.imageNoList.isVisible = false
        binding.textNoList.isVisible = false
    }

    private fun showError(){
        binding.imageListEmpty.isVisible = false
        binding.textListEmpty.isVisible = false
        binding.imageNoList.isVisible = true
        binding.textNoList.isVisible = true
    }

    private fun showContent(vacancyList: List<SearchVacancy>){
        binding.imageListEmpty.isVisible = false
        binding.textListEmpty.isVisible = false
        binding.imageNoList.isVisible = false
        binding.textNoList.isVisible = false
    }
}