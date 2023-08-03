package com.example.onlinevotingsystem

data class CandidateCard(
    val name:String,
    val age:Long,
    val email:String,
    val city:String,
    var votes:Long=0
)
