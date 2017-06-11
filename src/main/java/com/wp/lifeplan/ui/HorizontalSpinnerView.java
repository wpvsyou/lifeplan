package com.wp.lifeplan.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wp.lifeplan.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class HorizontalSpinnerView extends LinearLayout{
    private Spinner spinner;
    private TextView title;
    private ArrayList<String> enters = new ArrayList<String>();

    public HorizontalSpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalSpinnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalSpinnerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setEnable(boolean enable) {
        spinner.setEnabled(enable);
    }
    private void init(Context context, AttributeSet attrs){
        enters.clear();
        LayoutInflater factory = LayoutInflater.from(context);
        View v = factory.inflate(R.layout.horizontal_spinner_layout, this);
        title = (TextView) v.findViewById(R.id.title);
        spinner = (Spinner) v.findViewById(R.id.spinner);

        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.HorizontalSpinnerView);
        int titleTextColor = a.getColor(R.styleable.HorizontalSpinnerView_spinner_titleTextColor,
                getResources().getColor(android.R.color.black));
        title.setTextColor(titleTextColor);

        String titleStr = a.getString(R.styleable.HorizontalSpinnerView_spinner_title_str);
        if (!TextUtils.isEmpty(titleStr)) {
            title.setText(titleStr);
        }

        int enterArrayId = a.getResourceId(R.styleable.HorizontalSpinnerView_enter, -1);
        String[] enter;
        if (enterArrayId > 0) {
            enter = getResources().getStringArray(enterArrayId);
        } else {
            enter = new String[]{};
        }

        a.recycle();
        enters.addAll(Arrays.asList(enter));
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_dropdown_layout, enters);
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_layout);
        spinner.setAdapter(spinnerAadapter);
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(listener);
    }

    public String getUserSelectedStr() {
        return (String)spinner.getSelectedItem();
    }

    public void putUserSelectedStr(String str) {
        if (enters.indexOf(str) >= 0) {
            spinner.setSelection(enters.indexOf(str));
        }
    }

    public void setDefaultSelectedPosition(int position) {
        spinner.setSelection(position);
    }
}
