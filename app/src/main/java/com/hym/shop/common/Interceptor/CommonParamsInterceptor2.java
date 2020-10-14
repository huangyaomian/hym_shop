package com.hym.shop.common.Interceptor;

import android.content.Context;

import com.google.gson.Gson;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.ACache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
  * 通用的http攔截器
  * @author Mika.
  * @created 2020/10/12 18:26.
  */
public class CommonParamsInterceptor2 implements Interceptor {



    /**
     * 请求方法-GET
     */
    private static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求方法POST
     */
    private static final String REQUEST_METHOD_POST = "POST";

    /**
     * 基础参数-平台号
     */
    private static final String REQUEST_COMMON_PARAM_PLATFORM = "2";

    /**
     * 基础参数-个体户Id
     */
    private static final String REQUEST_COMMON_PARAM_PERSONAL_ID = "0";

    /**
     * 基础参数-未登录 userId
     */
    private static final String REQUEST_COMMON_PARAM_USER_ID_DEFAULT = "0";

    /**
     * 基础参数-未登录 token
     */
    private static final String REQUEST_COMMON_PARAM_TOKEN_DEFAULT = "";

    private Context mContext;

    public CommonParamsInterceptor2(Context context) {
        this.mContext = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原先的请求对象
        Request request = chain.request();
        if (REQUEST_METHOD_GET.equals(request.method())) {
            request = addGetBaseParams(request);
        } else if (REQUEST_METHOD_POST.equals(request.method())) {
            request = addPostBaseParams(request);
        }
        return chain.proceed(request);
    }


    /**
     * 添加GET方法基础参数
     *
     * @param request
     * @return
     */
    private Request addGetBaseParams(Request request) {


        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("platform", REQUEST_COMMON_PARAM_PLATFORM)//平台号
//                .addQueryParameter("version", ToolHelper.getVersionName())//版本号
//                .addQueryParameter("enterpriseId", FSaveData.Login.getEnterpriseId())//企业Id
//                .addQueryParameter("token", REQUEST_COMMON_PARAM_PERSONAL_ID)//个体户id
                .build();

        //根据登录状态添加userId&&token
        ACache aCache = ACache.get(mContext);
        String token = aCache.getAsString(Constant.TOKEN);
        String userId = aCache.getAsString(Constant.USER_ID);
//        if (ToolHelper.logined()) {
        if (token != null) {
            httpUrl = httpUrl.newBuilder()
//                    .addQueryParameter("userId", FSaveData.Login.getUserId())
                    .addQueryParameter("token", token)
                    .addQueryParameter("user_id", userId)
                    .build();
        } else {
            httpUrl = httpUrl.newBuilder()
                    .addQueryParameter("user_id", REQUEST_COMMON_PARAM_USER_ID_DEFAULT)
                    .addQueryParameter("token", REQUEST_COMMON_PARAM_TOKEN_DEFAULT)
                    .build();
        }

      /*  //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            paramMap.put(nameList.get(i), httpUrl.queryParameterValue(i));
        }
//        httpUrl = httpUrl.newBuilder().addQueryParameter("sign", ToolHelper.dataSignature(paramMap)).build();
*/
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    /**
     * 添加POST方法基础参数
     *
     * @param request
     * @return
     */
    private Request addPostBaseParams(Request request) {

        /**
         * request.body() instanceof FormBody 为true的条件为：
         * 在ApiService 中将POST请求中设置
         * 1，请求参数注解为@FieldMap
         * 2，方法注解为@FormUrlEncoded
         */
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();

            for (int i = 0; i < formBody.size(); i++) {
                //@FieldMap 注解 Map元素中 key 与 value 皆不能为null,否则会出现NullPointerException
                if (formBody.value(i) != null)
                    builder.add(formBody.name(i), formBody.value(i));
            }

            builder
                    .add("platform", REQUEST_COMMON_PARAM_PLATFORM)//平台
//                    .add("version", ToolHelper.getVersionName())//版本号
//                    .add("enterpriseId", FSaveData.Login.getEnterpriseId())//企业Id
                    .add("personalId", REQUEST_COMMON_PARAM_PERSONAL_ID);//个体户id


            //根据登录状态添加userId&&token
            ACache aCache = ACache.get(mContext);
            String token = aCache.getAsString(Constant.TOKEN);
            String userId = aCache.getAsString(Constant.USER_ID);
            if (token != null) {
                builder
//                        .add("userId", FSaveData.Login.getUserId())
                        .add("token", token)
                        .add("user_id", userId);
            } else {
                builder
                        .add("user_id", REQUEST_COMMON_PARAM_USER_ID_DEFAULT)
                        .add("token", REQUEST_COMMON_PARAM_TOKEN_DEFAULT);
            }

            //添加签名
            Map<String, Object> paramMap = new HashMap<>();
            formBody = builder.build();
          /*  for (int i = 0; i < formBody.size(); i++) {
                paramMap.put(formBody.name(i), formBody.value(i));
            }
//            formBody = builder
//                    .add("sign", ToolHelper.dataSignature(paramMap))
//                    .build();
*/
            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

}
