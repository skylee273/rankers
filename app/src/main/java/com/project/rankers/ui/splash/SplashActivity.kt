package com.project.rankers.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.project.rankers.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.base.BaseActivity
import com.project.rankers.databinding.ActivitySplashBinding
import com.project.rankers.ui.login.LoginActivity
import com.project.rankers.ui.main.MainViewModel
import java.util.ArrayList
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var splashBinding: ActivitySplashBinding
    private var mSplashViewModel: SplashViewModel? = null

    var introHandler: Handler? = Handler()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    var introRunnable: Runnable? = Runnable {
        mSplashViewModel!!.checkPermission(this)
    }

    // 에러 함수
    override fun handleError(throwable: Throwable) {

    }

    // 로그인 화면 이동
    override fun openLoginActivity() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // 현재 레이아웃
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    // 데이터 바인딩
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    // 뷰모델
    override fun getViewModel(): SplashViewModel {
        mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        return mSplashViewModel as SplashViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = getViewDataBinding()
        mSplashViewModel!!.navigator = this

        val imageView = findViewById<ImageView>(R.id.iv_intro)
        Glide.with(this).load(R.drawable.image_intro).into(imageView)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onResume() {
        super.onResume()
        if (introHandler != null) {
            introHandler!!.postDelayed(introRunnable, 2000)
        }
    }

    override fun onPause() {
        super.onPause()
        introHandler!!.removeCallbacks(introRunnable)
    }
}