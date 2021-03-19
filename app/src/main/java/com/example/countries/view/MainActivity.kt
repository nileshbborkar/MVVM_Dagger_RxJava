package com.example.countries.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.R
import com.example.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    lateinit var countries_list: RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout
    private val countryListAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        swipeToRefresh = findViewById(R.id.swipeRefreshLayout)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
            viewModel.refresh()
        }
        countries_list = findViewById(R.id.countries_list)
        countries_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer {
            it.let {
                countries_list.visibility = View.VISIBLE
                countryListAdapter.updateCountries(it)
            }
        })

        var error_view: TextView = findViewById(R.id.list_error)

        viewModel.countryLoadError.observe(this, Observer {
            it?.let { error_view.visibility = if (it) View.VISIBLE else View.GONE }
        })

        var loading_view: ProgressBar = findViewById(R.id.loading_view)

        viewModel.loading.observe(this, Observer {
            it?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    error_view.visibility = View.GONE
                    countries_list.visibility = View.GONE
                }
            }
        })
    }
}