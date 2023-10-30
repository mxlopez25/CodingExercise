package com.maloac.codingexercise.mock

import com.maloac.codingexercise.models.Repo
import com.maloac.codingexercise.models.RepoResponse
import com.maloac.codingexercise.models.RepoUser

val repoUser1 = RepoUser(
    "User1",
    1,
    "https://example1.com",
    "https://example1.com/html",
    "https://example1.com/avatar",
    100
)
val repoUser2 = RepoUser(
    "User2",
    2,
    "https://example2.com",
    "https://example2.com/html",
    "https://example2.com/avatar",
    101
)
val repoUser3 = RepoUser(
    "User3",
    3,
    "https://example3.com",
    "https://example3.com/html",
    "https://example3.com/avatar",
    100
)
val repo1 = Repo(
    1,
    "Test_1",
    "",
    false,
    repoUser1,
    "First Test Repo Description",
    "https://repo.test1.com/contributors",
    repoUser2
)
val repo2 = Repo(
    2,
    "Test_2",
    "User2/Test_2",
    false,
    repoUser2,
    "Second Test Repo Description",
    "https://repo.test2.com/contributors",
    repoUser3
)
val repo3 = Repo(
    3,
    "Test_3",
    "User3/Test_3",
    false,
    repoUser3,
    "Third Test Repo Description",
    "https://repo.test3.com/contributors",
    repoUser1
)

val mockRepoData = listOf(repo1, repo2, repo3)

val mockRepoResponse = RepoResponse(3, false, mockRepoData)
