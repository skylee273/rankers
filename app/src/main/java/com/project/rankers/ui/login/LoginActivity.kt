package com.project.rankers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityLoginBinding
import com.project.rankers.kakao.KakaoSignUpActivity
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.main.MainActivity
import com.project.rankers.ui.register.RegisterActivity
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null

    private var mLoginViewModel: LoginViewModel? = null
    private lateinit var loginBinding: ActivityLoginBinding

    // naver login value
    private var OAUTH_CLIENT_ID: String? = "UjyxaIsZ3i3GlROD6XPa"
    private var OAUTH_CLIENT_SECRET: String? = "THUoRkeses"
    private var OAUTH_CLIENT_NAME: String? = "shrinehaneal@naver.com"
    private var mOAuthLoginModule: OAuthLogin? = null


    fun newIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    override fun handleError(throwable: Throwable) {
        displayLog("Login", throwable.message!!)
    }


    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity(email: String) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra("userEmail", email)
        startActivity(intent)
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        return mLoginViewModel as LoginViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    private var sessionCallback: SessionCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = getViewDataBinding()
        mLoginViewModel!!.navigator = this

        //getHashKey()

        // naver login
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule!!.init(this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME)


        loginBinding.kakaoButton.setOnClickListener {

            sessionCallback = SessionCallback()
            // Session 에 콜백 추가
            Session.getCurrentSession().addCallback(sessionCallback)
            Session.getCurrentSession().checkAndImplicitOpen()
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
        }

        loginBinding.buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonOAuthLoginImg.setBgResourceId(R.drawable.btn_login_naver)



        loginBinding.buttonOAuthLoginImg.setOnClickListener {
            if (mOAuthLoginModule != null) {
                mOAuthLoginModule!!.startOauthLoginActivity(mContext as LoginActivity, mOAuthLoginHandler)
            }
        }

    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun getHashKey() {
        try {                                                        // 패키지이름을 입력해줍니다.
            val info = packageManager.getPackageInfo("com.project.rankers", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("LoginActivity", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3000)
                if (data != null) {
                    mLoginViewModel!!.isUser(data.getStringExtra("email"), data.getStringExtra("nickName"))
                    //findUser(data.getStringExtra("email"), data.getStringExtra("nickName"))
                }
        }
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun redirectSignupActivity() {
        val intent = Intent(this, KakaoSignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivityForResult(intent, 3000)
    }

    private inner class SessionCallback : ISessionCallback {

        /**
         * Access Token을 성공적으로 발급 받아 Valid Access token 을 가지고 있는 상태, 일반적으로
         * 로그인 후 다음 Activity 로 이동
         */
        override fun onSessionOpened() {
            redirectSignupActivity()
        }

        // 카카오 로그인에 실패한 경우
        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.d("LoginActivity", exception.toString())
            }
            //setContentView(R.layout.activity_login)
        }
    }

    private val mOAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken = mOAuthLoginModule!!.getAccessToken(mContext)
                val refreshToken = mOAuthLoginModule!!.getRefreshToken(mContext)
                val expiresAt = mOAuthLoginModule!!.getExpiresAt(mContext)
                val tokenType = mOAuthLoginModule!!.getTokenType(mContext)
                RequestApiTask().execute()
                displayLog("Naver Login", accessToken + refreshToken + expiresAt + tokenType)
            } else {
                val errorCode = mOAuthLoginModule!!.getLastErrorCode(mContext).code
                val errorDesc = mOAuthLoginModule!!.getLastErrorDesc(mContext)
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Naver Login
    private inner class RequestApiTask : AsyncTask<Void, Void, String>() {
        override fun onPreExecute() {

        }

        override fun doInBackground(vararg params: Void): String {
            val url = "https://openapi.naver.com/v1/nid/me"
            val at = mOAuthLoginModule!!.getAccessToken(mContext)
            return mOAuthLoginModule!!.requestApi(mContext, at, url)
        }

        override fun onPostExecute(content: String) {
            val jsonObject = JSONObject(content).getJSONObject("response")

            val name = jsonObject.getString("name")
            val email = jsonObject.getString("email")
            mLoginViewModel!!.isUser(email, name)
        }
        //mApiResultText.setText(content)
    }

}


