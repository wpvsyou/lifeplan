package com.wp.lifeplan.ui;

import android.view.View;

import com.wp.lifeplan.model.LifePlan;

/**
 * Created by wangpeng on 2017/5/21.
 */

public interface OnItemClickListener {
    void onItemClick(LifePlan lpDetailsBean);

    void onItemLongClick(View view, int position);
}
