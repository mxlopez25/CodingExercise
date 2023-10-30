package com.maloac.codingexercise.models

data class RepoUser(
    val login: String,
    val id: Int,
    val url: String,
    val htmlUrl: String,
    val avatarUrl: String,
    var contributions: Int?
)
