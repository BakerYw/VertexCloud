package com.vertex.cloud.mvp.model.net;


import com.vertex.cloud.mvp.model.entity.CloudApiResult;
import com.vertex.cloud.mvp.model.entity.CodeEntity;
import com.vertex.cloud.mvp.model.entity.LoginEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * 获取图形验证码
     *
     * @return
     */
    @GET(Urls.CODE)
    Observable<CloudApiResult<CodeEntity>> getCode();

    /**
     * 获取登录短信验证码
     *
     * @return
     */
    @POST(Urls.SMSCODE)
    Observable<CloudApiResult<CodeEntity>> getSMSCode(@Body RequestBody requestBody);

    /**
     * 短信验证码快捷登录
     *
     * @return
     */
    @POST(Urls.SMSLOGIN)
    Observable<CloudApiResult<LoginEntity>> smsLogin(@Body RequestBody requestBody);

}
