package com.maloac.codingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.maloac.codingexercise.databinding.ActivityMainBinding
import com.maloac.codingexercise.models.Repo
import com.maloac.codingexercise.utils.RepoListAdapter

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvRepos.layoutManager = LinearLayoutManager(this)
        val adapter = RepoListAdapter(emptyList<Repo>())
        binding.rvRepos.adapter = adapter

        binding.btnMakeRequest.setOnClickListener {
            try {
                viewModel.getRepos()
            } catch (e: java.lang.Exception) {
                Log.d("Error", e.toString())
            }
        }

        viewModel.getData().observe(this, Observer {
            binding.rvRepos.adapter = RepoListAdapter(it)
        })
    }
}