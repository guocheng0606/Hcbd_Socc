package com.android.hcbd.socc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/4/24.
 */

public class WarnDataInfo implements Serializable {

    private Object beginTime;
    private Object code;
    private String createTime;
    private String dealTime;
    private Object endTime;
    private String fullTime;
    private int id;
    private Object name;
    private String names;
    private String operNames;
    private Object orgCode;
    private Object paramsObj;
    private String remark;
    private String state;
    private String stateContent;
    private double v1;
    private double v2;
    private double v3;
    private WarnBean warn;
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

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
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

    public String getOperNames() {
        return operNames;
    }

    public void setOperNames(String operNames) {
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

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }

    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    public double getV3() {
        return v3;
    }

    public void setV3(double v3) {
        this.v3 = v3;
    }

    public WarnBean getWarn() {
        return warn;
    }

    public void setWarn(WarnBean warn) {
        this.warn = warn;
    }

    public List<String> getModelContent() {
        return modelContent;
    }

    public void setModelContent(List<String> modelContent) {
        this.modelContent = modelContent;
    }

    public static class WarnBean implements Serializable{

        private Object beginTime;
        private String code;
        private String createTime;
        private DeviceBean device;
        private Object endTime;
        private int id;
        private String name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object paramsObj;
        private String remark;
        private String s1;
        private String s2;
        private String s3;
        private String state;
        private String stateContent;
        private Object type;
        private double v1;
        private double v2;
        private double v3;
        private List<String> modelContent;

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
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

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
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

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

        public String getS2() {
            return s2;
        }

        public void setS2(String s2) {
            this.s2 = s2;
        }

        public String getS3() {
            return s3;
        }

        public void setS3(String s3) {
            this.s3 = s3;
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

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public double getV1() {
            return v1;
        }

        public void setV1(double v1) {
            this.v1 = v1;
        }

        public double getV2() {
            return v2;
        }

        public void setV2(double v2) {
            this.v2 = v2;
        }

        public double getV3() {
            return v3;
        }

        public void setV3(double v3) {
            this.v3 = v3;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }

        public static class DeviceBean implements Serializable{

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

            public static class ProjectBean implements Serializable{

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
}
