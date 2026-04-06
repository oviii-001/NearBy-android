package com.ovi.nearby.domain.usecase.group

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.repository.GroupRepository
import javax.inject.Inject

class GetUserGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(): Resource<List<Group>> {
        return groupRepository.getUserGroups()
    }
}
