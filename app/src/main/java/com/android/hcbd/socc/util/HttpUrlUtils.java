package com.android.hcbd.socc.util;

/**
 * Created by guocheng on 2017/4/18.
 */

public class HttpUrlUtils {
    //public static final String BASEURL = "http://112.124.108.24:8688/socc";
    public static final String login_url = "/soccApp/appLoginAction!login.action"; //登录
    public static final String device_state_list_url = "/soccApp/appDeviceAction!deviceList.action"; //设备状态列表
    public static final String query_device_url = "/soccApp/appDeviceAction!list.action"; //查询设备
    public static final String device_toedit_url = "/soccApp/appDeviceAction!toEdit.action"; //设备准备编辑列表
    public static final String device_edit_url = "/soccApp/appDeviceAction!edit.action"; //设备编辑
    public static final String device_delete_url = "/soccApp/appDeviceAction!delete.action"; //设备删除
    public static final String solusoft_manager_url = "/soccApp/appSoluSoftAction!mangerServer.action"; //关闭和开启解算软件
    public static final String warn_data_list_url = "/soccApp/appWarnDataAction!list.action"; //告警数据列表
    public static final String warn_data_edit_url = "/soccApp/appWarnDataAction!edit.action"; //告警数据编辑
    public static final String warn_list_url = "/soccApp/appWarnAction!list.action"; //预警列表
    public static final String warn_toedit_url = "/soccApp/appWarnAction!toEdit.action"; //预警准备编辑
    public static final String warn_edit_url = "/soccApp/appWarnAction!edit.action"; //预警编辑
    public static final String warn_delete_url = "/soccApp/appWarnAction!delete.action"; //预警删除
    public static final String indexRate_url = "/soccApp/appSoluSoftAction!indexRate.action"; //设置刷新频率
    public static final String device_data_url = "/soccApp/appDeviceDataAction!list.action"; //数据

}
