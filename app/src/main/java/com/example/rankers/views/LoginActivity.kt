package com.example.rankers.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.rankers.R
import com.example.rankers.databinding.ActivityLoginBinding
import com.example.rankers.kakao.KakaoSignUpActivity
import com.example.rankers.model.User
import com.kakao.auth.AuthType
import com.kakao.util.exception.KakaoException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding;
    private val TAG: String? = "LoginActivity"
    private var OAUTH_CLIENT_ID: String? = "aaQJOdhzRUarseiLP5A9"
    private var OAUTH_CLIENT_SECRET: String? = "MR0tI738Hz"
    private var OAUTH_CLIENT_NAME: String? = "shrinehaneal"
    var mOAuthLoginModule: OAuthLogin? = null
    lateinit var mContext: Context
    private var sessionCallback: SessionCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mContext = this
        loginBinding.kakaoButton.setOnClickListener {
            sessionCallback = SessionCallback()
            Session.getCurrentSession().addCallback(sessionCallback)
            Session.getCurrentSession().checkAndImplicitOpen()
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
        }
        loginBinding.buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonOAuthLoginImg.setOnClickListener {
            initNaverData()
            if (mOAuthLoginModule != null) {
                mOAuthLoginModule!!.startOauthLoginActivity(mContext as LoginActivity, mOAuthLoginHandler)
            }
        }

    }

    fun initNaverData() {
        mOAuthLoginModule = OAuthLogin.getInstance()
        if (mOAuthLoginModule != null) {
            mOAuthLoginModule!!.init(
                    this
                    , OAUTH_CLIENT_ID
                    , OAUTH_CLIENT_SECRET
                    , OAUTH_CLIENT_NAME
                    //,OAUTH_CALLBACK_INTENT
                    // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
            );
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(sessionCallback)

    }

    fun redirectSignupActivity() {
        val signUpIntent = Intent(this, KakaoSignUpActivity::class.java)
        signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(signUpIntent)
        finish()
    }

    private inner class SessionCallback : ISessionCallback {

        /**
         * Access Token을 성공적으로 발급 받아 Valid Access token 을 가지고 있는 상태, 일반적으로
         * 로그인 후 다음 ACctivity 로 이동
         */
        override fun onSessionOpened() {
            redirectSignupActivity()
        }

        // 카카오 로그인에 실패한 경우
        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.d(TAG, exception.toString())
            }
            setContentView(R.layout.activity_login)
        }
    }

    fun redirectMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val mOAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken = mOAuthLoginModule!!.getAccessToken(mContext)
                val refreshToken = mOAuthLoginModule!!.getRefreshToken(mContext)
                val expiresAt = mOAuthLoginModule!!.getExpiresAt(mContext)
                val tokenType = mOAuthLoginModule!!.getTokenType(mContext)
                val requstApi = RequestApiTask()
                requstApi.execute()
            } else {
                val errorCode = mOAuthLoginModule!!.getLastErrorCode(mContext).getCode()
                val errorDesc = mOAuthLoginModule!!.getLastErrorDesc(mContext)
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class RequestApiTask : AsyncTask<Void, Void, String>() {
        protected override fun onPreExecute() {//작업이 실행되기 전에 먼저 실행.

        }

        protected override fun doInBackground(vararg params: Void): String {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            val url = "https://openapi.naver.com/v1/nid/me"
            val accessToken = mOAuthLoginModule!!.getAccessToken(mContext)
            return mOAuthLoginModule!!.requestApi(mContext, accessToken, url)
        }

        protected override fun onPostExecute(content: String) =//doInBackground 에서 리턴된 값이 여기로 들어온다.
                try {
                    val jsonObject = JSONObject(content)
                    val response = jsonObject.getJSONObject("response")
                    val email = response.getString("email")
                    val name = response.getString("nickname")
                    val age = response.getString("age")
                    val gender = response.getString("gender")
                    val birthday = response.getString("birthday")
                    val id = response.getString("id");

                    val user: User? = null
                    if (user != null) {
                        user.id = id
                        user.name = name
                        user.gender = gender
                        user.age = age
                        user.birthday = birthday
                        user.seteMail(email)
                    }
                    redirectMainActivity();
                } catch (e: Exception) {
                    e.printStackTrace()
                }
    }

}
