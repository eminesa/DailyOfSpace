package com.eminesa.dailyofspace.presenters.dailyPhoto

import android.content.Context
import android.view.LayoutInflater
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
    bottomSheetDialog.setCancelable(false) // Ekrana tıklanıldığında bottom sheet'in kapanmasını önler

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

    bottomSheetDialog.show()
}