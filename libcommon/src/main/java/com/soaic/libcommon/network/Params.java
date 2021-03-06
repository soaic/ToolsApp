package com.soaic.libcommon.network;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Retrofit URL封装
 *
 * @author XiaoSai
 * @version V1.0
 * @since 2016/11/3.
 */
public interface Params {

    /**
     * 查询资料 如登录 等请求操作
     *
     * @param url   请求URL地址
     * @param param 请求参数
     */
    @GET()
    Call<ResponseBody> paramsGet(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> param);

    /**
     * 创建新资源文件 如注册 等请求操作
     *
     * @param url   请求URL地址
     * @param param 请求参数
     */
    @FormUrlEncoded  //不包含文件上传 对应FieldMap注解
    @POST()
    Call<ResponseBody> paramsPost(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> param);

    @POST()
    Call<ResponseBody> paramsPostJSON(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    /**
     * 创建新资源文件 如上传头像 等请求操作
     *
     * @param url   请求URL地址
     * @param param 请求参数
     */
    @Multipart  //文件上传注解 对应PartMap注解
    @POST()
    Call<ResponseBody> paramsPostUpload(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> param);

    /**
     * 资源修改 如修改个人资料 等请求操作
     *
     * @param url 请求URL地址
     */
    @FormUrlEncoded
    @PUT()
    Call<ResponseBody> paramsPut(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> param);

    @PUT()
    Call<ResponseBody> paramsPutJSON(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    /**
     * 资源修改 如修改个人资料 等请求操作
     *
     * @param url 请求URL地址
     */
    @Multipart
    @PUT()
    Call<ResponseBody> paramsPutUpload(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> param);

    /**
     * 资源删除 如退出登录 等请求操作
     *
     * @param url 请求URL地址
     */
    @FormUrlEncoded
    @DELETE()
    Call<ResponseBody> paramsDelete(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> param);


}
