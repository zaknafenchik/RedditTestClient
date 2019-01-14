package com.example.reddittestclient.feature.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.reddittestclient.R
import com.example.reddittestclient.data.pojo.Item
import com.example.reddittestclient.data.pojo.Resource
import com.example.reddittestclient.data.pojo.State
import com.example.reddittestclient.databinding.ActivityMainBinding
import com.example.reddittestclient.di.Injectable
import com.example.reddittestclient.feature.imageviewer.ImageViewerActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private var adapter: ItemsAdapter = ItemsAdapter {
        val intent = Intent(this, ImageViewerActivity::class.java)
        intent.putExtra(ImageViewerActivity.EXTRA_IMAGE_URL, it)
        startActivity(intent)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        initRecyclerView()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.data?.observe(this,
            Observer<Resource<List<Item>>> { t ->
                if (t?.state == State.LOADING) {
                    binding.loading = true
                } else if (t?.state == State.SUCCESS) {
                    adapter.submitList(t.data)
                    adapter.notifyDataSetChanged()
                    binding.loading = false
                } else {
                    Toast.makeText(this, t?.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.loading = false
                }
            })
    }
}
