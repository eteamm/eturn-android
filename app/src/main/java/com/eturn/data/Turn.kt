package com.eturn.data

data class Turn(
    var id: Long,
    var name: String,
    var description: String,
    var creator: String,
    var userId : Long,
    var countUsers: Int
)
