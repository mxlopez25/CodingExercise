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

class MainViewModel : ViewModel() {
    private val repoApi: GitHubAPI = RetrofitHelper.getInstance().create(GitHubAPI::class.java)
    private var _state: MutableLiveData<RequestState> = MutableLiveData()

    fun getRequestState(): LiveData<RequestState> = _state

    fun getRepos(page: Int) {
        _state.value = RequestState.Loading
        viewModelScope.launch {
            try {
                val result = repoApi.getRepositories(page)
                addRepos(result.items as MutableList<Repo>)
            } catch (e: java.lang.Exception) {
                _state.value = RequestState.Error(e.message!!)
                Log.d("Request Error", e.toString())
            }
        }
    }

    suspend fun addRepos(items: MutableList<Repo>) {
        var l: MutableList<Repo> = arrayListOf()
        for (i in items) {
            withContext(Dispatchers.IO) {
                val tc = async { repoApi.getTopContributors(i.owner.login, i.name) }.await().first()
                i.topContributor = tc
                l.add(i)
            }
        }
        _state.value = RequestState.Success(l)
    }
}

sealed class RequestState {
    object Loading : RequestState()
    data class Success(val repos: List<Repo>) : RequestState()
    data class Error(val error: String) : RequestState()
}