package com.shashi.dailyshot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(private val items: ArrayList<String>, private val listner: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listner.onNewsItemClicked(items.get(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.titleTextView.text = items.get(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.text_view_title_row)
}

interface NewsItemClicked {
    fun onNewsItemClicked(item: String)
}