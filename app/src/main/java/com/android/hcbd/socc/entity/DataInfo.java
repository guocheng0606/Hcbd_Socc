package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 14525 on 2017/4/27.
 */

public class DataInfo implements Serializable {

    /**
     * beginTime : null
     * code : null
     * createTime : 2017-04-27T15:44:06
     * dataTime : 2017-04-17 08:15
     * device : {"code":"A00002","createTime":"2017-04-21T14:52:40","id":2,"isRef":"0","modelContent":["设备信息","/socc/deviceAction!"],"name":"R001","names":"A00002-R001","operNames":"S0006-admin","orgCode":"027","paramsObj":null,"port":0,"project":{"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"},"remark":"tttt","snNo":"R00001","soluTime":"300","state":"1","stateContent":"启用","type":{"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null},"upCode":"A00001","x":-11.46854637,"y":35.25372451,"z":0.4575979633}
     * dx : 8.2525E-4
     * dy : 6.4779E-4
     * dz : 4.54575E-4
     * endTime : null
     * id : 408
     * modelContent : ["设备数据","/socc/deviceDataAction!"]
     * name : null
     * names : null-null
     * operNames : null
     * orgCode : null
     * paramsObj : null
     * remark : null
     * state : null
     * stateContent : 历史
     * x : -11.4658
     * y : 35.252
     * z : 0.462417
     */

    private Object beginTime;
    private Object code;
    private String createTime;
    private String dataTime;
    private DeviceBean device;
    private double dx;
    private double dy;
    private double dz;
    private Object endTime;
    private int id;
    private Object name;
    private String names;
    private Object operNames;
    private Object orgCode;
    private Object paramsObj;
    private Object remark;
    private Object state;
    private String stateContent;
    private double x;
    private double y;
    private double z;
    private List<String> modelContent;

    public Object getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Object beginTime) {
        this.beginTime = beginTime;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDz() {
        return dz;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Object getOperNames() {
        return operNames;
    }

    public void setOperNames(Object operNames) {
        this.operNames = operNames;
    }

    public Object getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(Object orgCode) {
        this.orgCode = orgCode;
    }

    public Object getParamsObj() {
        return paramsObj;
    }

    public void setParamsObj(Object paramsObj) {
        this.paramsObj = paramsObj;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getStateContent() {
        return stateContent;
    }

    public void setStateContent(String stateContent) {
        this.stateContent = stateContent;
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

    public static class DeviceBean implements Serializable {
        /**
         * code : A00002
         * createTime : 2017-04-21T14:52:40
         * id : 2
         * isRef : 0
         * modelContent : ["设备信息","/socc/deviceAction!"]
         * name : R001
         * names : A00002-R001
         * operNames : S0006-admin
         * orgCode : 027
         * paramsObj : null
         * port : 0
         * project : {"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"}
         * remark : tttt
         * snNo : R00001
         * soluTime : 300
         * state : 1
         * stateContent : 启用
         * type : {"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null}
         * upCode : A00001
         * x : -11.46854637
         * y : 35.25372451
         * z : 0.4575979633
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
        private String upCode;
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
             * remark : 001.png
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
}
