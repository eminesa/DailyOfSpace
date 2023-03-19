package com.eminesa.dailyofspace.adapter


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
import com.eminesa.dailyofspace.model.NasaByIdResponse
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers

/**
 * Bu class engellenmiş ve sessize alınmış listesinin her bir elemanını düzenlenmesini sağlar.
 * @param onShowMoreClickListener  cloud icindeki data bilgilerinin kullaniciya ait olanini getirir getirir
 */

class PhotoAdapter(
    val onShowMoreClickListener: ((textView: MaterialTextView, item: NasaByIdResponse) -> Unit)?,
    val itemClickListener: ((view: View, item: NasaByIdResponse) -> Unit)?,
    val translateListener: ((titleTextView: MaterialTextView, decTextView: MaterialTextView,  item: NasaByIdResponse) -> Unit)?,
) : ListAdapter<NasaByIdResponse, PhotoAdapter.PhotoAdapterViewHolder>(
    PhotoAdapterDiffUtil
) {

    inner class PhotoAdapterViewHolder(var itemBinding: LayoutItemPhotoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userItem: NasaByIdResponse) {

            initButtonText(userItem)
        }
    }

    private fun PhotoAdapterViewHolder.initButtonText(
        imageItem: NasaByIdResponse
    ) {
        itemBinding.txtDescription.setOnClickListener {
            onShowMoreClickListener?.let { onOpenListener ->
                onOpenListener(
                    itemBinding.txtDescription,
                    imageItem
                )
            }
        }

        itemBinding.apply {
            txtTitle.text = imageItem.title
            txtDescription.text = imageItem.explanation

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
        DiffUtil.ItemCallback<NasaByIdResponse>() {
        override fun areItemsTheSame(
            oldItem: NasaByIdResponse,
            newItem: NasaByIdResponse,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: NasaByIdResponse,
            newItem: NasaByIdResponse,
        ): Boolean {
            return oldItem.media_type == newItem.media_type
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