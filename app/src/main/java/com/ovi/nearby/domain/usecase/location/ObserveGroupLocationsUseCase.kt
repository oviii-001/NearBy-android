package com.ovi.nearby.domain.usecase.location

import com.ovi.nearby.domain.model.SharedLocation
import com.ovi.nearby.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGroupLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(groupId: String): Flow<List<SharedLocation>> {
        return locationRepository.observeGroupLocations(groupId)
    }
}
