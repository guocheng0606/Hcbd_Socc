package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/4/21.
 */

public class DeviceEditInfo implements Serializable{

    private DataInfo dataInfo;
    private List<Device> deviceList;
    private List<Type> typeList;
    private List<Project> projectList;

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public static class DataInfo implements Serializable{

        /**
         * isRef : 1
         * modelContent : ["设备信息","/socc/deviceAction!"]
         * port : 7003
         * project : {"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"}
         * snNo : B0003
         * soluTime : 3600
         * type : {"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null}
         * unit : null
         * upCode :
         * x : 0.0
         * y : 0.0
         * z : 0.0
         */

        private String isRef;
        private int port;
        private ProjectBean project;
        private String snNo;
        private String soluTime;
        private TypeBean type;
        private Object unit;
        private String upCode;
        private double x;
        private double y;
        private double z;
        private List<String> modelContent;

        public String getIsRef() {
            return isRef;
        }

        public void setIsRef(String isRef) {
            this.isRef = isRef;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public ProjectBean getProject() {
            return project;
        }

        public void setProject(ProjectBean project) {
            this.project = project;
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

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public String getUpCode() {
            return upCode;
        }

        public void setUpCode(String upCode) {
            this.upCode = upCode;
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

        public static class ProjectBean implements Serializable {
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

        public static class TypeBean implements Serializable {

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

    public static class Device implements Serializable{

        /**
         * code : A00006
         * createTime : 2018-08-08T20:55:36
         * id : 71
         * isRef : 1
         * modelContent : ["设备信息","/socc/deviceAction!"]
         * name : TCJZ0001
         * names : A00006-TCJZ0001
         * operNames : S0006-admin
         * orgCode : 027
         * paramsObj : null
         * port : 7003
         * project : {"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"}
         * remark :
         * snNo : B0003
         * soluTime : 3600
         * state : 1
         * stateContent : 启用
         * type : {"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null}
         * unit : null
         * upCode :
         * upload : null
         * uploadContentType : null
         * uploadFileName : null
         * x : 0.0
         * y : 0.0
         * z : 0.0
         */

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
        private ProjectBean project;
        private String remark;
        private String snNo;
        private String soluTime;
        private String state;
        private String stateContent;
        private TypeBean type;
        private Object unit;
        private String upCode;
        private Object upload;
        private Object uploadContentType;
        private Object uploadFileName;
        private double x;
        private double y;
        private double z;
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

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
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

        public static class ProjectBean implements Serializable {
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

        public static class TypeBean implements Serializable {

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

    public static class Type implements Serializable{

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

    public static class Project implements Serializable{

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

}
