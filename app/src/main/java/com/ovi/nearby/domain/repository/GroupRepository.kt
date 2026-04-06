package com.ovi.nearby.domain.repository

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.model.GroupMember
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(name: String, description: String): Resource<Group>
    suspend fun getGroup(groupId: String): Resource<Group>
    suspend fun getUserGroups(): Resource<List<Group>>
    suspend fun joinGroupWithCode(inviteCode: String): Resource<Group>
    suspend fun leaveGroup(groupId: String): Resource<Unit>
    suspend fun deleteGroup(groupId: String): Resource<Unit>
    suspend fun updateGroup(groupId: String, name: String, description: String): Resource<Group>

    fun observeGroupMembers(groupId: String): Flow<List<GroupMember>>
    suspend fun addMember(groupId: String, userId: String): Resource<Unit>
    suspend fun removeMember(groupId: String, userId: String): Resource<Unit>
    suspend fun updateMemberRole(groupId: String, userId: String, role: com.ovi.nearby.domain.model.MemberRole): Resource<Unit>
}
