package com.project.rankers.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Handler
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.project.rankers.base.BaseViewModel
import com.project.rankers.ui.login.LoginActivity
import java.util.ArrayList

class SplashViewModel : BaseViewModel<SplashNavigator>() {

    private var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            navigator.openLoginActivity()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {

        }
    }

    fun checkPermission(context: Context){
        TedPermission.with(context)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("[설정] -> [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }

}