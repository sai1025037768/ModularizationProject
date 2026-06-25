package com.travel.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 *         2017/3/13
 */
public class TravelDetail implements Parcelable {

    private String travelId;
    private String destinationName;
    private int stayDays;

    public TravelDetail() {
    }

    public TravelDetail(String travelId, String destinationName, int stayDays) {
        this.stayDays = stayDays;
        this.destinationName = destinationName;
        this.travelId = travelId;
    }

    @Override
    public String toString() {
        return " 行程ID：'" + travelId + '\'' +
                ", 目的地：'" + destinationName + '\'' +
                ", 停留天数：" + stayDays;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getStayDays() {
        return stayDays;
    }

    public void setStayDays(int stayDays) {
        this.stayDays = stayDays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.travelId);
        dest.writeString(this.destinationName);
        dest.writeInt(this.stayDays);
    }

    protected TravelDetail(Parcel in) {
        this.travelId = in.readString();
        this.destinationName = in.readString();
        this.stayDays = in.readInt();
    }

    public static final Creator<TravelDetail> CREATOR = new Creator<TravelDetail>() {
        @Override
        public TravelDetail createFromParcel(Parcel source) {
            return new TravelDetail(source);
        }

        @Override
        public TravelDetail[] newArray(int size) {
            return new TravelDetail[size];
        }
    };
}
