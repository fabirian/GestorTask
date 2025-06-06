package com.fabian.gestortask.domain.model

data class Task(
    val id: Comparable<*> = 0,
    val title: String,
    val description: String,
    val isDone: Boolean
)
