package com.wp.lifeplan.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wp.lifeplan.model.columns.LpDetailsColumns;

import java.util.ArrayList;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class LpDatabase extends SQLiteOpenHelper {
    private final static String TAG = "LpDatabase";
    private final static String DB_NAME = "lifeplan";
    private final static int DB_VERSION = 1;
    private final ArrayList<LpChangedObserver> observers = new ArrayList<LpChangedObserver>();

    public void regsiterObserver(LpChangedObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void notifyDatabaseChange() {
        for (LpChangedObserver observer :  observers) {
            observer.onLpChanged();
        }
    }

    public LpDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDetailsLifePlanTable(db);
    }

    private void createDetailsLifePlanTable(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder(String.format("CREATE TABLE %s",
                LpDetailsColumns.TABLE_NAME));
        sb.append("(");
        sb.append(String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT,", LpDetailsColumns._ID));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.UUID));
        sb.append(String.format("%s LONG,", LpDetailsColumns.GENERATE_PLAN_DATA));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.IDEA));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.LEVEL));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.PLAN));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.STATUS));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.TARGET));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.NEXT_STEP));
        sb.append(String.format("%s LONG,", LpDetailsColumns.SCHEDULED_TIME));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.LATITUDE));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.LONGITUDE));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.ADDRESS));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.FINAL_ADDRESS));
        sb.append(String.format("%s TEXT,", LpDetailsColumns.FINAL_LATITUDE));
        sb.append(String.format("%s TEXT", LpDetailsColumns.FINAL_LONGITUDE));
        sb.append(")");
        String sql = sb.toString();
        Log.d(TAG, "createGeneralFileListTable: " +
                "check general_file_list_table sql: " + sql);
        db.execSQL("DROP TABLE IF EXISTS " + LpDetailsColumns.TABLE_NAME);
        db.execSQL(sql);
    }

    private synchronized SQLiteDatabase getDatabase() {
        return getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
