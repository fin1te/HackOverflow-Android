package com.fin1te.hackoverflow.model

data class Member(
    val name: String,
    val email: String,
    var avUrl: String,
    val id: String,
    val phone: String
)

data class Team(
    val teamName: String,
    val category: String,
    val members: MutableList<Member> = mutableListOf()
) {
    fun addMember(member: Member) {
        members.add(member)
    }

}

