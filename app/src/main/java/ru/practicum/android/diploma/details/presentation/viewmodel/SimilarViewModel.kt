package ru.practicum.android.diploma.details.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.api.SimilarInterActor
import ru.practicum.android.diploma.details.presentation.state.SimilarState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.UNKNOWN_ERROR
import ru.practicum.android.diploma.util.VACANCY_ID

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
            val id = savedStateHandle.get<String>(VACANCY_ID) ?: return@launch
            when (val resultData = similarInterActor.getSimilarVacancies(id = id)) {
                is Resource.Error -> {
                    _state.value =
                        SimilarState.Error(
                            message = resultData.message ?: UNKNOWN_ERROR,
                            errorImagePath = resultData.errorImagePath
                                ?: R.drawable.server_error
                        )
                }
                is Resource.Success -> {
                    _state.value = if (resultData.data.isNullOrEmpty()) {
                        SimilarState.Empty
                    } else {
                        SimilarState.Success(data = resultData.data)
                    }
                }
            }
        }
    }
}