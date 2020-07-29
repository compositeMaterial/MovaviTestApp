package com.killzone.movavitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.killzone.movavitest.adapters.HabrPostsListAdapter
import com.killzone.movavitest.adapters.HabrPostsListListener
import com.killzone.movavitest.databinding.FragmentHabrPostsListBinding
import com.killzone.movavitest.viewmodels.HabrViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabrPostsListFragment : Fragment() {

    private lateinit var binding: FragmentHabrPostsListBinding
    private lateinit var adapter: HabrPostsListAdapter
    private val viewModel: HabrViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabrPostsListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        adapter = HabrPostsListAdapter(HabrPostsListListener { viewModel.onPostClicked(it) })
        binding.rvHabrPostsList.adapter = adapter

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {

        // Observing Habr posts in ViewModel
        viewModel.habrPostsList.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.submitList(it.posts) }
        })

        // Navigate to Detail Fragment
        viewModel.navigateToDetailFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it && viewModel.navigationInfo != null) {
                val post = viewModel.navigationInfo
                this.findNavController().navigate(MainFragmentDirections.toDetailFragmentt(post!!))
                viewModel.resetNavigateEventValue()
            }
        })

        // First load actions
        viewModel.loadingValue.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.rvHabrPostsList.visibility = View.INVISIBLE
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.rvHabrPostsList.visibility = View.VISIBLE
            }
        })

        // Error Toast
        viewModel.errorValue.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })

        // Refreshing data actions
        viewModel.refreshingValue.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.swipeRefreshLayout.isRefreshing = true
                binding.swipeRefreshLayout.isActivated = true
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.swipeRefreshLayout.isActivated = false
            }
        })

        // When user swipes up action
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.load()
        }
    }


}