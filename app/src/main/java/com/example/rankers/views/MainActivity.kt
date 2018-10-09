package com.example.rankers.views

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.security.MessageDigest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.rankers.R
import com.example.rankers.databinding.ActivityMainBinding
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    var mContext : Context? = null;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mContext = this
        //Log.d("MAIN", getKeyHash(mContext as MainActivity))
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


