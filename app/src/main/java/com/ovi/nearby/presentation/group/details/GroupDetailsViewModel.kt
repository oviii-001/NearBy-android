package com.ovi.nearby.presentation.group.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.model.GroupMember
import com.ovi.nearby.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState: StateFlow<GroupDetailsUiState> = _uiState.asStateFlow()

    fun loadGroupDetails(groupId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = groupRepository.getGroup(groupId)) {
                is com.ovi.nearby.core.common.Resource.Success -> _uiState.value = _uiState.value.copy(isLoading = false, group = result.data)
                is com.ovi.nearby.core.common.Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                is com.ovi.nearby.core.common.Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
            }
        }
    }

    fun observeMembers(groupId: String) {
        viewModelScope.launch {
            groupRepository.observeGroupMembers(groupId).collect { members ->
                _uiState.value = _uiState.value.copy(members = members)
            }
        }
    }
}

data class GroupDetailsUiState(
    val group: Group? = null,
    val members: List<GroupMember> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
