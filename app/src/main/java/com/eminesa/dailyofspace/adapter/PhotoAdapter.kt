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
import com.eminesa.dailyofspace.clouddb.ObjPhoto
import com.eminesa.dailyofspace.databinding.LayoutItemPhotoBinding
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers

/**
 * Bu class engellenmiş ve sessize alınmış listesinin her bir elemanını düzenlenmesini sağlar.
 * @param onShowMoreClickListener  cloud icindeki data bilgilerinin kullaniciya ait olanini getirir getirir
 */

class PhotoAdapter(
    val onShowMoreClickListener: ((textView: MaterialTextView, item: ObjPhoto) -> Unit)?,
    val itemClickListener: ((view: View, item: ObjPhoto) -> Unit)?,
    val translateListener: ((titleTextView: MaterialTextView, decTextView: MaterialTextView,  item: ObjPhoto) -> Unit)?,
) : ListAdapter<ObjPhoto, PhotoAdapter.PhotoAdapterViewHolder>(
    PhotoAdapterDiffUtil
) {

    inner class PhotoAdapterViewHolder(var itemBinding: LayoutItemPhotoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userItem: ObjPhoto) {

            initButtonText(userItem)
        }
    }

    private fun PhotoAdapterViewHolder.initButtonText(
        userItem: ObjPhoto
    ) {
        itemBinding.txtDescription.setOnClickListener {
            onShowMoreClickListener?.let { onOpenListener ->
                onOpenListener(
                    itemBinding.txtDescription,
                    userItem
                )
            }
        }
        itemBinding.txtTranslate.setOnClickListener {
            translateListener?.let { it1 -> it1(itemBinding.txtTitle, itemBinding.txtDescription, userItem) }
        }
        itemBinding.apply {
            txtTitle.text = userItem.photoTitle
            txtDescription.text = userItem.photoDesc

            if (userItem.urlType == "video") {
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

                imgSpace.load(userItem.photoUrl) {
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
        DiffUtil.ItemCallback<ObjPhoto>() {
        override fun areItemsTheSame(
            oldItem: ObjPhoto,
            newItem: ObjPhoto,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ObjPhoto,
            newItem: ObjPhoto,
        ): Boolean {
            return oldItem.userId == newItem.userId
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