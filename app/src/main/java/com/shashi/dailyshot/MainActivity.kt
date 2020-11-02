package com.shashi.dailyshot

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.shashi.dailyshot.data.NewsDataModel
import com.shashi.dailyshot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar: Toolbar = binding.toolbarMainActivity
        setSupportActionBar(toolBar)

        initViews()
    }

    private fun initViews() {

        binding.progressBarRecyclerView.visibility = View.VISIBLE
        binding.recyclerViewMainActivity.layoutManager = LinearLayoutManager(this)
        fetchData()
        newsAdapter = NewsAdapter(this)

        binding.recyclerViewMainActivity.adapter = newsAdapter
    }

    private fun fetchData() {

        val url =
            "https://gnews.io/api/v4/top-headlines?lang=en&country=in&topic=breaking-news&token=ef098601144aaa99d14f8cd6d85eb7d8"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<NewsDataModel>()

                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)

                    val news = NewsDataModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("image")
                    )

                    newsArray.add(news)
                }

                newsAdapter.updateNewsList(newsArray)
                binding.progressBarRecyclerView.visibility = View.GONE

            },
            {
                Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
                binding.progressBarRecyclerView.visibility = View.GONE
            }
        )

        VolleySingletonClass.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onNewsItemClicked(item: NewsDataModel) {
        val url: String = item.url

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

}

