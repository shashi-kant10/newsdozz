package com.shashi.dailyshot

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shashi.dailyshot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {

    lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter

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

        val data = initData()
        newsAdapter = NewsAdapter(data, this)

        binding.recyclerViewMainActivity.adapter = newsAdapter
    }

    private fun initData(): ArrayList<String> {

        val list = ArrayList<String>()
        for (i in 1..100) {
            list.add("Item $i")
        }

        return list
    }

    override fun onNewsItemClicked(item: String) {
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
    }

}

