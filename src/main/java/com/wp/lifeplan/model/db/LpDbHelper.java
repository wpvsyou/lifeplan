package com.wp.lifeplan.model.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wp.lifeplan.LpApplication;
import com.wp.lifeplan.model.beans.LpDetailsBean;
import com.wp.lifeplan.model.columns.LpDetailsColumns;

import java.util.ArrayList;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class LpDbHelper {
    private final static String TAG = "LpDbHelper";
    private SQLiteDatabase getDatabase() {
        return LpApplication.getInstance().getDatabase().getWritableDatabase();
    }

    public boolean deleteLp(String uuid) {
        return getDatabase().delete(
                LpDetailsColumns.TABLE_NAME,
                String.format("%s=?", LpDetailsColumns.UUID),
                new String[]{uuid}
        ) > 0;
    }

    public boolean insertLpDetails(LpDetailsBean lpDetailsBean) {
        boolean b = getDatabase().update(
                LpDetailsColumns.TABLE_NAME,
                lpDetailsBean.toContentValues(),
                String.format("%s=?", LpDetailsColumns.UUID),
                new String[]{lpDetailsBean.getUuid()}) > 0;
        if (!b) {
            Log.i(TAG, "insert new Lpb" + lpDetailsBean);
            b = getDatabase().insert(LpDetailsColumns.TABLE_NAME,
                    null, lpDetailsBean.toContentValues()) > 0;
        } else {
            Log.i(TAG, "update success!" + lpDetailsBean);
        }
        if (b) {
            Log.i(TAG, "notify changed!");
            LpApplication.getInstance().getDatabase().notifyDatabaseChange();
        }
        return b;
    }

    public ArrayList<LpDetailsBean> queryAllLp() {
        ArrayList<LpDetailsBean> result = new ArrayList<LpDetailsBean>();
        Cursor cursor = LpApplication.getInstance().getDatabase().getReadableDatabase().
                query(LpDetailsColumns.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(new LpDetailsBean(cursor));
            }
            cursor.close();
        }
        return result;
    }
}
