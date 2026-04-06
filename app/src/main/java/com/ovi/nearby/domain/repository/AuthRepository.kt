package com.ovi.nearby.domain.repository

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String?
    val currentUser: Flow<User?>

    suspend fun signInWithEmail(email: String, password: String): Resource<User>
    suspend fun registerWithEmail(name: String, email: String, password: String): Resource<User>
    suspend fun signInWithGoogle(): Resource<User>
    suspend fun signOut()
    fun resetPassword(email: String): Flow<Resource<Unit>>
    suspend fun updateProfile(displayName: String, photoUrl: String?): Resource<User>
}
