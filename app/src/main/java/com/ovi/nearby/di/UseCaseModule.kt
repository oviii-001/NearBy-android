package com.ovi.nearby.di

import com.ovi.nearby.domain.usecase.auth.ObserveCurrentUserUseCase
import com.ovi.nearby.domain.usecase.auth.RegisterUseCase
import com.ovi.nearby.domain.usecase.auth.SignInUseCase
import com.ovi.nearby.domain.usecase.auth.SignOutUseCase
import com.ovi.nearby.domain.usecase.group.CreateGroupUseCase
import com.ovi.nearby.domain.usecase.group.GetUserGroupsUseCase
import com.ovi.nearby.domain.usecase.group.JoinGroupUseCase
import com.ovi.nearby.domain.usecase.location.ObserveGroupLocationsUseCase
import com.ovi.nearby.domain.usecase.location.StartLocationSharingUseCase
import com.ovi.nearby.domain.usecase.location.StopLocationSharingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignInUseCase(
        authRepository: com.ovi.nearby.domain.repository.AuthRepository
    ) = SignInUseCase(authRepository)

    @Provides
    fun provideRegisterUseCase(
        authRepository: com.ovi.nearby.domain.repository.AuthRepository
    ) = RegisterUseCase(authRepository)

    @Provides
    fun provideObserveCurrentUserUseCase(
        authRepository: com.ovi.nearby.domain.repository.AuthRepository
    ) = ObserveCurrentUserUseCase(authRepository)

    @Provides
    fun provideSignOutUseCase(
        authRepository: com.ovi.nearby.domain.repository.AuthRepository
    ) = SignOutUseCase(authRepository)

    @Provides
    fun provideCreateGroupUseCase(
        groupRepository: com.ovi.nearby.domain.repository.GroupRepository
    ) = CreateGroupUseCase(groupRepository)

    @Provides
    fun provideGetUserGroupsUseCase(
        groupRepository: com.ovi.nearby.domain.repository.GroupRepository
    ) = GetUserGroupsUseCase(groupRepository)

    @Provides
    fun provideJoinGroupUseCase(
        groupRepository: com.ovi.nearby.domain.repository.GroupRepository
    ) = JoinGroupUseCase(groupRepository)

    @Provides
    fun provideStartLocationSharingUseCase(
        locationRepository: com.ovi.nearby.domain.repository.LocationRepository
    ) = StartLocationSharingUseCase(locationRepository)

    @Provides
    fun provideStopLocationSharingUseCase(
        locationRepository: com.ovi.nearby.domain.repository.LocationRepository
    ) = StopLocationSharingUseCase(locationRepository)

    @Provides
    fun provideObserveGroupLocationsUseCase(
        locationRepository: com.ovi.nearby.domain.repository.LocationRepository
    ) = ObserveGroupLocationsUseCase(locationRepository)
}
