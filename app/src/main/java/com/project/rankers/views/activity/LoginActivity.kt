package com.project.rankers.views.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.project.rankers.R
import com.project.rankers.databinding.ActivityLoginBinding
import com.project.rankers.kakao.KakaoSignUpActivity
import com.kakao.auth.AuthType
import com.kakao.util.exception.KakaoException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.json.JSONObject
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.project.rankers.model.User
import com.project.rankers.retrofit.`interface`.RankersUser
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val TAG: String? = "LoginActivity"
    private var OAUTH_CLIENT_ID: String? = "aaQJOdhzRUarseiLP5A9"
    private var OAUTH_CLIENT_SECRET: String? = "MR0tI738Hz"
    private var OAUTH_CLIENT_NAME: String? = "shrinehaneal"
    private val RC_SIGN_IN = 10
    private var mGoogleApiClient: GoogleApiClient? = null

    private var mAuth: FirebaseAuth? = null

    var mOAuthLoginModule: OAuthLogin? = null
    lateinit var mContext: Context
    private var sessionCallback: SessionCallback? = null
    var rankersServerRepo: RankersServerRepo? = null

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mContext = this
        //싱글톤 패턴
        mAuth = FirebaseAuth.getInstance()

        user = User()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()


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
            initNaverData()
            if (mOAuthLoginModule != null) {
                mOAuthLoginModule!!.startOauthLoginActivity(mContext as LoginActivity, mOAuthLoginHandler)
            }
        }
        loginBinding.googleButton.setOnClickListener {
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3000)
                if (data != null) {
                    findUser(data.getStringExtra("email"), data.getStringExtra("nickName"))
                }
        }
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // 구글 로그인 성공
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(sessionCallback)

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
                Log.d(TAG, exception.toString())
            }
            //setContentView(R.layout.activity_login)
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
        override fun onPreExecute() {//작업이 실행되기 전에 먼저 실행.

        }

        override fun doInBackground(vararg params: Void): String {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            val url = "https://openapi.naver.com/v1/nid/me"
            val accessToken = mOAuthLoginModule!!.getAccessToken(mContext)
            return mOAuthLoginModule!!.requestApi(mContext, accessToken, url)
        }

        override fun onPostExecute(content: String) =//doInBackground 에서 리턴된 값이 여기로 들어온다.
                try {
                    val jsonObject = JSONObject(content)
                    val response = jsonObject.getJSONObject("response")
                    val email = response.getString("email")
                    val name = response.getString("nickname")
                    findUser(email, name)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(@NonNull task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            // 로그인 성공
                            val user: FirebaseUser = mAuth!!.currentUser!!
                            findUser(user.email, user.displayName)
                        } else {
                            Log.d(TAG, "GOOGLE LOGIN FAILED")
                        }
                    }
                })
    }

    fun findUser(email: String?, nickName: String?){
        val server = RankersPostCreator.create(RankersUser::class.java)
        server.getID(email).enqueue(object : Callback<RankersServerRepo>{
            override fun onFailure(call: Call<RankersServerRepo>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onResponse(call: Call<RankersServerRepo>, response: Response<RankersServerRepo>) {
                rankersServerRepo = response.body()
                if (rankersServerRepo != null) {
                    if (rankersServerRepo!!.getSuccess()) {
                        Log.d("LoginActivity", "Exist User")

                        user!!.seteMail(email)
                        redirectMainActivity()
                    } else {
                        uploadUser(email, nickName)
                    }
                }
            }
        })
    }
    fun uploadUser(email: String?, nickName: String?) {
        val server = RankersPostCreator.create(RankersUser::class.java)
        server.postUserCreator(email, nickName).enqueue(object : Callback<RankersServerRepo> {
            override fun onFailure(call: Call<RankersServerRepo>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onResponse(call: Call<RankersServerRepo>, response: Response<RankersServerRepo>) {
                rankersServerRepo = response.body()
                if (rankersServerRepo != null) {
                    if (rankersServerRepo!!.getSuccess()) {
                        Log.d("LoginActivity", "Success")
                        user!!.seteMail(email)
                        redirectMainActivity()
                    } else {
                        Log.d("LoginActivity", "False")
                    }
                }
            }
        })
    }


}


