package com.eminesa.photoofspace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.util.CoilUtils
import com.eminesa.photoofspace.databinding.LayoutItemIntroBinding
import com.eminesa.photoofspace.model.IntroModel
import kotlinx.coroutines.Dispatchers

class IntroAdapter : ListAdapter<IntroModel, IntroAdapter.IntroViewHolder>(IntroDiffUtil) {
    companion object IntroDiffUtil : DiffUtil.ItemCallback<IntroModel>() {
        override fun areItemsTheSame(
            oldItem: IntroModel,
            newItem: IntroModel,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IntroModel,
            newItem: IntroModel,
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroViewHolder {

        return IntroViewHolder(LayoutItemIntroBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class IntroViewHolder(private var itemBinding: LayoutItemIntroBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            introPageData: IntroModel,
        ) {
            itemBinding.txtTitle.text = introPageData.title
            itemBinding.txtDescription.text = introPageData.description
            itemBinding.imgListItem.load(introPageData.image) {
                allowRgb565(true)
                placeholderMemoryCacheKey(CoilUtils.metadata(itemBinding.imgListItem)?.memoryCacheKey)
                dispatcher(Dispatchers.IO)
            }

        }
    }
}