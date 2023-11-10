package ru.practicum.android.diploma.details.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.api.SimilarInterActor
import ru.practicum.android.diploma.details.presentation.models.SimilarState
import ru.practicum.android.diploma.util.Resource

class SimilarViewModel (
    private val similarInterActor: SimilarInterActor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableLiveData<SimilarState>(SimilarState.Loading)
    val state = _state

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("id_vacancy") ?: return@launch
            when (val resultData = similarInterActor.getSimilarVacancies(id = id)) {
                is Resource.Error -> {
                    _state.value =
                        SimilarState.Error(
                            message = resultData.message ?: "An unknown error",
                            errorImagePath = resultData.errorImagePath
                                ?: R.drawable.error_vacancy_dm
                        )
                }
                is Resource.Success -> {
                    _state.value =
                        resultData.data?.let {
                            SimilarState.Success(data = it)
                        }
                }
            }
        }
    }
}