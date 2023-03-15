package com.synergy.synergydhrishtiplus.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static class Data {

        @SerializedName("status_code")
        @Expose
        private Integer statusCode;
        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("error_messages")
        @Expose
        private List<Object> errorMessages = null;
        @SerializedName("data")
        @Expose
        private Data__1 data;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Object> getErrorMessages() {
            return errorMessages;
        }

        public void setErrorMessages(List<Object> errorMessages) {
            this.errorMessages = errorMessages;
        }

        public Data__1 getData() {
            return data;
        }

        public void setData(Data__1 data) {
            this.data = data;
        }


        public static class Data__1 {

            @SerializedName("login_id")
            @Expose
            private String loginId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("contact_person")
            @Expose
            private String contactPerson;
            @SerializedName("contact_num")
            @Expose
            private String contactNum;

            public String getLoginId() {
                return loginId;
            }

            public void setLoginId(String loginId) {
                this.loginId = loginId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getContactPerson() {
                return contactPerson;
            }

            public void setContactPerson(String contactPerson) {
                this.contactPerson = contactPerson;
            }

            public String getContactNum() {
                return contactNum;
            }

            public void setContactNum(String contactNum) {
                this.contactNum = contactNum;
            }

        }
    }
}
