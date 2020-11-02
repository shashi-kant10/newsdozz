package com.shashi.dailyshot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashi.dailyshot.data.NewsDataModel

class NewsAdapter(private val listner: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<NewsDataModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listner.onNewsItemClicked(items.get(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articleTitle = items[position].title
        val articleDescription = items[position].description
        val articleImageUrl = items[position].imageUrl

        holder.titleTextView.text = articleTitle
        holder.descriptionTextView.text = articleDescription
        Glide.with(holder.itemView.context).load(articleImageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNewsList(updatedNews: ArrayList<NewsDataModel>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.image_view_row)
    val titleTextView: TextView = itemView.findViewById(R.id.text_view_title_row)
    val descriptionTextView: TextView = itemView.findViewById(R.id.text_view_description_row)
}

interface NewsItemClicked {
    fun onNewsItemClicked(item: NewsDataModel)
}