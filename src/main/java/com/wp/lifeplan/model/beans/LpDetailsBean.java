package com.wp.lifeplan.model.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.wp.lifeplan.model.columns.LpDetailsColumns;

import java.util.Date;

import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class LpDetailsBean implements LpDetailsColumns, Parcelable {
    private long generatePlanData;
    private String idea;
    private String level;
    private String plan;
    private String status;
    private String target;
    private String nextStep;
    private long scheduledTime;
    private String longitude;
    private String latitude;
    private String address;
    private String finalLongitude;
    private String finalLatitude;
    private String finalAddress;
    private String uuid;

    public LpDetailsBean() {
    }

    public LpDetailsBean(String json) {
        LpDetailsBean tmp = new Gson().fromJson(json, LpDetailsBean.class);
        copy(tmp);
    }

    public void copy(LpDetailsBean lpDetailsBean) {
        if (lpDetailsBean == null) {
            return;
        }
        this.generatePlanData = lpDetailsBean.getGeneratePlanData();
        this.idea = lpDetailsBean.getIdea();
        this.level = lpDetailsBean.getLevel();
        this.plan = lpDetailsBean.getPlan();
        this.status = lpDetailsBean.getStatus();
        this.target = lpDetailsBean.getTarget();
        this.nextStep = lpDetailsBean.getNextStep();
        this.scheduledTime = lpDetailsBean.getScheduledTime();
        this.longitude = lpDetailsBean.getLongitude();
        this.latitude = lpDetailsBean.getLatitude();
        this.address = lpDetailsBean.getAddress();
        this.finalAddress = lpDetailsBean.getFinalAddress();
        this.finalLatitude = lpDetailsBean.getFinalLatitude();
        this.finalLongitude = lpDetailsBean.getFinalLongitude();
        this.uuid = lpDetailsBean.getUuid();
    }

    public LpDetailsBean(Cursor cursor) {
        this.generatePlanData = cursor.getLong(cursor.getColumnIndex(GENERATE_PLAN_DATA));
        this.idea = cursor.getString(cursor.getColumnIndex(IDEA));
        this.level = cursor.getString(cursor.getColumnIndex(LEVEL));
        this.plan = cursor.getString(cursor.getColumnIndex(PLAN));
        this.status = cursor.getString(cursor.getColumnIndex(STATUS));
        this.target = cursor.getString(cursor.getColumnIndex(TARGET));
        this.nextStep = cursor.getString(cursor.getColumnIndex(NEXT_STEP));
        this.scheduledTime = cursor.getLong(cursor.getColumnIndex(SCHEDULED_TIME));
        this.longitude = cursor.getString(cursor.getColumnIndex(LONGITUDE));
        this.latitude = cursor.getString(cursor.getColumnIndex(LATITUDE));
        this.address = cursor.getString(cursor.getColumnIndex(ADDRESS));
        this.finalAddress = cursor.getString(cursor.getColumnIndex(FINAL_ADDRESS));
        this.finalLongitude = cursor.getString(cursor.getColumnIndex(FINAL_LONGITUDE));
        this.finalLatitude = cursor.getString(cursor.getColumnIndex(FINAL_LATITUDE));
        this.uuid = cursor.getString(cursor.getColumnIndex(UUID));
    }

    public LpDetailsBean(String uuid, long generatePlanData, String idea, String level, String plan,
                         String status, String target, String nextStep, long scheduledTime) {
        this.uuid = uuid;
        this.generatePlanData = generatePlanData;
        this.idea = idea;
        this.level = level;
        this.plan = plan;
        this.status = status;
        this.target = target;
        this.nextStep = nextStep;
        this.scheduledTime = scheduledTime;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(GENERATE_PLAN_DATA, this.generatePlanData);
        values.put(IDEA, this.idea);
        values.put(LEVEL, this.level);
        values.put(PLAN, this.plan);
        values.put(STATUS, this.status);
        values.put(TARGET, this.target);
        values.put(NEXT_STEP, this.nextStep);
        values.put(SCHEDULED_TIME, this.scheduledTime);
        values.put(LATITUDE, this.latitude);
        values.put(LONGITUDE, this.longitude);
        values.put(ADDRESS, this.address);
        values.put(FINAL_ADDRESS, this.finalAddress);
        values.put(FINAL_LATITUDE, this.finalLatitude);
        values.put(FINAL_LONGITUDE, this.finalLongitude);
        values.put(UUID, this.uuid);
        return values;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFinalLongitude() {
        return finalLongitude;
    }

    public void setFinalLongitude(String finalLongitude) {
        this.finalLongitude = finalLongitude;
    }

    public String getFinalLatitude() {
        return finalLatitude;
    }

    public void setFinalLatitude(String finalLatitude) {
        this.finalLatitude = finalLatitude;
    }

    public String getFinalAddress() {
        return finalAddress;
    }

    public void setFinalAddress(String finalAddress) {
        this.finalAddress = finalAddress;
    }

    public long getGeneratePlanData() {
        return generatePlanData;
    }

    public void setGeneratePlanData(long generatePlanData) {
        this.generatePlanData = generatePlanData;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "LpDetailsBean{" +
                "generatePlanData=" + SDF_LOG.format(new Date(generatePlanData)) +
                ", idea='" + idea + '\'' +
                ", level='" + level + '\'' +
                ", plan='" + plan + '\'' +
                ", status='" + status + '\'' +
                ", target='" + target + '\'' +
                ", nextStep='" + nextStep + '\'' +
                ", scheduledTime=" + SDF_LOG.format(new Date(scheduledTime)) +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", finalLongitude='" + finalLongitude + '\'' +
                ", finalLatitude='" + finalLatitude + '\'' +
                ", finalAddress='" + finalAddress + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LpDetailsBean that = (LpDetailsBean) o;

        if (generatePlanData != that.generatePlanData) return false;
        if (scheduledTime != that.scheduledTime) return false;
        if (idea != null ? !idea.equals(that.idea) : that.idea != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (plan != null ? !plan.equals(that.plan) : that.plan != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (nextStep != null ? !nextStep.equals(that.nextStep) : that.nextStep != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (finalLongitude != null ? !finalLongitude.equals(that.finalLongitude) : that.finalLongitude != null)
            return false;
        if (finalLatitude != null ? !finalLatitude.equals(that.finalLatitude) : that.finalLatitude != null)
            return false;
        if (finalAddress != null ? !finalAddress.equals(that.finalAddress) : that.finalAddress != null)
            return false;
        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (generatePlanData ^ (generatePlanData >>> 32));
        result = 31 * result + (idea != null ? idea.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (nextStep != null ? nextStep.hashCode() : 0);
        result = 31 * result + (int) (scheduledTime ^ (scheduledTime >>> 32));
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (finalLongitude != null ? finalLongitude.hashCode() : 0);
        result = 31 * result + (finalLatitude != null ? finalLatitude.hashCode() : 0);
        result = 31 * result + (finalAddress != null ? finalAddress.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.generatePlanData);
        dest.writeString(this.idea);
        dest.writeString(this.level);
        dest.writeString(this.plan);
        dest.writeString(this.status);
        dest.writeString(this.target);
        dest.writeString(this.nextStep);
        dest.writeLong(this.scheduledTime);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.address);
        dest.writeString(this.finalLongitude);
        dest.writeString(this.finalLatitude);
        dest.writeString(this.finalAddress);
        dest.writeString(this.uuid);
    }

    protected LpDetailsBean(Parcel in) {
        this.generatePlanData = in.readLong();
        this.idea = in.readString();
        this.level = in.readString();
        this.plan = in.readString();
        this.status = in.readString();
        this.target = in.readString();
        this.nextStep = in.readString();
        this.scheduledTime = in.readLong();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.address = in.readString();
        this.finalLongitude = in.readString();
        this.finalLatitude = in.readString();
        this.finalAddress = in.readString();
        this.uuid = in.readString();
    }

    public static final Creator<LpDetailsBean> CREATOR = new Creator<LpDetailsBean>() {
        @Override
        public LpDetailsBean createFromParcel(Parcel source) {
            return new LpDetailsBean(source);
        }

        @Override
        public LpDetailsBean[] newArray(int size) {
            return new LpDetailsBean[size];
        }
    };
}
