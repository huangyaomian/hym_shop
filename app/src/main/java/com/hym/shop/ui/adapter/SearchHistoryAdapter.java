package com.hym.shop.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SearchHistoryAdapter() {
        super(R.layout.template_search_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.btn,item);

//        helper.addOnClickListener(R.id.btn);

    }
}
