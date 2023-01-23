package com.elmoselhy.elfiltardelivery.business.general.dialog

import android.content.Context
import android.view.LayoutInflater
import com.elmoselhy.elfiltardelivery.commons.helpers.CameraGalleryHelper
import com.elmoselhy.elfiltardelivery.databinding.DialogLayoutCameraGalleryBinding

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
            CameraGalleryHelper.openCamera(mContext)
            click()
            dismiss()
        }
        binding.galleryLy.setOnClickListener {
            CameraGalleryHelper.openGallery(mContext)
            click()
            dismiss()
        }
        binding.closeIv.setOnClickListener {
            CameraGalleryHelper.openGallery(mContext)
            click()
            dismiss()
        }

    }
}