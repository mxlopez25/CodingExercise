package com.maloac.codingexercise.utils

import com.maloac.codingexercise.models.Repo
import com.maloac.codingexercise.models.RepoResponse
import com.maloac.codingexercise.models.RepoUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPI {
    @GET("/search/repositories?q=stars:>0")
    suspend fun getRepositories(): RepoResponse

    @GET("/repos/{login}/{repoName}/contributors")
    suspend fun getTopContributors
                (@Path("login") login: String, @Path("repoName") repoName:String ): List<RepoUser>

}