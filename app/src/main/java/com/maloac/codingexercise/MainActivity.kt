package com.maloac.codingexercise

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView.OnScrollListener
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maloac.codingexercise.databinding.ActivityMainBinding
import com.maloac.codingexercise.models.Repo
import com.maloac.codingexercise.utils.RepoListAdapter

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel : MainViewModel by viewModels()
    private val rvReposeLinearLayoutManager = LinearLayoutManager(this)
    private lateinit var adapter: RepoListAdapter
    private var repoList: MutableList<Repo> = arrayListOf()
    private var pageCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvRepos.layoutManager = rvReposeLinearLayoutManager
        adapter = RepoListAdapter(repoList)
        binding.rvRepos.adapter = adapter

        binding.btnMakeRequest.setOnClickListener {
            try {
                viewModel.getRepos(pageCount)
            } catch (e: java.lang.Exception) {
                Log.d("Error", e.toString())
            }
        }

        viewModel.getRequestState().observe(this) { state ->
            if(state != null) {
                renderUi(state)
            }
        }

        binding.rvRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = rvReposeLinearLayoutManager.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    pageCount++
                    viewModel.getRepos(pageCount)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "Here")
    }

    private fun renderUi(state: RequestState) {
        when (state) {
            is RequestState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                if(repoList.size > 1) {
                    binding.rvRepos.visibility = View.VISIBLE
                } else {
                    binding.rvRepos.visibility = View.INVISIBLE
                }
                binding.btnMakeRequest.isEnabled = false
                binding.tvError.visibility = View.INVISIBLE
            }
            is RequestState.Success -> {
                binding.progressBar.visibility = View.INVISIBLE
                binding.rvRepos.visibility = View.VISIBLE
                binding.btnMakeRequest.isEnabled = true
                binding.tvError.visibility = View.INVISIBLE
                repoList.addAll(state.repos)
                adapter.notifyDataSetChanged()
//                binding.rvRepos.adapter = RepoListAdapter(state.repos)

            }
            is RequestState.Error -> {
                binding.btnMakeRequest.isEnabled = true
                binding.rvRepos.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = state.error
            }
        }
    }

}