package com.ovi.nearby.domain.model

data class User(
    val id: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L,
    val createdAt: Long = 0L
)
