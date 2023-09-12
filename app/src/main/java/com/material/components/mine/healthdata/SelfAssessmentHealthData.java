package com.material.components.mine.healthdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SelfAssessmentHealthData implements Parcelable {
    float sleepScore;
    float emotionScore;
    float appetiteScore;
    float cognitionScore;
    float activityScore;
    float selfWorthScore;
    float lifeAttitudeScore;
    float interpersonalRelationScore;

    Date date;


    public SelfAssessmentHealthData() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getSleepScore() {
        return sleepScore;
    }

    public void setSleepScore(float sleepScore) {
        this.sleepScore = sleepScore;
    }

    public float getEmotionScore() {
        return emotionScore;
    }

    public void setEmotionScore(float emotionScore) {
        this.emotionScore = emotionScore;
    }

    public float getAppetiteScore() {
        return appetiteScore;
    }

    public void setAppetiteScore(float appetiteScore) {
        this.appetiteScore = appetiteScore;
    }

    public float getCognitionScore() {
        return cognitionScore;
    }

    public void setCognitionScore(float cognitionScore) {
        this.cognitionScore = cognitionScore;
    }

    public float getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(float activityScore) {
        this.activityScore = activityScore;
    }

    public float getSelfWorthScore() {
        return selfWorthScore;
    }

    public void setSelfWorthScore(float selfWorthScore) {
        this.selfWorthScore = selfWorthScore;
    }

    public float getLifeAttitudeScore() {
        return lifeAttitudeScore;
    }

    public void setLifeAttitudeScore(float lifeAttitudeScore) {
        this.lifeAttitudeScore = lifeAttitudeScore;
    }

    public float getInterpersonalRelationScore() {
        return interpersonalRelationScore;
    }

    public void setInterpersonalRelationScore(float interpersonalRelationScore) {
        this.interpersonalRelationScore = interpersonalRelationScore;
    }









    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.sleepScore);
        dest.writeFloat(this.emotionScore);
        dest.writeFloat(this.appetiteScore);
        dest.writeFloat(this.cognitionScore);
        dest.writeFloat(this.activityScore);
        dest.writeFloat(this.selfWorthScore);
        dest.writeFloat(this.lifeAttitudeScore);
        dest.writeFloat(this.interpersonalRelationScore);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.sleepScore = source.readFloat();
        this.emotionScore = source.readFloat();
        this.appetiteScore = source.readFloat();
        this.cognitionScore = source.readFloat();
        this.activityScore = source.readFloat();
        this.selfWorthScore = source.readFloat();
        this.lifeAttitudeScore = source.readFloat();
        this.interpersonalRelationScore = source.readFloat();
        long tmpDate = source.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    protected SelfAssessmentHealthData(Parcel in) {
        this.sleepScore = in.readFloat();
        this.emotionScore = in.readFloat();
        this.appetiteScore = in.readFloat();
        this.cognitionScore = in.readFloat();
        this.activityScore = in.readFloat();
        this.selfWorthScore = in.readFloat();
        this.lifeAttitudeScore = in.readFloat();
        this.interpersonalRelationScore = in.readFloat();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<SelfAssessmentHealthData> CREATOR = new Creator<SelfAssessmentHealthData>() {
        @Override
        public SelfAssessmentHealthData createFromParcel(Parcel source) {
            return new SelfAssessmentHealthData(source);
        }

        @Override
        public SelfAssessmentHealthData[] newArray(int size) {
            return new SelfAssessmentHealthData[size];
        }
    };
}
