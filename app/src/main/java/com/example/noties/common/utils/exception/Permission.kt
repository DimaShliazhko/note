package com.example.noties.common.utils.exception

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


fun Context.hasReadPermission(): Boolean {
    return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED)

}

fun Context.hasWritePermission(): Boolean {
    val minSDK = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) || minSDK
}

fun Context.hasCameraPermission(): Boolean {
    return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED)
}

fun Context.checkPermissions(): List<String> {
    val permissionToRequest = mutableListOf<String>()
    if (!this.hasReadPermission()) {
        permissionToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (!this.hasWritePermission()) {
        permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    if (!this.hasCameraPermission()) {
        permissionToRequest.add(Manifest.permission.CAMERA)
    }
    return permissionToRequest.ifEmpty { emptyList() }
}