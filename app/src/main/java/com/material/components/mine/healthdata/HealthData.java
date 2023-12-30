package com.material.components.mine.healthdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HealthData implements Parcelable {

    long stepCount;

    long heartRate;

    long distance;

    Date date;


    public HealthData() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getStepCount() {
        return stepCount;
    }

    public void setStepCount(long stepCount) {
        this.stepCount = stepCount;
    }

    public long getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(long heartRate) {
        this.heartRate = heartRate;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.stepCount);
        dest.writeLong(this.heartRate);
        dest.writeLong(this.distance);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.stepCount = source.readLong();
        this.heartRate = source.readLong();
        this.distance = source.readLong();
        long tmpDate = source.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    protected HealthData(Parcel in) {
        this.stepCount = in.readLong();
        this.heartRate = in.readLong();
        this.distance = in.readLong();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<HealthData> CREATOR = new Creator<HealthData>() {
        @Override
        public HealthData createFromParcel(Parcel source) {
            return new HealthData(source);
        }

        @Override
        public HealthData[] newArray(int size) {
            return new HealthData[size];
        }
    };
}
