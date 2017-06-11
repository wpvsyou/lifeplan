package com.wp.lifeplan.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wp.lifeplan.R;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class ExtendTextView extends LinearLayout implements View.OnClickListener, View.OnTouchListener {
    private TextView title;
    private ImageView extendButton;
    private EditText mainTv;

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater factory = LayoutInflater.from(context);
        View v = factory.inflate(R.layout.extent_text_view_layout, this);
        title = (TextView) v.findViewById(R.id.title);
        extendButton = (ImageView) v.findViewById(R.id.extend_button);
        mainTv = (EditText) v.findViewById(R.id.text_view);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.ExtendTextView);
            String titleStr = a.getString(R.styleable.ExtendTextView_title);
            if (!TextUtils.isEmpty(titleStr)) {
                title.setText(titleStr);
            }

            int titleTextColor = a.getColor(R.styleable.ExtendTextView_titleTextColor,
                    getResources().getColor(android.R.color.white));
            title.setTextColor(titleTextColor);

            String hint = a.getString(R.styleable.ExtendTextView_hint);
            if (!TextUtils.isEmpty(hint)) {
                mainTv.setHint(hint);
            }

            int mainTextViewColor = a.getColor(R.styleable.ExtendTextView_mainTextColor,
                    getResources().getColor(android.R.color.black));
            mainTv.setTextColor(mainTextViewColor);

            a.recycle();
        }

        extendButton.setOnClickListener(this);
//        extendButton.setSelected(true);
//        mainTv.setVisibility(VISIBLE);
        mainTv.setOnTouchListener(this);
        setExtend(true);
    }

    public void setEnable(boolean enable) {
        if (!enable) {
            mainTv.setKeyListener(null);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.text_view && canVerticalScroll(mainTv))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight()
                - editText.getCompoundPaddingTop()
                - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    public ExtendTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExtendTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExtendTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public void onClick(View v) {
        boolean needExtend = mainTv.getVisibility() == GONE;
        setExtend(needExtend);
    }

    private void setExtend(boolean extend) {
        extendButton.setSelected(extend);
        mainTv.setVisibility(extend ? VISIBLE : GONE);
    }

    public void setText(String str) {
        mainTv.setText(str);
    }

    public String getText() {
        Editable eb = mainTv.getText();
        return eb != null ? eb.toString() : "";
    }
}
