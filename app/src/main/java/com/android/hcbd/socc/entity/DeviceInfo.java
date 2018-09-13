package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/4/20.
 */

public class DeviceInfo implements Serializable {

    /**
     * bdPosition : 115.13035738259,30.200276967478
     * code : A00014
     * createTime : 2018-08-09T10:39:46
     * id : 59
     * isRef : 0
     * modelContent : ["设备信息","/socc/deviceAction!"]
     * name : R008(板岩山监测站1)
     * names : A00014-R008(板岩山监测站1)
     * operNames : S0000-Eingabe
     * orgCode : 027
     * paramsObj : null
     * port : 5771
     * position : 8.90003_-360.464_198.403
     * project : {"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"}
     * remark :
     * snNo : R00008
     * soluTime : 7200
     * state : 1
     * stateContent : 启用
     * type : {"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null}
     * unit : mm
     * upCode : A00006
     * upload : null
     * uploadContentType : null
     * uploadFileName : null
     * x : 115.11832988656056
     * y : 30.19686279774174
     * z : 17214.326507511458
     */

    private String bdPosition;
    private String code;
    private String createTime;
    private int id;
    private String isRef;
    private String name;
    private String names;
    private String operNames;
    private String orgCode;
    private Object paramsObj;
    private int port;
    private String position;
    private ProjectBean project;
    private String remark;
    private String snNo;
    private String soluTime;
    private String state;
    private String stateContent;
    private TypeBean type;
    private String unit;
    private String upCode;
    private Object upload;
    private Object uploadContentType;
    private Object uploadFileName;
    private double x;
    private double y;
    private double z;
    private List<String> modelContent;

    public String getBdPosition() {
        return bdPosition;
    }

    public void setBdPosition(String bdPosition) {
        this.bdPosition = bdPosition;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsRef() {
        return isRef;
    }

    public void setIsRef(String isRef) {
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getOperNames() {
        return operNames;
    }

    public void setOperNames(String operNames) {
        this.operNames = operNames;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Object getParamsObj() {
        return paramsObj;
    }

    public void setParamsObj(Object paramsObj) {
        this.paramsObj = paramsObj;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getSoluTime() {
        return soluTime;
    }

    public void setSoluTime(String soluTime) {
        this.soluTime = soluTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateContent() {
        return stateContent;
    }

    public void setStateContent(String stateContent) {
        this.stateContent = stateContent;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUpCode() {
        return upCode;
    }

    public void setUpCode(String upCode) {
        this.upCode = upCode;
    }

    public Object getUpload() {
        return upload;
    }

    public void setUpload(Object upload) {
        this.upload = upload;
    }

    public Object getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(Object uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public Object getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(Object uploadFileName) {
        this.uploadFileName = uploadFileName;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public List<String> getModelContent() {
        return modelContent;
    }

    public void setModelContent(List<String> modelContent) {
        this.modelContent = modelContent;
    }

    public static class ProjectBean implements Serializable{
        /**
         * addr : null
         * code : 001
         * createTime : 2017-03-16T17:29:57
         * id : 1
         * modelContent : ["工程项目","/socc/projectAction!"]
         * name : 华创工程部
         * names : 001-华创工程部
         * operNames : S0000-Eingabe
         * orgCode : 027
         * owner : null
         * paramsObj : null
         * remark :
         * state : 1
         * stateContent : 启用
         */

        private Object addr;
        private String code;
        private String createTime;
        private int id;
        private String name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object owner;
        private Object paramsObj;
        private String remark;
        private String state;
        private String stateContent;
        private List<String> modelContent;

        public Object getAddr() {
            return addr;
        }

        public void setAddr(Object addr) {
            this.addr = addr;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getOperNames() {
            return operNames;
        }

        public void setOperNames(String operNames) {
            this.operNames = operNames;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public Object getOwner() {
            return owner;
        }

        public void setOwner(Object owner) {
            this.owner = owner;
        }

        public Object getParamsObj() {
            return paramsObj;
        }

        public void setParamsObj(Object paramsObj) {
            this.paramsObj = paramsObj;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateContent() {
            return stateContent;
        }

        public void setStateContent(String stateContent) {
            this.stateContent = stateContent;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }
    }

    public static class TypeBean implements Serializable{
        /**
         * code : 001
         * createTime : 2017-03-16T17:39:22
         * id : 1
         * modelContent : ["分析代码数据","/nsp/typeAction!"]
         * name : GNSS
         * names : 001-GNSS
         * operNames : S0000-Eingabe
         * orgCode : 027
         * paramsObj : null
         * remark :
         * state : 1
         * stateContent : 启用
         * type : A001
         * upload : null
         * uploadContentType : null
         * uploadFileName : null
         */

        private String code;
        private String createTime;
        private int id;
        private String name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object paramsObj;
        private String remark;
        private String state;
        private String stateContent;
        private String type;
        private Object upload;
        private Object uploadContentType;
        private Object uploadFileName;
        private List<String> modelContent;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getOperNames() {
            return operNames;
        }

        public void setOperNames(String operNames) {
            this.operNames = operNames;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public Object getParamsObj() {
            return paramsObj;
        }

        public void setParamsObj(Object paramsObj) {
            this.paramsObj = paramsObj;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateContent() {
            return stateContent;
        }

        public void setStateContent(String stateContent) {
            this.stateContent = stateContent;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUpload() {
            return upload;
        }

        public void setUpload(Object upload) {
            this.upload = upload;
        }

        public Object getUploadContentType() {
            return uploadContentType;
        }

        public void setUploadContentType(Object uploadContentType) {
            this.uploadContentType = uploadContentType;
        }

        public Object getUploadFileName() {
            return uploadFileName;
        }

        public void setUploadFileName(Object uploadFileName) {
            this.uploadFileName = uploadFileName;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }
    }
}
