package com.wp.lifeplan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wp.lifeplan.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class CollectTimeLinearView extends LinearLayout
        implements View.OnClickListener, DatePickerDialog.Callback {
    private TextView collectTitle;
    private EditText timer;
    private Date date;
    private DatePickerDialog datePickerDialog;

    public CollectTimeLinearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CollectTimeLinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setHint(String hint) {
        timer.setHint(hint);
    }

    public void setEnable(boolean enable) {
        timer.setEnabled(enable);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CollectTimeLinearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater factory = LayoutInflater.from(context);
        View v = factory.inflate(R.layout.collect_time_layout, this);
        datePickerDialog = DatePickerDialog.newInstance(this, getContext());
        collectTitle = (TextView) v.findViewById(R.id.collect_title);
        timer = (EditText) v.findViewById(R.id.timer);
        timer.setKeyListener(null);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CollectTimeView);
        String titleStr = a.getString(R.styleable.CollectTimeView_collect_title_str);
        if (!TextUtils.isEmpty(titleStr)) {
            collectTitle.setText(titleStr);
        }

        int titleTextColor = a.getColor(R.styleable.CollectTimeView_collect_titleTextColor,
                getResources().getColor(android.R.color.black));
        collectTitle.setTextColor(titleTextColor);

        a.recycle();

        timer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (datePickerDialog.isAdded()) {
            datePickerDialog.dismiss();
        }
        datePickerDialog.show(((Activity) getContext()).getFragmentManager(), "DatePickerDialog");
    }

    public long getDate() {
        return date != null ? date.getTime() : 0;
    }

    public void setDate(String date) {
        timer.setText(date);
    }

    public static final SimpleDateFormat SDF_LOG = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_LOG2 = new SimpleDateFormat("yyyy-MM-dd\nHH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_LOG3 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Override
    public void onDateSelected(long millisecond) {
        date = new Date(millisecond);
        timer.setText(SDF_LOG3.format(date));
    }
}
