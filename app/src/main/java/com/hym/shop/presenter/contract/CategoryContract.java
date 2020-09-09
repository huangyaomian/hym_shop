package com.hym.shop.presenter.contract;

import com.hym.shop.bean.Banner;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

public interface CategoryContract {

    interface  CategoryView extends BaseView {

        void showCategory(List<Category> categories);
        void showCategoryWares(HotWares hotWares);
    }


    interface ICategoryModel{

        Observable<List<Category>> getCategory();

        Observable<HotWares> getCategoryWares(int curPage, int pageSize, int categoryId);

        Observable<List<Banner>> getBanner(int type);

    }


}
