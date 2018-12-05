package com.moli.module.widget.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

class PermissionCheckerImpl(private val act: Activity) : PermissionCheck {
    val PERMISSION_REQUEST_CODE = 0 // 系统权限管理页面的参数
    private var mPermissionCallBack: PermissionCallBack? = null
    private var builder: AlertDialog.Builder? = getPermissionDialog()

    override fun setPermissionCallBack(mPermissionCallBack: PermissionCallBack) {
        this.mPermissionCallBack = mPermissionCallBack
    }


    //检查权限
    override fun checkPermissions(activity: Activity, permissions: Array<String>) {
        if (lacksPermissions(*permissions)) {
            // 请求权限兼容低版本
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
        } else {
            // 全部权限都已获取
            if (null != mPermissionCallBack) {
                mPermissionCallBack!!.granted()
            }
        }
    }

    //检查单个权限，可以用来区分权限
    override fun checkPermission(activity: Activity, permission: String, requestCode: Int, permissionCallBack: PermissionCallBack?) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showMissingPermissionDialog()
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        } else {
            permissionCallBack?.granted()
        }
    }

    //在在Activity中的onRequestPermissionsResult中添加
    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCheckPermissions(requestCode, grantResults)) {
            // 全部权限都已获取
            if (null != mPermissionCallBack) {
                mPermissionCallBack!!.granted()
            }
        } else {
            showMissingPermissionDialog()
        }
    }


    private fun getPermissionDialog(): AlertDialog.Builder {
        val builder = AlertDialog.Builder(act)
        builder.setTitle("帮助")
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。")

        // 拒绝, 退出应用
        builder.setNegativeButton("退出") { _, _ ->
            if (null != mPermissionCallBack) {
                mPermissionCallBack!!.denied()
            }
        }

        builder.setPositiveButton("设置") { _, _ -> startAppSettings(act) }

        builder.setCancelable(false)
        return builder
    }


    // 显示缺失权限提示
    private fun showMissingPermissionDialog() {
        builder?.show()
    }

    // 判断是否缺少权限
    private fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_DENIED
    }

    // 判断权限集合
    private fun lacksPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (lacksPermission(permission)) {
                return true
            }
        }
        return false
    }


    //在Activity中的onRequestPermissionsResult 中检测是否同意了所有权限
    private fun requestCheckPermissions(requestCode: Int, grantResults: IntArray): Boolean {
        return requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)
    }


    // 含有全部的权限
    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    companion object {
        const val WRITE_EXTERNAL_STORAGE_CODE = 1 //写权限请求码
        /**
         * 检查权限
         *
         * @param permission
         * @param resultCode
         */
        fun checkSelfPermission(act: Activity, permission: String, resultCode: Int): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(act, arrayOf(permission), resultCode)
                    false
                } else {
                    true
                }
            }
            return true
        }
    }


}
