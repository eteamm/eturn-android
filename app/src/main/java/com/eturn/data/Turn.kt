package com.eturn.data

data class Turn(
    var id: Int,
    var name: String,
    var description: String,
    var creator: String,
    var userId : Int,
    var countUsers: Int
)
