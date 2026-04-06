package com.ovi.nearby.domain.usecase.group

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(name: String, description: String): Resource<Group> {
        return groupRepository.createGroup(name, description)
    }
}
