package com.wp.lifeplan;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.wp.lifeplan.service.AlertClockService;
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
        AlertClockService.scheduleAlarmClock(this, "一天之计在于晨,来起来撒尿！", Calendar.getInstance(), new AlertClockService.Builder().setHours(6).setMinute(0));
        AlertClockService.scheduleAlarmClock(this, "该睡觉了！", Calendar.getInstance(), new AlertClockService.Builder().setHours(22).setMinute(0));
    }

    public synchronized LocationService getLocationService() {
        return mLocationService;
    }
}
