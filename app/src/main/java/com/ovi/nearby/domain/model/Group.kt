package com.ovi.nearby.domain.model

data class Group(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val createdBy: String = "",
    val createdAt: Long = 0L,
    val memberCount: Int = 0,
    val inviteCode: String = ""
)
