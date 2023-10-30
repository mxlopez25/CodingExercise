package com.maloac.codingexercise.models

data class RepoResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<Repo>
)
