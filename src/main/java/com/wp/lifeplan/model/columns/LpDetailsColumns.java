package com.wp.lifeplan.model.columns;

import android.provider.BaseColumns;

/**
 * Created by wangpeng on 2017/5/20.
 */

public interface LpDetailsColumns extends BaseColumns {
    String TABLE_NAME = "details_life_plan";
    String UUID = "uuid";
    String GENERATE_PLAN_DATA = "generate_plan_data";
    String IDEA = "idea";
    String LEVEL = "level";
    String PLAN = "plan";
    String STATUS = "status";
    String TARGET = "target";
    String NEXT_STEP = "next_step";
    String SCHEDULED_TIME = "scheduled_time";
    String LONGITUDE = "longitude";
    String LATITUDE = "latitude";
    String ADDRESS = "address";
    String FINAL_LONGITUDE = "final_longitude";
    String FINAL_LATITUDE = "final_latitude";
    String FINAL_ADDRESS = "final_address";
}
