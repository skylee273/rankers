package com.project.rankers.views.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.project.rankers.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.ArrayList

class IntroActivity : AppCompatActivity() {
    var introHandler : Handler? = Handler()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    var introRunnable : Runnable? = Runnable {
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("[설정] -> [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
    }

    internal var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val imageView = findViewById<ImageView>(R.id.iv_intro)
        Glide.with(this).load(R.drawable.image_intro).into(imageView)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onResume() {
        super.onResume()
        if(introHandler != null){
            introHandler!!.postDelayed(introRunnable, 2000)
        }
    }

    override fun onPause(){
        super.onPause()
        introHandler!!.removeCallbacks(introRunnable)
    }
}