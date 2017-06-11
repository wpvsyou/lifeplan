package com.wp.lifeplan.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wp.lifeplan.R;
import com.wp.lifeplan.model.beans.LpDetailsBean;

/**
 * Created by wangpeng on 2017/5/21.
 */

public class DetailActivity extends AppCompatActivity {
    private final static String ARGS_LP = "args_lp";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);
        handleIntent(getIntent());

    }

    private void handleIntent(Intent intent) {
        LpDetailsBean lpDetailsBean = null;
        if (intent != null) {
            lpDetailsBean = intent.getParcelableExtra(ARGS_LP);
        }

        LifePlanDetailsFrg f = lpDetailsBean != null ?
                LifePlanDetailsFrg.newInstance(lpDetailsBean) : LifePlanDetailsFrg.newInstance();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, f, this.getClass().getName());
        ft.commit();
    }

    public static void startShowLpDetail(Context context, LpDetailsBean lpDetailsBean) {
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(ARGS_LP, lpDetailsBean);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void startAddLifePlan(Context context) {
        Intent i = new Intent(context, DetailActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
