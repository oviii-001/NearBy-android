package com.ovi.nearby.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.usecase.group.GetUserGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserGroupsUseCase: GetUserGroupsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { loadGroups() }

    fun loadGroups() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getUserGroupsUseCase()) {
                is Resource.Success -> _uiState.value = _uiState.value.copy(isLoading = false, groups = result.data ?: emptyList())
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
            }
        }
    }
}

data class HomeUiState(
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
