package com.moli.module.widget.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat

interface PermissionCheck{

    fun setPermissionCallBack(mPermissionCallBack: PermissionCallBack)

    //检查单个权限，可以用来区分权限
    fun checkPermission(activity: Activity, permission: String, requestCode: Int, permissionCallBack: PermissionCallBack?)

    //检查权限
    fun checkPermissions(activity: Activity, permissions: Array<String>)

    //在在Activity中的onRequestPermissionsResult中添加
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)

    // 启动应用的设置
    fun startAppSettings(act:Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${act.packageName}")
        act.startActivity(intent)
    }

    // 缺少权限时, 进入权限配置页面
    fun startPermissionsActivity(activity: Activity, permissionsActivityClazz: Class<out Activity>, requestCode: Int, permissions: Array<String>) {
        val intent = Intent(activity, permissionsActivityClazz)
        intent.putExtra("permission.extra_permission", permissions)
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null)
    }


}
