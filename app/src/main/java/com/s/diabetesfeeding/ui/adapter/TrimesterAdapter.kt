package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.TrimesterData
import kotlinx.android.synthetic.main.item_trimester.view.*

class TrimesterAdapter(val trimesterTopics : List<TrimesterData>) : RecyclerView.Adapter<TrimesterAdapter.TrimesterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrimesterViewHolder {
        return TrimesterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trimester, parent, false)
        )
    }

    override fun getItemCount()= trimesterTopics.size


    override fun onBindViewHolder(holder: TrimesterViewHolder, position: Int) {
        val topics = trimesterTopics[position]
        holder.view.tv_topic_title.text = topics.title
    }

    class TrimesterViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}