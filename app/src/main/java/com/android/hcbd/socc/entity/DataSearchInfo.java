package com.android.hcbd.socc.entity;

import java.io.Serializable;

/**
 * Created by 14525 on 2017/4/27.
 */

public class DataSearchInfo implements Serializable {
    private String deviceName;
    private String beginTime;
    private String endTime;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
