package com.eminesa.dailyofspace.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.util.CoilUtils
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.LayoutItemPhotoBinding
import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.presenters.dailyPhoto.initBottomSheet
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers

class PhotoAdapter : ListAdapter<DailyImage, PhotoAdapter.PhotoAdapterViewHolder>(
    PhotoAdapterDiffUtil
) {

    inner class PhotoAdapterViewHolder(var itemBinding: LayoutItemPhotoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userItem: DailyImage) {

            initButtonText(userItem)
        }
    }

    private fun PhotoAdapterViewHolder.initButtonText(
        imageItem: DailyImage
    ) {
        itemBinding.apply {

            initBottomSheet(
                imageItem.title,
                imageItem.explanation,
                itemView.context
                )

            if (imageItem.url == "video") {
                //https://www.youtube.com/embed/VYWjxvm14Pk?rel=0
                imgSpace.isVisible = false
                imgContentOfVideo.isVisible = true
                constraintInfo.isVisible = false

                //url?.let { initUI(it) }
                //   val youtubePlayerInit = url?.let { initUI(it) }
                //   binding?.youtubePlayer?.initialize(youtubeApiKey, youtubePlayerInit)
            } else {

                imgSpace.isVisible = true
                imgContentOfVideo.isVisible = false

                imgSpace.load(imageItem.url) {
                    allowRgb565(true)
                    placeholderMemoryCacheKey(CoilUtils.metadata(imgSpace)?.memoryCacheKey)
                    dispatcher(Dispatchers.IO)
                    placeholder(R.drawable.ic_astronaut)
                    error(R.drawable.ic_astronaut)
                }
            }
        }
    }

    companion object PhotoAdapterDiffUtil :
        DiffUtil.ItemCallback<DailyImage>() {
        override fun areItemsTheSame(
            oldItem: DailyImage,
            newItem: DailyImage,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DailyImage,
            newItem: DailyImage,
        ): Boolean {
            return oldItem.mediaType == newItem.mediaType
        }
    }

    override fun onBindViewHolder(holder: PhotoAdapterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoAdapterViewHolder {
        return PhotoAdapterViewHolder(
            LayoutItemPhotoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}