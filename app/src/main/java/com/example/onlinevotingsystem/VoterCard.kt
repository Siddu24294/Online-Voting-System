package com.example.onlinevotingsystem

data class VoterCard(
    val name:String? = null,
    val phone:String? = null,
    val age: Int? = null,
    val city:String? = null,
    var status: String ="N"
)
