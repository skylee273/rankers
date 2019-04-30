package com.project.rankers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.R
import com.project.rankers.databinding.ActivityLoginBinding
import com.project.rankers.kakao.KakaoSignUpActivity
import com.kakao.auth.AuthType
import com.kakao.util.exception.KakaoException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.main.MainActivity
import com.project.rankers.ui.register.RegisterActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() , LoginNavigator{


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLoginViewModel: LoginViewModel? = null
    private lateinit var loginBinding: ActivityLoginBinding

    // google login value
    private val RC_SIGN_IN = 234
    var mGoogleSignInClient: GoogleSignInClient? = null
    private var mAuth: FirebaseAuth? = null
    var fbUser: FirebaseUser? = null

    // naver login value
    private var OAUTH_CLIENT_ID: String? = "Ap10LMIr_R6my9DWFJtk"
    private var OAUTH_CLIENT_SECRET: String? = "n9TZAfGAVc"
    private var OAUTH_CLIENT_NAME: String? = "shrinehaneal"
    private var mOAuthLoginModule: OAuthLogin? = null


    fun newIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    override fun googleLogin() {
        signIn()
    }
    override fun handleError(throwable: Throwable) {
        displayLog("Login", throwable.message!!)
    }

    override fun login() {

    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity(email: String) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra("userEmail",email)
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

    override fun onStart() {
        super.onStart()

        if (mAuth!!.getCurrentUser() != null) {
            fbUser = mAuth!!.currentUser
        }
    }

    private var sessionCallback: SessionCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = getViewDataBinding()
        mLoginViewModel!!.navigator = this

        // google login
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        fbUser = mAuth!!.currentUser

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
        loginBinding.naverButton.setOnClickListener {
            loginBinding.buttonOAuthLoginImg.performClick()
        }

        loginBinding.buttonOAuthLoginImg.setOnClickListener {
            if (mOAuthLoginModule != null) {
                mOAuthLoginModule!!.startOauthLoginActivity(mContext as LoginActivity, mOAuthLoginHandler)
            }
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        mAuth!!.signOut()
        mGoogleSignInClient!!.signOut().addOnCompleteListener(this){

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
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
//        Session.getCurrentSession().removeCallback(sessionCallback)
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
                mLoginViewModel!!.onNaverLogin(accessToken)
                displayLog("Naver Login", accessToken + refreshToken + expiresAt + tokenType)
            } else {
                val errorCode = mOAuthLoginModule!!.getLastErrorCode(mContext).code
                val errorDesc = mOAuthLoginModule!!.getLastErrorDesc(mContext)
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                mLoginViewModel!!.isUser(account.email, account.displayName)
            }
        } catch (e: ApiException) {
            displayLog("Google Login", e.toString())
        }
    }

}


