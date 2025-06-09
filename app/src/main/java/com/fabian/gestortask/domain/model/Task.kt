package com.fabian.gestortask.domain.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val userId: String = ""
)

