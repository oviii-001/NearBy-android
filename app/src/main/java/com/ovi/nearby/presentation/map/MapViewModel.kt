package com.ovi.nearby.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.SharedLocation
import com.ovi.nearby.domain.usecase.location.ObserveGroupLocationsUseCase
import com.ovi.nearby.domain.usecase.location.StartLocationSharingUseCase
import com.ovi.nearby.domain.usecase.location.StopLocationSharingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val observeGroupLocationsUseCase: ObserveGroupLocationsUseCase,
    private val startLocationSharingUseCase: StartLocationSharingUseCase,
    private val stopLocationSharingUseCase: StopLocationSharingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun observeLocations(groupId: String) {
        viewModelScope.launch {
            observeGroupLocationsUseCase(groupId).collect { locations ->
                _uiState.value = _uiState.value.copy(locations = locations)
            }
        }
    }

    fun onStartSharing(groupId: String, durationMinutes: Long) {
        viewModelScope.launch {
            when (val result = startLocationSharingUseCase(groupId, durationMinutes)) {
                is Resource.Success -> _uiState.value = _uiState.value.copy(isSharing = true)
                is Resource.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message ?: "Failed"))
                is Resource.Loading -> {}
            }
        }
    }

    fun onStopSharing(groupId: String) {
        viewModelScope.launch {
            when (val result = stopLocationSharingUseCase(groupId)) {
                is Resource.Success -> _uiState.value = _uiState.value.copy(isSharing = false)
                is Resource.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message ?: "Failed"))
                is Resource.Loading -> {}
            }
        }
    }
}

data class MapUiState(
    val locations: List<SharedLocation> = emptyList(),
    val isSharing: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
