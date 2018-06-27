package com.android.hcbd.socc.entity;

import java.io.Serializable;

/**
 * Created by guocheng on 2017/4/24.
 */

public class WarnSearchInfo implements Serializable {
    private String deviceName;
    private String warnName;
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

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }
}
