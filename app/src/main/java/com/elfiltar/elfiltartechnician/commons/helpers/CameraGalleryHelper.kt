package com.elfiltar.elfiltartechnician.commons.helpers

import android.content.ContentUris
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.net.toFile
import com.elfiltar.elfiltartechnician.R
import com.nguyenhoanglam.imagepicker.model.GridCount
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CameraGalleryHelper {

    companion object {
        fun openImagesPicker(context: Context, launcher: ImagePickerLauncher) {
            if (CheckPermissionsHelper.isCameraPermissionGranted(context)) {
                val config = ImagePickerConfig(
                    statusBarColor = "#000000",
                    isLightStatusBar = false,
                    isFolderMode = true,
                    isMultipleMode = false,
                    subDirectory = context.getString(R.string.app_name),
                    folderGridCount = GridCount(2, 4),
                    imageGridCount = GridCount(3, 5),
                    isShowCamera = true
                    // See more at configuration attributes table below
                )
                launcher.launch(config)
            }
        }

        fun getImageSelectionMultipart(
            context: Context,
            uri: Uri,
            fileName: String,
        ): MultipartBody.Part {
            var selectedFilePath = getImagePath(context, uri)
            val file = File(selectedFilePath)
            return MultipartBody.Part.createFormData(
                fileName,
                file.name,
                RequestBody.create("image*//*".toMediaTypeOrNull(), file)
            )
        }

        private fun getImagePath(
            context: Context,
            uri: Uri
        ): String? {
            val data = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, uri, data, null, null, null)
            val cursor: Cursor = loader.loadInBackground()
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
    }
}