package com.example.baseapplication.http;


import com.example.baseapplication.bean.BaseBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2019/6/18  14:41
 * desc   : 基类
 */
public interface ApiService {

    /**
     * post请求
     */
    @FormUrlEncoded
    @POST(ApiConstant.EG)
    Observable<BaseBean> creatAddress(@FieldMap Map<String, Object> params);

    /**
     * get请求
     */
    @GET(ApiConstant.EG)
    Observable<BaseBean> getAddressList(@Query("member_id") int member_id);

    /**
     * 动态拼接地址
     */
    @FormUrlEncoded
    @POST("v1/history/{category}")
    Observable<BaseBean> createHistory(@Path("category") String category,
                                       @FieldMap Map<String, Object> params);

    /**
     * 使用指定url
     */
    @FormUrlEncoded
    @POST()
    Observable<BaseBean> uploadFitSleepData(@Url String url, @FieldMap Map<String, Object> params);

    @GET()
    Observable<BaseBean> cleanFitSleepData(@Url String url, @Query("mac") String mac);


    /**
     * post上传文件
     */
    @Multipart
    @POST(ApiConstant.EG)
    Observable<BaseBean> updateMemberAvatar(@PartMap Map<String, RequestBody> params);

    /**
     * 上传文件时 RequestBody 示例
     */
    static Map<String, RequestBody> getRequestBodyDemo() {
        Map<String, RequestBody> params = new HashMap<>();
        File file = new File("path");
        params.put("images[]\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        params.put("title", RequestBody.create(MediaType.parse("text/plain"), "title"));
        return params;
    }

}

