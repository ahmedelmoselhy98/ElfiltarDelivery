package com.elfiltar.elfiltartechnician.commons.helpers

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

//allprojects {
//    repositories {
//        ...
//        maven { url 'https://jitpack.io' }
//    }
//}
// imagePicker
//    implementation 'com.github.dhaval2404:imagepicker:2.1'
class CameraGalleryHelper {

    companion object {
        fun openCamera(context: Context) {
            ImagePicker.with(context as Activity)
                .cameraOnly()
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        fun openGallery(context: Context) {
            ImagePicker.with(context as Activity)
                .galleryOnly()
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        fun  getImageSelectionMultipart(
            context: Context,
            uri: Uri,
            fileName: String,
        ): MultipartBody.Part {
            var selectedFilePath = getPath(context, uri)
            val file = File(selectedFilePath)
            return MultipartBody.Part.createFormData(
                fileName,
                file.name,
                RequestBody.create("image*//*".toMediaTypeOrNull(), file)
            )
        }

        fun getPath(context: Context?, uri: Uri): String? {
            //check here to KITKAT or new version
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (ImageUtil.isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (ImageUtil.isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id))
                    return ImageUtil.getDataColumn(context, contentUri, null, null)
                } else if (ImageUtil.isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                        split[1]
                    )
                    return ImageUtil.getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                // Return the remote address
                return if (ImageUtil.isGooglePhotosUri(uri)) uri.lastPathSegment else ImageUtil.getDataColumn(
                    context,
                    uri,
                    null,
                    null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }


    }

}