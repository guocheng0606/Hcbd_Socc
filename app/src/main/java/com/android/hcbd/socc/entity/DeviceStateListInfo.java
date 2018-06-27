package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/4/19.
 */

public class DeviceStateListInfo implements Serializable {
    private String indexRate; //刷新频率
    private String danger; //危险个数
    private String connect; //连接个数
    private String disconnect;  //断开个数
    private List<DataInfo> dataInfoList;

    public String getIndexRate() {
        return indexRate;
    }

    public void setIndexRate(String indexRate) {
        this.indexRate = indexRate;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(String disconnect) {
        this.disconnect = disconnect;
    }

    public List<DataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<DataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }

    public static class DataInfo implements Serializable{

        /**
         * img : 001.png
         * name : B001
         * state : 断开
         * type : GNSS
         * x : 114.348802
         * y : 30.510488
         */

        private String img;
        private String name;
        private String state;
        private String type;
        private double x;
        private double y;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

    }

}
