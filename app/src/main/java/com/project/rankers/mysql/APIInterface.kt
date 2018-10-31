package com.project.rankers.mysql

import com.project.rankers.model.User
import retrofit2.Call;
import retrofit2.http.*

interface APIInterface {

    /**
     * Body - request body로 Java 객테를 전달합니다.
     * Url - 동적인 Url이 필요할떄 사용됨
     * Query - 쿼리를 추가 할 수 있으며, 쿼릴를 URL 인코딩하려면
     * Field POST 에서만 동작하며 form-urlencoded로 데이터를 전송
     */
    @GET("api/unknown")
    fun doGetListResources(): Call<MultipleResource>

    @POST("api/users")
    fun createUser(@Body user: User)

    @GET("api/users?")
    fun dogetUserList(@Query("page") page: String)

    @FormUrlEncoded
    @POST("api/users?")
    fun  doCreateUsereWIthField(@Field("name") name : String,
                                @Field("job") job : String)

}