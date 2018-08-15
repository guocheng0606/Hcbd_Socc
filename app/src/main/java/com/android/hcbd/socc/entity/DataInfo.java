package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 14525 on 2017/4/27.
 */

public class DataInfo implements Serializable {


    /**
     * beginTime : 2018-08-13 00:00
     * code : null
     * createTime : 2018-08-13T10:39:02
     * d0 : 0.04657
     * d1 : -12.59020
     * d2 : -23.88510
     * d3 : -37.94870
     * d4 : 42.45490
     * d5 : -64.43080
     * d6 : 1.00315
     * dataTime : 2018-08-13 09:00
     * device : {"code":"A00007","createTime":"2018-08-08T20:55:51","id":72,"isRef":"0","modelContent":["设备信息","/socc/deviceAction!"],"name":"TCJC001","names":"A00007-TCJC001","operNames":"S0006-admin","orgCode":"027","paramsObj":null,"port":7503,"project":{"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"},"remark":"","snNo":"R0003","soluTime":"3600","state":"1","stateContent":"启用","type":{"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null},"unit":null,"upCode":"A00006","upload":null,"uploadContentType":null,"uploadFileName":null,"x":0,"y":0,"z":0}
     * dx : -0.0125902
     * dy : -0.0238851
     * dz : -0.0379487
     * endTime : 2018-08-13 23:59
     * id : 8605
     * modelContent : ["设备数据","/socc/deviceDataAction!"]
     * name : null
     * names : null-null
     * operNames : null
     * orgCode : null
     * paramsObj : null
     * ratio : null
     * remark : null
     * state : null
     * stateContent : 历史
     * x : 42.4549
     * y : -64.4308
     * z : 1.00315
     */

    private String beginTime;
    private Object code;
    private String createTime;
    private String d0;
    private String d1;
    private String d2;
    private String d3;
    private String d4;
    private String d5;
    private String d6;
    private String dataTime;
    private DeviceBean device;
    private double dx;
    private double dy;
    private double dz;
    private String endTime;
    private int id;
    private Object name;
    private String names;
    private Object operNames;
    private Object orgCode;
    private Object paramsObj;
    private Object ratio;
    private Object remark;
    private Object state;
    private String stateContent;
    private double x;
    private double y;
    private double z;
    private List<String> modelContent;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
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

    public String getD0() {
        return d0;
    }

    public void setD0(String d0) {
        this.d0 = d0;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public Object getRatio() {
        return ratio;
    }

    public void setRatio(Object ratio) {
        this.ratio = ratio;
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
         * code : A00007
         * createTime : 2018-08-08T20:55:51
         * id : 72
         * isRef : 0
         * modelContent : ["设备信息","/socc/deviceAction!"]
         * name : TCJC001
         * names : A00007-TCJC001
         * operNames : S0006-admin
         * orgCode : 027
         * paramsObj : null
         * port : 7503
         * project : {"addr":null,"code":"001","createTime":"2017-03-16T17:29:57","id":1,"modelContent":["工程项目","/socc/projectAction!"],"name":"华创工程部","names":"001-华创工程部","operNames":"S0000-Eingabe","orgCode":"027","owner":null,"paramsObj":null,"remark":"","state":"1","stateContent":"启用"}
         * remark :
         * snNo : R0003
         * soluTime : 3600
         * state : 1
         * stateContent : 启用
         * type : {"code":"001","createTime":"2017-03-16T17:39:22","id":1,"modelContent":["分析代码数据","/nsp/typeAction!"],"name":"GNSS","names":"001-GNSS","operNames":"S0000-Eingabe","orgCode":"027","paramsObj":null,"remark":"\\upload\\deviceType\\001.png","state":"1","stateContent":"启用","type":"A001","upload":null,"uploadContentType":null,"uploadFileName":null}
         * unit : null
         * upCode : A00006
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
}
