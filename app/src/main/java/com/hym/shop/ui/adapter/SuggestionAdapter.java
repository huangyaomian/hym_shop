package com.hym.shop.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

public class SuggestionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public SuggestionAdapter(Context context) {
        super(R.layout.suggest_item);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


        ImageView icon = helper.getView(R.id.icon_suggestion);
        icon.setImageDrawable(new IconicsDrawable(mContext, Ionicons.Icon.ion_ios_search)
                .color(ContextCompat.getColor(mContext,R.color.theme_while)).sizeDp(16));

        helper.setText(R.id.txt_suggestion,item);



    }
}
