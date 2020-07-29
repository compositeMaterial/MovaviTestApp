package com.killzone.movavitest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.killzone.movavitest.R
import com.killzone.movavitest.databinding.RecyclerViewFooterBinding
import androidx.paging.LoadStateAdapter

class LoadStateAdapter : LoadStateAdapter<PokemonLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PokemonLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokemonLoadStateViewHolder {
        return PokemonLoadStateViewHolder.create(parent)
    }
}


class PokemonLoadStateViewHolder(
    private val binding: RecyclerViewFooterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Loading || loadState is LoadState.Error) {
            binding.pbLoadState.isVisible = loadState is LoadState.Loading
        }
    }

    companion object {
        fun create(parent: ViewGroup) : PokemonLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_footer, parent, false)
            val binding = RecyclerViewFooterBinding.bind(view)
            return PokemonLoadStateViewHolder(binding)
        }
    }
}