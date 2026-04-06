package com.ovi.nearby.presentation.group.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.core.common.UiEvent
import com.ovi.nearby.domain.usecase.group.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState: StateFlow<CreateGroupUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameChange(name: String) { _uiState.value = _uiState.value.copy(name = name) }
    fun onDescriptionChange(desc: String) { _uiState.value = _uiState.value.copy(description = desc) }

    fun onCreateGroup() {
        val state = _uiState.value
        if (state.name.isBlank()) {
            viewModelScope.launch { _uiEvent.send(UiEvent.ShowToast("Group name is required")) }
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = createGroupUseCase(state.name, state.description)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _uiEvent.send(UiEvent.NavigateUp)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                    _uiEvent.send(UiEvent.ShowSnackbar(result.message ?: "Failed"))
                }
                is Resource.Loading -> { _uiState.value = _uiState.value.copy(isLoading = true) }
            }
        }
    }
}

data class CreateGroupUiState(
    val name: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
