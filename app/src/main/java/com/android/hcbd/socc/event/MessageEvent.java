package com.android.hcbd.socc.event;

/**
 * Created by guocheng on 2017/3/17.
 */
public class MessageEvent {

    /*发出的广播类型*/
    public static final int EVENT_LOCATION_SUCCESS = 100;

    public static final int EVENT_DEVICE_ADD = 101;
    public static final int EVENT_DEVICE_UPDATE = 102;
    public static final int EVENT_DEVICE_DELETE = 103;
    public static final int EVENT_DANGER_DEVICE_SEARCH = 104;
    public static final int EVENT_DANGER_DEVICE_REAL = 105;
    public static final int EVENT_WARN_SEARCH = 106;

    public static final int EVENT_WARN_ADD = 107;
    public static final int EVENT_WARN_UPDATE = 108;
    public static final int EVENT_WARN_DELETE = 109;

    public static final int EVENT_DATA_SEARCH = 110;

    public static final int EVENT_IPADDRESS_DEL = 120;
    public static final int EVENT_LOGINOUT = 121;


    private int eventId;
    private Object obj;
    private Object obj2;
    public MessageEvent() {
    }

    public MessageEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }
}
