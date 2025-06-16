package com.fabian.gestortask.domain.model

data class TaskList(
    var id: String = "",
    var name: String = "",
    var userId: String = "",
    val defaultList: Boolean = false
)