package com.hym.shop.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.common.imageloader.ImageLoader;
import com.xuexiang.xui.widget.textview.ExpandableTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class AppInfoAdapter extends BaseQuickAdapter<AppInfoBean, BaseViewHolder> implements LoadMoreModule {


    String baseImgUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";
    private Builder mBuilder;


    private AppInfoAdapter(Builder builder) {
        super(builder.layoutId);
        this.mBuilder = builder;
    }



    public static Builder builder(){
        return new Builder();
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AppInfoBean appInfoBean) {

        ImageLoader.load(baseImgUrl+appInfoBean.getIcon(),baseViewHolder.getView(R.id.img_app_icon));

        if (mBuilder.isShowName){
            baseViewHolder.setText(R.id.home_recyclerview_name,appInfoBean.getDisplayName());
        }




        if (mBuilder.isUpdateStatus) {
            //可扩展的TextView
            ExpandableTextView viewChangeLog =baseViewHolder.getView(R.id.view_changelog);
            Log.d("convert", "convert: " + appInfoBean.getChangeLog());
            viewChangeLog.setText(appInfoBean.getChangeLog());

            TextView txtViewBrief = baseViewHolder.getView(R.id.home_recyclerview_brief);
            if(txtViewBrief !=null) {
                txtViewBrief.setVisibility( View.VISIBLE);
                txtViewBrief.setText("v"+appInfoBean.getVersionName() +  "  " + (appInfoBean.getApkSize() / 1014 / 1024) +"Mb");
            }
        }else {

            if (mBuilder.isShowPosition){
                TextView txtViewPosition = baseViewHolder.getView(R.id.home_recyclerview_position);
                if (txtViewPosition != null){
                    txtViewPosition.setVisibility(mBuilder.isShowPosition? View.VISIBLE:View.GONE);
                    txtViewPosition.setText(appInfoBean.getPosition() + 1 + ".");
                }
            }


            if (mBuilder.isShowBrief) {
                TextView txtViewBrief = baseViewHolder.getView(R.id.home_recyclerview_brief);
                if (txtViewBrief != null){
                    txtViewBrief.setVisibility(mBuilder.isShowBrief? View.VISIBLE:View.GONE);
                    txtViewBrief.setText(appInfoBean.getBriefShow());
                }
            }


            if (mBuilder.isShowCategoryName) {
                TextView categoryView = baseViewHolder.getView(R.id.home_recyclerview_category);
                if (categoryView != null){
                    categoryView.setVisibility(mBuilder.isShowCategoryName? View.VISIBLE:View.GONE);
                    categoryView.setText(appInfoBean.getLevel1CategoryName());
                }
            }

            if (mBuilder.isShowApkSize) {
                TextView txtViewSize = baseViewHolder.getView(R.id.txt_apk_size);
                if (txtViewSize != null){
                    txtViewSize.setVisibility(mBuilder.isShowApkSize? View.VISIBLE:View.GONE);
                    txtViewSize.setText(appInfoBean.getApkSize() / 1024 / 1024 + "MB");
                }

            }

            if (mBuilder.isShowScore) {
                TextView txtViewScore = baseViewHolder.getView(R.id.home_recyclerview_score);
                if (txtViewScore != null){
                    txtViewScore.setVisibility(mBuilder.isShowScore? View.VISIBLE:View.GONE);
                    txtViewScore.setText(appInfoBean.getRatingScore() + "");
                }

            }


        }

    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position, @NotNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder,position,payloads);
        }
    }



    public static class Builder{
        private boolean isShowPosition = true;
        private boolean isShowCategoryName = true;
        private boolean isShowBrief = true;
        private boolean isShowApkSize = false;
        private boolean isShowName = true;
        private int layoutId = R.layout.home_recyclerview_item;
        private boolean isUpdateStatus = false;
        private boolean isDownloadBtnVisible = false;
        private boolean isShowScore = false;

        public Builder showPosition(boolean b){
            this.isShowPosition = b;
            return this;
        }

        public Builder showCategoryName(boolean b){
            this.isShowCategoryName = b;
            return this;
        }

        public Builder showBrief(boolean b){
            this.isShowBrief = b;
            return this;
        }

        public Builder showApkSize(boolean b){
            this.isShowApkSize = b;
            return this;
        }

        public Builder showName(boolean b){
            this.isShowName = b;
            return this;
        }

        public AppInfoAdapter build(){
            return new AppInfoAdapter(this);
        }

        public Builder layout(int resId){
            this.layoutId = resId;
            return this;
        }


        public Builder updateStatus(boolean b){
            this.isUpdateStatus = b;
            return this;
        }

        public Builder setDownloadBtnVisible(boolean b){
            this.isDownloadBtnVisible = b;
            return this;
        }

        public Builder showScore(boolean b){
            this.isShowScore = b;
            return this;
        }



    }


}
