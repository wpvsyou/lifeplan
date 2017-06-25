package com.wp.lifeplan;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.wp.lifeplan.service.LocationService;

import java.util.Calendar;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class LpApplication extends Application {
    private static LpApplication sInstance;

    public static LpApplication getInstance() {
        return sInstance;
    }

    private LocationService mLocationService;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        FlowManager.init(this);
        mLocationService = new LocationService(this).init();

        Calendar now = Calendar.getInstance();
        int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (hour > 22) {

        } else if (hour > 6) {
            now.set(Calendar.DAY_OF_MONTH, ++dayOfMonth);
            now.set(Calendar.HOUR_OF_DAY, 6);
            long morning = now.getTimeInMillis();

        } else {

        }
    }

    public synchronized LocationService getLocationService() {
        return mLocationService;
    }
}
