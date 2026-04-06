package com.ovi.nearby.domain.usecase.auth

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.User
import com.ovi.nearby.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return authRepository.currentUser
    }
}
