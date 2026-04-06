package com.ovi.nearby.di

import com.ovi.nearby.data.repository.AuthRepositoryImpl
import com.ovi.nearby.data.repository.GroupRepositoryImpl
import com.ovi.nearby.data.repository.LocationRepositoryImpl
import com.ovi.nearby.data.repository.UserRepositoryImpl
import com.ovi.nearby.domain.repository.AuthRepository
import com.ovi.nearby.domain.repository.GroupRepository
import com.ovi.nearby.domain.repository.LocationRepository
import com.ovi.nearby.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        groupRepositoryImpl: GroupRepositoryImpl
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
