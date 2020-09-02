package com.hym.shop.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.TintTypedArray;

import com.hym.shop.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CNToolbar extends Toolbar {

    private LayoutInflater mLayoutInflater;
    private View mView;

    private TextView mTitleText;
    private EditText mSearchView;
    private ImageButton mRightButton;

    public CNToolbar(Context context) {
        this(context,null);
    }

    public CNToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CNToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    @SuppressLint("RestrictedApi")
    public CNToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
        setContentInsetsAbsolute(10,10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs,R.styleable.CnToolbar,defStyleAttr,0);

            final Drawable rightIcon = a.getDrawable(R.styleable.CnToolbar_right_button_icon);

            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

            boolean isShowSearchView = a.getBoolean(R.styleable.CnToolbar_is_show_search_view,false);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }

            a.recycle();
        }
    }

    private void initView() {

        if (mView == null) {
            mView = mLayoutInflater.inflate(R.layout.my_toolbar,null);

            mTitleText = mView.findViewById(R.id.toolbar_title);
            mSearchView = mView.findViewById(R.id.toolbar_search_view);
            mRightButton = mView.findViewById(R.id.toolbar_right_button);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT);

            addView(mView,lp);
        }

    }

    public void setRightButtonIcon(Drawable icon){
        if (mRightButton != null) {
            mRightButton.setImageDrawable(icon);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener li){
        mRightButton.setOnClickListener(li);
    }


    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTitleText != null) {
            mTitleText.setText(title);
            showTitleView();
        }
    }

    public void showSearchView(){
        if (mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView(){
        if (mSearchView != null) {
            mSearchView.setVisibility(GONE);
        }
    }

    public void showTitleView(){
        if (mTitleText != null) {
            mTitleText.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView(){
        if (mTitleText != null) {
            mTitleText.setVisibility(GONE);
        }
    }


}
