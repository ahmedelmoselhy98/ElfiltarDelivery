package com.elmoselhy.elfiltardelivery.business.general.dialog

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.commons.helpers.CameraGalleryHelper
import com.elmoselhy.elfiltardelivery.databinding.DialogLayoutCameraGalleryBinding

class CameraGalleryDialog(
    val mContext: Context
) : BaseDialog(mContext) {
    var binding: DialogLayoutCameraGalleryBinding =
        DialogLayoutCameraGalleryBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
        setUpDialogUi()
        setUpPageAction()
    }

    private fun setUpPageAction() {
        binding.cameraLy.setOnClickListener {
            CameraGalleryHelper.openCamera(mContext)
            dismiss()
        }
        binding.galleryLy.setOnClickListener {
            CameraGalleryHelper.openGallery(mContext)
            dismiss()
        }
    }
}