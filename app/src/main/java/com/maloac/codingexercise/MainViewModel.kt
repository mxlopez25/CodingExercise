package com.maloac.codingexercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maloac.codingexercise.models.Repo
import com.maloac.codingexercise.utils.GitHubAPI
import com.maloac.codingexercise.utils.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    val repoApi = RetrofitHelper.getInstance().create(GitHubAPI::class.java)
    private var _repos = MutableLiveData<MutableList<Repo>>()

    fun getData(): LiveData<MutableList<Repo>> {
        return _repos
    }

    fun getRepos() {
        viewModelScope.launch {
            try {
                val result = repoApi.getRepositories()
                addRepos(result.items as MutableList<Repo>)
            } catch (e: java.lang.Exception) {
                Log.d("Request Error", e.toString())
            }
        }
    }

    suspend fun addRepos(items: MutableList<Repo>) {
        var l: MutableList<Repo> = arrayListOf()
        if(_repos.value == null) {
            for(i in items) {
                withContext(Dispatchers.IO) {
                    try {
                        val tc = async { repoApi.getTopContributors(i.owner.login, i.name) }
                        val t = tc.await().first()
                        i.topContributor = t
                        l.add(i)
                    } catch (e: Exception) {
                        Log.d("RequestError", e.message!!)
                    }
                }
            }
            _repos.value = l
        } else {
            _repos.value?.addAll(items)
        }
    }
}

sealed class RequestState {
    object Loading: RequestState()
    data class Sucess(val repos: List<Repo>): RequestState()
    data class Error(val error: String): RequestState()
}