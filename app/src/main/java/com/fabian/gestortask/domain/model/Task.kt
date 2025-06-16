package com.fabian.gestortask.domain.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val listId: String = "",
    val userId: String = ""
)

