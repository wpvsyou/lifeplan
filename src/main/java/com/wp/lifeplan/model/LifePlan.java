package com.wp.lifeplan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG;

/**
 * Created by wangpeng on 2017/5/20.
 */
@Table(database = LifePlanDatabase.class)
public class LifePlan extends BaseModel implements Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column
    public long id;
    @Column
    public long generatePlanData;
    @Column
    public String idea;
    @Column
    public String level;
    @Column
    public String plan;
    @Column
    public String status;
    @Column
    public String target;
    @Column
    public String nextStep;
    @Column
    public long scheduledTime;
    @Column
    public String longitude;
    @Column
    public String latitude;
    @Column
    public String address;
    @Column
    public String finalLongitude;
    @Column
    public String finalLatitude;
    @Column
    public String finalAddress;
    @Column
    public String uuid;

    public LifePlan() {
    }

    public LifePlan(String json) {
        LifePlan tmp = new Gson().fromJson(json, LifePlan.class);
        copy(tmp);
    }

    public void copy(LifePlan lpDetailsBean) {
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

    public LifePlan(String uuid, long generatePlanData, String idea, String level, String plan,
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

        LifePlan that = (LifePlan) o;

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

    protected LifePlan(Parcel in) {
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

    public static final Creator<LifePlan> CREATOR = new Creator<LifePlan>() {
        @Override
        public LifePlan createFromParcel(Parcel source) {
            return new LifePlan(source);
        }

        @Override
        public LifePlan[] newArray(int size) {
            return new LifePlan[size];
        }
    };
}
