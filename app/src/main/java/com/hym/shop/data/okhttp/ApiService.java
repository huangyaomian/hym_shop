package com.hym.shop.data.okhttp;

import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.Banner;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.LoginBean;
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
    String BASE_URL = "http:112.124.22.238:8081/course_api/";

    /*@GET("featured")
    public Call<RecommendBean2> getApps(@Query("p") String jsonParam);*/

    @GET("cniaoplay/featured")
    public Observable<BaseBean<List<AppInfoBean>>> getApps(@Query("p") String jsonParam);

    //http://112.124.22.238:8081/course_api/cniaoplay/index?p={"publicParams":{"model":"12","imei":"12","la":"12","os":"12","resolution":"12","sdk":"12","densityScaleFactor":"12"}}
    @GET("cniaoplay/index")
    public Observable<BaseBean<HomeBean>> getHome();

    @GET("cniaoplay/toplist")
    public Observable<BaseBean<PageBean<AppInfoBean>>> getTopList(@Query("page") int page);

    @GET("cniaoplay/game")
    public Observable<BaseBean<PageBean<AppInfoBean>>> getGames(@Query("page") int page);

    @POST("cniaoplay/login")
    public Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean bean);

    @GET("cniaoplay/category")
    Observable<BaseBean<List<SortBean>>> getCategories();

    @GET("cniaoplay/category/featured/{categoryid}")
    Observable<BaseBean<PageBean<AppInfoBean>>> getFeaturedAppsBySort(@Path("categoryid") int categoryid, @Query("page") int page);

    @GET("cniaoplay/category/toplist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfoBean>>> getTopListAppsBySort(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("cniaoplay/category/newlist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfoBean>>> getNewListAppsBySort(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("cniaoplay/app/{id}")
    Observable<BaseBean<AppInfoBean>> getAppDetail(@Path("id")int id);

    @GET("cniaoplay/apps/updateinfo")
    Observable<BaseBean<List<AppInfoBean>>> getAppsUpdateinfo(@Query("packageName") String packageName,@Query("versionCode") String versionCode);

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


}
