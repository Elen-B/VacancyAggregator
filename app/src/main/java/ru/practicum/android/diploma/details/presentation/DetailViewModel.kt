package ru.practicum.android.diploma.details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.impl.DetailRepository
import ru.practicum.android.diploma.util.Resource

class DetailViewModel (
    private val repo: DetailRepository,
    //private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableLiveData<DetailState>(DetailState.Loading)
    val state = _state

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            //val id = savedStateHandle.get<String>("id") ?: return@launch
            when (val resultData = repo.getVacancyDetail(id = "5021")) {
                is Resource.Error -> {
                    _state.value =
                        DetailState.Error(resultData.message ?: "An unknown error")
                }
                is Resource.Success -> {
                    _state.value =
                        resultData.data?.let {
                            DetailState.Success(data = it)
                        }
                }
            }
        }
    }
}