package com.eminesa.dailyofspace.presenters.dailyPhoto

import android.content.Context
import android.view.LayoutInflater
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.LayoutBottomSheetDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView

fun initBottomSheet(
     title: String?,
     desc: String?,
     context: Context,
     onShowMoreClickListener: ((textView: MaterialTextView) -> Unit),
) {
    val bottomSheetDialog = BottomSheetDialog(context, R.style.Settings_BottomSheetDialog)
    val bottomSheetBinding =
        LayoutBottomSheetDetailBinding.inflate(LayoutInflater.from(context))
    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.window?.setBackgroundDrawableResource(R.color.transparent)

    bottomSheetBinding.apply {
        titleTextView.text = title
        descriptionTextView.text = desc

        bottomSheetConstLayout.setOnClickListener {
            onShowMoreClickListener(descriptionTextView)
        }
    }

    bottomSheetDialog.show()
}