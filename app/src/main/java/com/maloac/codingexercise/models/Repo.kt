package com.maloac.codingexercise.models

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val private: Boolean,
    val owner: RepoUser,
    val description: String,
    @SerializedName("contributors_url")
    val contributorsUrl: String,
    var topContributor: RepoUser?
)
