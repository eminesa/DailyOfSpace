package com.eminesa.photoofspace.presenters.dailyPhoto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.databinding.LayoutBottomSheetDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun initBottomSheet(
    onClicked: (clicked: Boolean) -> Unit,
    title: String?,
    desc: String?,
    context: Context,
) {
    val bottomSheetDialog = BottomSheetDialog(context, R.style.Settings_BottomSheetDialog)
    val bottomSheetBinding = LayoutBottomSheetDetailBinding.inflate(LayoutInflater.from(context))
    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.window?.setBackgroundDrawableResource(R.color.transparent)
    bottomSheetDialog.setCancelable(false)

    bottomSheetDialog.behavior.apply {
        isFitToContents = true
        state = BottomSheetBehavior.STATE_COLLAPSED
        isHideable = true
    }

    bottomSheetBinding.apply {
        titleTextView.text = title
        descriptionTextView.text = desc
    }

    bottomSheetDialog.behavior.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                onClicked(true)
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            // İhtiyacınıza göre burada da bir işlem yapabilirsiniz
        }
    })

    bottomSheetDialog.show()
}