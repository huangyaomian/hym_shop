package com.hym.shop.presenter;

import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import org.jetbrains.annotations.Contract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryModel, CategoryContract.CategoryView> {


    @Inject
    public CategoryPresenter(CategoryContract.ICategoryModel iCategoryModel, CategoryContract.CategoryView categoryView) {
        super(iCategoryModel, categoryView);
    }



    public void getCategory(){

        mModel.getCategory()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<List<Category>>(mContext,mView) {
                    @Override
                    public void onNext(List<Category> categoryList) {
                        mView.showCategory(categoryList);
                    }
                });
    }

    public void getCategoryWares(int curPage, int pageSize, int categoryId, boolean isShowProgress){
        mModel.getCategoryWares(curPage,pageSize,categoryId)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<HotWares>(mContext,mView) {
                    @Override
                    public void onNext(HotWares hotWares) {
                        mView.showCategoryWares(hotWares);
                    }

                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }


                });
    }






}
