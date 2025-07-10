package com.eminesa.photoofspace.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.util.CoilUtils
import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.databinding.LayoutItemPhotoBinding
import com.eminesa.photoofspace.model.DailyImage
import com.eminesa.photoofspace.presenters.VideoViewActivity
import com.eminesa.photoofspace.presenters.dailyPhoto.initBottomSheet
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

            //imageItem.mediaType = "video"
            //imageItem.url = "https://www.youtube.com/embed/VYWjxvm14Pk?rel=0"

            var isClicked = false
            txtTitle.text = imageItem.title
            txtDescription.text = imageItem.explanation

            txtDescription.setOnClickListener {
                if (!isClicked) {
                    setVisibility(false)
                    isClicked = true

                    initBottomSheet(
                        onClicked = {
                            setVisibility(true)
                            isClicked = false
                        },
                        imageItem.title,
                        imageItem.explanation,
                        itemView.context
                    )
                }
            }

            if (imageItem.mediaType == "video") {
                imgSpace.isVisible = false
                openVideoButton.isVisible = true
                setVisibility(true)
                openVideoButton.setOnClickListener {
                    val intent = Intent(itemView.context, VideoViewActivity::class.java)
                    intent.putExtra("daily_image", imageItem)
                    itemView.context.startActivity(intent)
                }

            } else {
                openVideoButton.isVisible = false
                imgSpace.isVisible = true

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

    private fun LayoutItemPhotoBinding.setVisibility(isVisible: Boolean) {
        txtTitle.isVisible = isVisible
        txtDescription.isVisible = isVisible
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