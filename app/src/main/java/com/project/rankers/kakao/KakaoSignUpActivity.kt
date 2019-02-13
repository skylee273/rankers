package com.project.rankers.kakao

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.project.rankers.views.activity.LoginActivity
import com.project.rankers.views.activity.MainActivity
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import java.util.*

open class KakaoSignUpActivity : Activity() {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    private val TAG = javaClass.simpleName
    var flag : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMe()
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    private fun requestMe() { //유저의 정보를 받아오는 함수
        val keys = ArrayList<String>()
        keys.add("properties.nickname")
        keys.add("kakao_account.email")

        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            /**
             * 사용자 정보 요청이 성공한 경우로 사용자 정보 객체를 받는다.
             * @param result
             */
            override fun onSuccess(result: MeV2Response) {
                val intent = Intent()
                intent.putExtra("email", result.kakaoAccount.email)
                intent.putExtra("nickName", result.nickname)
                setResult(RESULT_OK, intent)
                finish()
            }

            /**
             * 가입이 안된 경우와 세션이 닫힌 경우를 제외한 다른 이유로 요청이 실패한 경우의 콜백
             * @param errorResult
             */
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
                Log.d(TAG, message)
                redirectLoginActivity()
            }

            /**
             * 세션이 닫혀 실패한 경우로 에러 결과를 받습니다. 재로그인 / 토큰발급이 필요하다.
             * @param errorResult
             */
            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.d(TAG, "ERROR : " + errorResult.toString())
            }
        })
    }

    private fun redirectMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    protected fun redirectLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

}