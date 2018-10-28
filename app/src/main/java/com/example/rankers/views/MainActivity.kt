package com.example.rankers.views

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.security.MessageDigest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import com.example.rankers.R
import com.example.rankers.databinding.ActivityMainBinding
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.NoSuchAlgorithmException
import com.roughike.bottombar.TabSelectionInterceptor
import com.example.rankers.R.id.bottomBar
import com.roughike.bottombar.OnTabReselectListener
import com.example.rankers.R.id.bottomBar


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    var mContext : Context? = null;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Log.d("MAIN", getKeyHash(mContext as MainActivity))
        mainBinding.bottomBar.setOnTabReselectListener { tabId ->
            if (tabId == R.id.tab_favorites) {
                Log.d("MAIN", "잘동작함")
            }
        }
        mainBinding.btnRegister.setOnClickListener {
            startActivity(Intent(this, ContestRegisterActivity::class.java))
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getKeyHash(context: Context) : String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null
        for (signature in packageInfo.signatures){
            try {
                val md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray())
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP)
            }catch (e : NoSuchAlgorithmException){
                Log.d("TAG", e.toString())
            }
        }
        return null
    }
}


