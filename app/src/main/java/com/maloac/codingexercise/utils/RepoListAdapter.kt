package com.maloac.codingexercise.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maloac.codingexercise.R
import com.maloac.codingexercise.models.Repo

class RepoListAdapter(private val mList: List<Repo>):
RecyclerView.Adapter<RepoListAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvRepoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val tvTopContributor: TextView = itemView.findViewById(R.id.tvTopContributorName)
        val tvContributionsNumber: TextView = itemView.findViewById(R.id.tvContributionsNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.repo_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.tvRepoName.text = "$position. ${item.name}"
        holder.tvTopContributor.text  = item.topContributor?.login ?: "Not Available"
        holder.tvContributionsNumber.text = item.topContributor?.contributions.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}