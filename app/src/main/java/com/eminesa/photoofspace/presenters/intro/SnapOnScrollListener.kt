package com.eminesa.photoofspace.presenters.intro

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.photoofspace.enum.Behavior
import com.eminesa.photoofspace.extentions.getSnapPosition

/**
 * Bu class recyclerView scroll olduğunda ekranda en fazla örünen itemi ortalamayı sağlar.
 * @param snapHelper SnapHelper.
 * @param onSnapPositionChangeListener OnSnapPositionChangeListener listener.
 * @param behavior Enum tipi.
 */
class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    var onSnapPositionChangeListener: OnSnapPositionChangeListener? = null,
    var behavior: Behavior = Behavior.NOTIFY_ON_SCROLL

) : RecyclerView.OnScrollListener() {

    /**
     * Bu enum class'ı RecyclerView için state tiplerini içerir.
     */


    private var snapPosition = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (behavior == Behavior.NOTIFY_ON_SCROLL) {
            maybeNotifySnapPositionChange(recyclerView)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (behavior == Behavior.NOTIFY_ON_SCROLL_STATE_IDLE
            && newState == RecyclerView.SCROLL_STATE_IDLE) {
            maybeNotifySnapPositionChange(recyclerView)
        }
    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChangeListener?.onSnapPositionChange(snapPosition)
            this.snapPosition = snapPosition
        }
    }
}

interface OnSnapPositionChangeListener {
    fun onSnapPositionChange(position: Int)
}

