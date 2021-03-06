package com.hym.shop.data.okhttp;

import com.hym.shop.BuildConfig;
import com.hym.shop.bean.Address;
import com.hym.shop.bean.Banner;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.LoginBean;
import com.hym.shop.bean.Order;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.bean.PageBean;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.bean.SortBean;
import com.hym.shop.bean.Subject;
import com.hym.shop.bean.SubjectDetail;
import com.hym.shop.bean.User;
import com.hym.shop.bean.requestbean.LoginRequestBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    https://github.com/15829238397/CN5E-shop


    //    http://112.124.22.238:8081/course_api/cniaoplay/featured?p={%22page%22:0}
//    String BASE_URL = "http:112.124.22.238:8081/course_api/";
    String BASE_URL = BuildConfig.API_HOST + "/course_api/";

    /*@GET("featured")
    public Call<RecommendBean2> getApps(@Query("p") String jsonParam);*/


    //http://112.124.22.238:8081/course_api/cniaoplay/index?p={"publicParams":{"model":"12","imei":"12","la":"12","os":"12","resolution":"12","sdk":"12","densityScaleFactor":"12"}}


    @POST("cniaoplay/login")
    Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean bean);

    @GET("cniaoplay/category")
    Observable<BaseBean<List<SortBean>>> getCategories();


    @GET("cniaoplay/subject/hot")
    Observable<BaseBean<PageBean<Subject>>> subjects(@Query("page") int page);

    @GET("cniaoplay/subject/{id}")
    Observable<BaseBean<SubjectDetail>> subjectDetail(@Path("id") int id);

    @GET("cniaoplay/search/suggest")
    Observable<BaseBean<List<String>>> searchSuggest(@Query("keyword") String keyword);

    @GET("cniaoplay/search")
    Observable<BaseBean<SearchResult>> search(@Query("keyword") String keyword);

    //下面是菜鸟商城的
//    String BASE_URL_SHOP = "http:112.124.22.238:8081/course_api/";


    @GET("banner/query")
    Observable<List<Banner>> getBanner(@Query("type") int type);

    @GET("campaign/recommend")
    Observable<List<HomeCampaign>> getHomeCampaign();

    @GET("wares/hot")
    Observable<HotWares> getHotWares(@Query("curPage") int curPage, @Query("pageSize") int pageSize);

    @GET("category/list")
    Observable<List<Category>> getCategoryList();

    @GET("wares/list")
    Observable<HotWares> getWaresList(@Query("curPage") int curPage, @Query("pageSize") int pageSize,@Query("categoryId") int categoryId);

    @GET("wares/campaign/list")
    Observable<HotWares> getCampaignWaresList(@Query("curPage") int curPage, @Query("pageSize") int pageSize,@Query("campaignId") int campaignId, @Query("orderBy") int orderBy );

    @FormUrlEncoded //用户登录
    @POST("auth/login")
    Observable<BaseBean<User>> shopLogin(@Field("phone") String phone, @Field("password") String password);

    @POST("auth/reg")
    Observable<LoginBean> shopReg(@Body LoginRequestBean bean);

    @FormUrlEncoded
    @POST("order/create")
    Observable<BaseBean<OrderRespMsg>> submitOrder(@Field("user_id") long userId, @Field("item_json") String itemJson, @Field("amount") int amount, @Field("addr_id") int addrId, @Field("pay_channel") String payChannel, @Field("token") String token);

    @FormUrlEncoded
    @POST("order/complete")
    Observable<BaseBean> completeOrder(@Field("order_num") String orderNum, @Field("status") String status, @Field("token") String token);

    @FormUrlEncoded
    @POST("order/list")
    Observable<LoginBean> getOrderList(@Field("phone") String phone, @Field("password") String password);

    @GET("addr/list")
    Observable<List<Address>> getAddrList(@Query("user_id") long userId, @Query("token") String token);

    @FormUrlEncoded
    @POST("addr/create")
    Observable<BaseBean> addAddr(@Field("user_id") long user_id, @Field("consignee") String consignee,
                                  @Field("phone") String phone, @Field("addr") String addr,
                                  @Field("zip_code") String zip_code, @Field("token") String token);

    @FormUrlEncoded
    @POST("addr/update")
    Observable<BaseBean> updateAddr(@Field("id") long id, @Field("consignee") String consignee,
                                     @Field("phone") String phone, @Field("addr") String addr,
                                     @Field("zip_code") String zip_code, @Field("is_default") boolean is_default,
                                     @Field("token") String token);

    @FormUrlEncoded
    @POST("addr/del")
    Observable<BaseBean> delAddr(@Field("id") Long id, @Field("token") String token);


    @FormUrlEncoded //我的订单
    @POST("order/list")
    Observable<List<Order>> orderList(@Field("user_id") long user_id, @Field("status") int status, @Field("token") String token);

}
