package com.elmoselhy.elfiltardelivery.commons.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

class CheckPermissionsHelper {
    companion object {

        // permissions request code
        const val CAMERA = 1203
        const val LOCATION = 1134

        /************************* Camera ************************/
        fun isCameraPermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.CAMERA),
                    CAMERA
                )
                return false
            }
            return true
        }
        fun handleCameraPermissionResult(
            context: Context,
            permissions: Array<String>,
            grantResults: IntArray, onPermissionGranted: () -> Unit
        ) {
            if (permissions.get(0) == Manifest.permission.CAMERA && grantResults.get(0) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(
                context as Activity, arrayOf(
                    Manifest.permission.CAMERA
                ),
                CAMERA
            ) else onPermissionGranted()
        }
        /************************* Location ************************/
        fun isLocationPermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        LOCATION
                    )
                    return false
                }
            return true
        }
        fun handleLocationPermissionResult(
            context: Context,
            permissions: Array<out String>,
            grantResults: IntArray, onPermissionGranted: () -> Unit
        ) {
            if (permissions.get(0) == Manifest.permission.ACCESS_FINE_LOCATION && grantResults.get(0) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    LOCATION
                ) else onPermissionGranted()
        }


    }
}