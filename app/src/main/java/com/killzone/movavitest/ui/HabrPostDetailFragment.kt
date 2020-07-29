package com.killzone.movavitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.killzone.movavitest.R
import com.killzone.movavitest.databinding.FragmentHabrPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


@AndroidEntryPoint
class HabrPostDetailFragment : Fragment() {

    private val navArgs: HabrPostDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentHabrPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabrPostDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.tvTitle.text = navArgs.habrPost.title
        binding.tvDescription.text = navArgs.habrPost.description

        val url = navArgs.habrPost.imageUrl

        if (url.isNullOrEmpty()) {
            binding.ivPostImage.visibility = View.GONE
        } else {
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivPostImage)
        }

        binding.floatingActionButton.setOnClickListener {
            this.findNavController().navigateUp()
        }



        return binding.root
    }


}