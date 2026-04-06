package com.ovi.nearby.domain.usecase.auth

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.model.User
import com.ovi.nearby.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Resource<User> {
        return authRepository.registerWithEmail(name, email, password)
    }
}
