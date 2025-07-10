package com.eminesa.photoofspace.extentions

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.photoofspace.enum.Behavior
import com.eminesa.photoofspace.presenters.intro.OnSnapPositionChangeListener
import com.eminesa.photoofspace.presenters.intro.SnapOnScrollListener


/**
 * Bu extension recyclerView için o anki position bilgisini döner.
 * @param recyclerView position bilgisini döneceğimiz recyclerView
 * @return position bilgisi
 */
fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}


/**
 * Bu extension recyclerView'e scrollListener eklemek içindir.
 * @param snapHelper recyclrView'e eklenir ve SnapOnScrollListener'in bir parametresidir.
 * @param behavior SnapOnScrollListener'in bir parametresidir.
 * @param onSnapPositionChangeListener SnapOnScrollListener'in bir parametresidir ve position
 * değerinin observe etmek içindir.
 */
fun RecyclerView.attachSnapHelperWithListener(
    snapHelper: SnapHelper,
    behavior: Behavior = Behavior.NOTIFY_ON_SCROLL,
    onSnapPositionChangeListener: OnSnapPositionChangeListener,
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener = SnapOnScrollListener(
        snapHelper,
        onSnapPositionChangeListener,
        behavior
    )
    addOnScrollListener(snapOnScrollListener)
}