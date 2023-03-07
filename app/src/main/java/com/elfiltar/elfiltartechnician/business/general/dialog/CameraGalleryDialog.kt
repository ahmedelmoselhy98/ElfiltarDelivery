package com.elfiltar.elfiltartechnician.business.general.dialog

import android.content.Context
import android.view.LayoutInflater
import com.elfiltar.elfiltartechnician.commons.helpers.CameraGalleryHelper
import com.elfiltar.elfiltartechnician.databinding.DialogLayoutCameraGalleryBinding

class CameraGalleryDialog(
    val mContext: Context,
    val click: ()->Unit
) : BaseDialog(mContext) {
    var binding: DialogLayoutCameraGalleryBinding =
        DialogLayoutCameraGalleryBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
        setUpDialogUi()
        setUpPageAction()
        setCancelable(false)
    }

    private fun setUpPageAction() {
        binding.cameraLy.setOnClickListener {
            click()
            dismiss()
        }
        binding.galleryLy.setOnClickListener {
            click()
            dismiss()
        }
        binding.closeIv.setOnClickListener {
            click()
            dismiss()
        }

    }
}