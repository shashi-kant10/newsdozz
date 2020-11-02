package com.shashi.dailyshot

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.shashi.dailyshot.data.NewsDataModel
import com.shashi.dailyshot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var newsUrl: String

    private var buttons = ArrayList<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar: Toolbar = binding.toolbarMainActivity
        setSupportActionBar(toolBar)

        initViews()
    }

    private fun initViews() {

        binding.recyclerViewMainActivity.layoutManager = LinearLayoutManager(this)

        newsUrl =
            "https://gnews.io/api/v4/top-headlines?lang=en&country=in&topic=breaking-news&token=ef098601144aaa99d14f8cd6d85eb7d8"
        fetchData()
        newsAdapter = NewsAdapter(this)

        binding.recyclerViewMainActivity.adapter = newsAdapter

        buttons.add(binding.buttonHeadlines)
        buttons.add(binding.buttonBusiness)
        buttons.add(binding.buttonTechnology)
        buttons.add(binding.buttonSports)
        buttons.add(binding.buttonScience)
        buttons.add(binding.buttonHealth)

        for (i in buttons)
            i.setOnClickListener(this)

    }

    private fun fetchData() {

        binding.progressBarRecyclerView.visibility = View.VISIBLE

        val url = newsUrl

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

    override fun onClick(button: View?) {

        var position = 0

        when (button?.id) {
            R.id.button_headlines -> position = 0
            R.id.button_business -> position = 1
            R.id.button_technology -> position = 2
            R.id.button_sports -> position = 3
            R.id.button_science -> position = 4
            R.id.button_health -> position = 5
        }

        changeColor(position)
    }

    private fun changeColor(position: Int) {

        buttons[position].setBackgroundResource(R.drawable.design_button_category_selected)
        buttons[position].setTextColor(Color.parseColor("#000000"))

        val newsCategory = buttons[position].text

        newsUrl =
            "https://gnews.io/api/v4/top-headlines?lang=en&country=in&topic=$newsCategory&token=ef098601144aaa99d14f8cd6d85eb7d8"

        for (i in 0 until buttons.size) {
            if (i == position)
                continue

            buttons[i].setBackgroundResource(R.drawable.design_button_category)
            buttons[i].setTextColor(Color.parseColor("#DAE0E2"))
        }

        fetchData()
    }

}

