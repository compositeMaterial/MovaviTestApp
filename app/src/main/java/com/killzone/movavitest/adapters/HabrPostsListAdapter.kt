package com.killzone.movavitest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.killzone.movavitest.R
import com.killzone.movavitest.databinding.HabrPostItemBinding
import com.killzone.movavitest.model.ConvertedHabrPost
import com.killzone.movavitest.util.extractImage

class HabrPostsListAdapter(val clickListener: HabrPostsListListener)
    : ListAdapter<ConvertedHabrPost, HabrPostsListAdapter.ViewHolder>(HabrPostsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.bind(getItem(position), position)
        holder.bind(clickListener, getItem(position)!!)
    }

    class ViewHolder private constructor(
        val binding: HabrPostItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: HabrPostsListListener, item: ConvertedHabrPost) {

            binding.ivTitle.text = item.title
            binding.tvCreator.text = item.creator
            binding.tvDate.text = item.pubDate

            binding.post = item
            binding.clickListener = listener

            if (!item.imageUrl.isNullOrEmpty()) {
                binding.ivPostImage.visibility = View.VISIBLE
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(item.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.ivPostImage)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HabrPostItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HabrPostsListListener(val clickListener: (p: ConvertedHabrPost) -> Unit) {
    fun onClick(p: ConvertedHabrPost) = clickListener(p)
}

class HabrPostsDiffUtil : DiffUtil.ItemCallback<ConvertedHabrPost>() {
    override fun areItemsTheSame(oldItem: ConvertedHabrPost, newItem: ConvertedHabrPost): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(oldItem: ConvertedHabrPost, newItem: ConvertedHabrPost): Boolean {
        return oldItem == newItem
    }
}