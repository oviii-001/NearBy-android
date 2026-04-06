package com.ovi.nearby.domain.repository

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(userId: String): Resource<User>
    suspend fun getUsers(userIds: List<String>): Resource<List<User>>
    fun observeUser(userId: String): Flow<User?>
    suspend fun updateUserStatus(isOnline: Boolean): Resource<Unit>
    suspend fun searchUsers(query: String): Resource<List<User>>
}
