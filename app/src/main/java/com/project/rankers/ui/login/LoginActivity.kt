package com.project.rankers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.OAuthLoginActivity
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


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    internal var factory: ViewModelProviderFactory? = null

    private var mLoginViewModel: LoginViewModel? = null
    private lateinit var loginBinding: ActivityLoginBinding

    // naver login value
    private var OAUTH_CLIENT_ID: String? = "UjyxaIsZ3i3GlROD6XPa"
    private var OAUTH_CLIENT_SECRET: String? = "THUoRkeses"
    private var OAUTH_CLIENT_NAME: String? = "shrinehaneal@naver.com"
    private var mOAuthLoginModule: OAuthLogin? = null

    //google login value
    private var Google_Login: SignInButton? = null
    private val RC_SIGN_IN = 1000
    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    //kakao oauth value
    private var callback: SessionCallback? = null


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = getViewDataBinding()
        mLoginViewModel!!.navigator = this

        //Oauth
        setKakaoOauth()
        setGoogleOauth()
        setNaverOauth()
    }

    fun setKakaoOauth() {

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }
    /**
     * getHashKey()
     *
     */
    fun setNaverOauth() {
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule!!.init(this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME)


        loginBinding.kakaoButton.setOnClickListener {

            callback = SessionCallback()

//            KakaoSDK.init(KakaoSDKAdapter())
            // Session 에 콜백 추가
            Session.getCurrentSession().addCallback(callback)
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

    fun setGoogleOauth() {

        Google_Login = findViewById(R.id.Google_Login) as SignInButton

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        mAuth = FirebaseAuth.getInstance()

        Google_Login!!.setOnClickListener(View.OnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "인증 실패", Toast.LENGTH_SHORT).show()

                    } else {
                        //Toast.makeText(this@LoginActivity, data.getStringExtra("nickName") + "통과" + data.getStringExtra("email"), Toast.LENGTH_SHORT).show()
                        mLoginViewModel!!.isUser(acct.getEmail(), acct.getDisplayName())
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        //findUser(data.getStringExtra("email"), data.getStringExtra("nickName"))


//                        Toast.makeText(this@LoginActivity, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(applicationContext, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
                    }
                }
    }

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {

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


    /**
     * kakao login method
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            //google
            if (requestCode == RC_SIGN_IN) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    //구글 로그인 성공해서 파베에 인증
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account!!)
                } else {
                    //구글 로그인 실패
                }
            }

            //kakao
            if(requestCode == 0){ //인증번호가 0임
                openMainActivity()
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }


    fun redirectSignupActivity() {
        openMainActivity()
    }

    private inner class SessionCallback : ISessionCallback {

        /**
         * Access Token을 성공적으로 발급 받아 Valid Access token 을 가지고 있는 상태, 일반적으로
         * 로그인 후 다음 Activity 로 이동
         */

        override fun onSessionOpened() {
            redirectSignupActivity()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Logger.e(exception)
            }
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

