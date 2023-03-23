package com.eminesa.dailyofspace.presenters.dailyPhoto

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.LayoutBottomSheetDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun initBottomSheet(
    title: String?,
    desc: String?,
    context: Context,
) {
    val bottomSheetDialog = BottomSheetDialog(context, R.style.Settings_BottomSheetDialog)
    val bottomSheetBinding = LayoutBottomSheetDetailBinding.inflate(LayoutInflater.from(context))
    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.window?.setBackgroundDrawableResource(R.color.transparent)

    bottomSheetDialog.behavior.apply {
        isFitToContents = true
        peekHeight = 300
        state = BottomSheetBehavior.STATE_COLLAPSED
        isHideable = false
    }

    bottomSheetBinding.apply {
        titleTextView.text = title
        descriptionTextView.text = desc
    }

    bottomSheetDialog.behavior.setBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        @SuppressLint("SwitchIntDef")
        override fun onStateChanged(view: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    // mMap.animateCamera(CameraUpdateFactory.zoomIn())
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    //  mMap.animateCamera(CameraUpdateFactory.zoomOut())
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                }
            }
        }

        override fun onSlide(view: View, v: Float) {
        }
    })

    bottomSheetDialog.show()
}