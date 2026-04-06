package com.ovi.nearby.domain.usecase.location

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.repository.LocationRepository
import javax.inject.Inject

class StopLocationSharingUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(groupId: String): Resource<Unit> {
        return locationRepository.stopLocationSharing(groupId)
    }
}
