package com.android.hcbd.socc.entity;

import java.util.List;

/**
 * Created by guocheng on 2017/4/18.
 */

public class LoginInfo {
    private String token;
    private UserInfo userInfo;
    private List<MenuInfo> menuList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<MenuInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuInfo> menuList) {
        this.menuList = menuList;
    }

    public static class UserInfo{

        /**
         * code : S0006
         * id : 5
         * menuPath : null
         * name : admin
         * names : S0006-admin
         * orgCode : 027
         * orgName : 华创北斗总公司
         * permit : false
         * remind : false
         */

        private String code;
        private int id;
        private Object menuPath;
        private String name;
        private String names;
        private String orgCode;
        private String orgName;
        private boolean permit;
        private boolean remind;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getMenuPath() {
            return menuPath;
        }

        public void setMenuPath(Object menuPath) {
            this.menuPath = menuPath;
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

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public boolean isPermit() {
            return permit;
        }

        public void setPermit(boolean permit) {
            this.permit = permit;
        }

        public boolean isRemind() {
            return remind;
        }

        public void setRemind(boolean remind) {
            this.remind = remind;
        }
    }

    public static class MenuInfo{

        /**
         * code : 1,1,1,1
         * id : null
         * name : 超限处罚
         * names : 1,1,1,1-超限处罚
         */

        private String code;
        private Object id;
        private String name;
        private String names;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
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
    }

}
