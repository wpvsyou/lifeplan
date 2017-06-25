package com.wp.lifeplan.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by wangpeng on 2017/6/11.
 */
@Database(name = LifePlanDatabase.NAME, version = LifePlanDatabase.VERSION)
public class LifePlanDatabase {
    public static final String NAME = "LifePlanDatabase";

    public static final int VERSION = 1;
}
