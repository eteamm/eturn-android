package com.eturn.data

data class Turn(
    var id: Int,
    var name: String,
    var description: String,
    var nameCreator: String,
    var idUser : Int,
    var numberOfPeople: Int
)
