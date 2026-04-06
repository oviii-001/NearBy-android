package com.ovi.nearby.domain.usecase.location

import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.domain.repository.LocationRepository
import javax.inject.Inject

class StartLocationSharingUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(groupId: String, durationMinutes: Long): Resource<Unit> {
        return locationRepository.startLocationSharing(groupId, durationMinutes)
    }
}
