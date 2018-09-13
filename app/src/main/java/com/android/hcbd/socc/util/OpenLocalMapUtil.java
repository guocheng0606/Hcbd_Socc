package com.android.hcbd.socc.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by chenliu on 2016/10/18.<br/>
 * 描述：打开手机已安装地图相关工具类
 * </br>
 */
public class OpenLocalMapUtil {

    /**
     * 地图应用是否安装
     * @return
     */
    public static boolean isGdMapInstalled(){
        return isInstallPackage("com.autonavi.minimap");
    }

    public static boolean isBaiduMapInstalled(){
        return isInstallPackage("com.baidu.BaiduMap");
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 百度地图定位经纬度转高德经纬度
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    //************************************************************************
    //*************************    百度专区     *******************************
    //************************************************************************

    /**
     * 调起百度客户端 自定义打点
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param title
     * @param content
     */
    public static void openBaiduMarkerMap(Context activity, String longitude, String latitude,
                                          String title, String content) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/marker?location=" + latitude + "," +
                        longitude + "&title=" + title + "&content=" + content + "&traffic=on"));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 展示地图
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 范围参数
     * lat,lng,lat,lng (先纬度，后经度, 先左下,后右上)
     *
     * @param activity
     */
    public static void openBaiduCenterMap(Context activity, String cLongitude, String cLatitude,
                                          String zoom, boolean traffic, String lLatitude, String lLongitude, String rLatitude, String rLongitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/show?center=" + cLatitude + "," +
                        cLongitude + "&zoom=" + zoom + "&traffic=" + (traffic ? "on" : "off") +
                        "&bounds=" + lLatitude + "," + lLongitude + "," + rLatitude + "," + rLongitude));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 驾车导航
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     */
    public static void openBaiduNaviMap(Context activity, String longitude, String latitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/navi?location=" + latitude + "," + longitude));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 路径规划
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * lat,lng,lat,lng (先纬度，后经度, 先左下,后右上)
     *
     * @param activity
     */
    public static void openBaiduiDrectionMap(Context activity, String sLongitude, String sLatitude, String sName,
                                             String dLongitude, String dLatitude, String dName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/direction?origin=name:" +
                        sName + "|latlng:" + sLatitude + "," + sLongitude + "&destination=name:" +
                        dName + "|latlng:" + dLatitude + "," + dLongitude + "&" +
                        "mode=transit&sy=0&index=0&target=0"));
        activity.startActivity(intent);
    }


    //************************************************************************
    //*************************    高德专区     *******************************
    //************************************************************************
    /**
     * 调起高德客户端 展示标注点
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 根据名称或经纬度，启动高德地图产品展示一个标注点，如分享位置，标注店铺。支持版本V4.1.3起。
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param appName
     * @param poiname
     */
    public static void openGaodeMarkerMap(Context activity, String longitude, String latitude,
                                          String appName, String poiname) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://viewMap?sourceApplication=" +
                        appName + "&poiname=" + poiname + "&lat=" + latitude + "&lon=" + longitude + "&dev=1"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 路径规划
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 输入起点和终点，搜索公交、驾车或步行的线路。支持版本 V4.2.1 起。
     *
     * @param activity
     * @param sLongitude
     * @param sLatitude
     * @param sName
     * @param dLongitude
     * @param dLatitude
     * @param dName
     * @param appName
     */
    public static void openGaodeRouteMap(Context activity, String sLongitude, String sLatitude, String sName,
                                         String dLongitude, String dLatitude, String dName, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("amapuri://route/plan/?sourceApplication=" + appName +
                        "&sid=&slat=" + sLatitude + "&slon=" +
                        sLongitude + "&sname=" + sName + "&did=&dlat=" +
                        dLatitude + "&dlon=" + dLongitude + "&dname=" + dName + "&dev=1&t=1"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 我的位置
     * 显示我当前的位置。支持版本V4.2.1 起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeMyLocationMap(Context activity, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://myLocation?sourceApplication=" + appName));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 导航
     * 输入终点，以用户当前位置为起点开始路线导航，提示用户每段行驶路线以到达目的地。支持版本V4.1.3 起。
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeNaviMap(Context activity, String appName, String poiname,
                                        String latitude, String longitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://navi?sourceApplication=" + appName + "&poiname=" +
                        poiname + "&lat=" + latitude + "&lon=" + longitude + "&dev=1&style=2"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 公交线路查询
     * 输入公交线路名称，如 445，搜索该条线路经过的所有公交站点。支持版本 v4.2.1 起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeBusMap(Context activity, String appName, String busName,
                                       String city) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://bus?sourceApplication=" + appName + "&busname=" + busName + "&city=" + city));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 地图主图
     * 进入高德地图主图页面。支持版本V4.2.1起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeRootmapMap(Context activity, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://rootmap?sourceApplication=" + appName));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    //************************************************************************
    //*************************    网页专区     *******************************
    //************************************************************************

    /**
     * 打开网页版 反向地址解析
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param appName
     * @param useOutWeb 是否调用外部的浏览器 默认不调用
     */
    public static void openBrosserMarkerMap(Context activity, String longitude, String latitude,
                                            String appName, String title, String content, boolean useOutWeb) {
        if ( useOutWeb ) {
            Uri mapUri = Uri.parse("http://api.map.baidu.com/marker?location=" + latitude + "," + longitude +
                    "&title=" + title + "&content=" + content + "&output=html&src=" + appName);
            Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
            activity.startActivity(loction);
        }
    }



}
