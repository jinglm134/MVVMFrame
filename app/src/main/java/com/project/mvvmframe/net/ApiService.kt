package com.project.mvvmframe.net

import com.project.mvvmframe.constant.ApiConfig
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 请求接口类
 * @CreateDate 2020/4/21 14:19
 * @Author jaylm
 */
interface ApiService {

    @POST(ApiConfig.LOGIN)
    suspend fun login(@Body body: Map<String, String>): Observable<ResponseBody>

    /*  //我的奖品列表
      @FormUrlEncoded
      @POST(ApiConfig.PRIZE_LIST)
      suspend fun getPrizeList(
          @Field("pageNumber") pageNumber: Int, @Field("pageSize") pageSize: Int,
          @Field("status") status: String
      ): Observable<BaseResponse<List<PrizeListBean>>>

      @POST(ApiConfig.NOTICE_LIST)
      suspend fun getNoticeList(@Body body: RequestBody): Observable<BaseResponse<NoticeListBean>>



      @POST(ApiConfig.GET_CODE)
      suspend fun getCode(@Body body: Map<String, String>): Observable<BaseResponse<String>>


      @GET("http://bibr.mixcdn.co//test/banner/banner.json")
      suspend fun getBanner(@Query("v") time: Long): Observable<BannerBean>*/


    /*  @Headers("url_name:banner")
      @GET("{firstPathSegment}/${ApiConfig.GET_BANNER}")
     suspend fun getBanner(@Path("firstPathSegment") firstPathSegment: String, @Query("v") time: Long): Observable<BaseResponse<BannerBean>>

    @Headers("url_name:banner")
    @GET(ApiConfig.GET_BANNER)
    suspend fun getBanner(@Query("v") time: Long): Observable<BaseResponse<BannerBean>>*/


}