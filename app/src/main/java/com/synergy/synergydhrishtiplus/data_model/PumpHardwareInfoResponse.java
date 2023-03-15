package com.synergy.synergydhrishtiplus.data_model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PumpHardwareInfoResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static class Data  implements Serializable {
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
        @SerializedName("dispenser_id")
        @Expose
        private List<DispenserId> dispenserId = null;
        @SerializedName("dispenser_nozzle")
        @Expose
        private List<DispenserNozzle> dispenserNozzle = null;
        @SerializedName("tank_data")
        @Expose
        private List<TankDatum> tankData = null;

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

        public List<DispenserId> getDispenserId() {
            return dispenserId;
        }

        public void setDispenserId(List<DispenserId> dispenserId) {
            this.dispenserId = dispenserId;
        }

        public List<DispenserNozzle> getDispenserNozzle() {
            return dispenserNozzle;
        }

        public void setDispenserNozzle(List<DispenserNozzle> dispenserNozzle) {
            this.dispenserNozzle = dispenserNozzle;
        }

        public List<TankDatum> getTankData() {
            return tankData;
        }

        public void setTankData(List<TankDatum> tankData) {
            this.tankData = tankData;
        }

        @NonNull
        @Override
        public String toString() {
            return "\nData{" +
                    "\n\tstatusCode='" + statusCode + '\'' +
                    "\n\tsuccess='" + success + '\'' +
                    "\n\tmessage='" + message + '\'' +
                    "\n\terror_messages='" + errorMessages + '\'' +
                    "\n\tdispenserId='" + dispenserId + '\'' +
                    "\n\tdispenserNozzle='" + dispenserNozzle + '\'' +
                    "\n\ttankData='" + tankData + '\'' +
                    "\n}";
        }

        public static class DispenserId  implements Serializable{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("ip_address")
            @Expose
            private String ip_address;
            @SerializedName("port_no")
            @Expose
            private String port_number;
            @SerializedName("dispenser")
            @Expose
            private String  dispencer="";

            private boolean connected_Status;



            public String getDispencer() {
                return dispencer;
            }

            public boolean isConnected_Status() {
                return connected_Status;
            }

            public void setConnected_Status(boolean connected_Status) {
                this.connected_Status = connected_Status;
            }

            public void setDispencer(String dispencer) {
                this.dispencer = dispencer;
            }

            public String getIp_address() {
                return ip_address;
            }

            public void setIp_address(String ip_address) {
                this.ip_address = ip_address;
            }

            public String getPort_number() {
                return port_number;
            }

            public void setPort_number(String port_number) {
                this.port_number = port_number;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @NonNull
            @Override
            public String toString() {
                return "\ndispenserid{" +
                        "\n\tid='" + id + '\'' +
                        "\n\tip_address='" + ip_address + '\'' +
                        "\n\tport_number='" + port_number + '\'' +
                        "\n\tdispencer='" + dispencer + '\'' +
                        "\n\tconnected_Status='" + connected_Status + '\'' +
                        "\n}";
            }
        }

        public static class DispenserNozzle implements Serializable {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("dispenser_id")
            @Expose
            private String dispenserId;
            @SerializedName("pump_id")
            @Expose
            private String pumpId;
            @SerializedName("nozzel")
            @Expose
            private String nozzel;
            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("type_of_preset")
            @Expose
            private String typeOfPreset;
            @SerializedName("insert_at")
            @Expose
            private String insertAt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDispenserId() {
                return dispenserId;
            }

            public void setDispenserId(String dispenserId) {
                this.dispenserId = dispenserId;
            }

            public String getPumpId() {
                return pumpId;
            }

            public void setPumpId(String pumpId) {
                this.pumpId = pumpId;
            }

            public String getNozzel() {
                return nozzel;
            }

            public void setNozzel(String nozzel) {
                this.nozzel = nozzel;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getTypeOfPreset() {
                return typeOfPreset;
            }

            public void setTypeOfPreset(String typeOfPreset) {
                this.typeOfPreset = typeOfPreset;
            }

            public String getInsertAt() {
                return insertAt;
            }

            public void setInsertAt(String insertAt) {
                this.insertAt = insertAt;
            }

            @NonNull
            @Override
            public String toString() {
                return "\nDispenserNozzle{" +
                        "\n\tid='" + id + '\'' +
                        "\n\tdispenser_id='" + dispenserId + '\'' +
                        "\n\tpumpId=" + pumpId +
                        "\n\tnozzel=" + nozzel +
                        "\n\tproductId=" + productId +
                        "\n\tproductName=" + productName +
                        "\n\ttypeOfPreset=" + typeOfPreset +
                        "\n}";
            }
        }

        public static class TankDatum implements Serializable {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("franchisee_id")
            @Expose
            private String franchiseeId;
            @SerializedName("capacity")
            @Expose
            private String capacity;
            @SerializedName("product")
            @Expose
            private String product;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("atg_type")
            @Expose
            private String atgType;
            @SerializedName("make_of_atg")
            @Expose
            private String makeOfAtg;
            @SerializedName("serial_no")
            @Expose
            private String serialNo;
            @SerializedName("dip_chart_name")
            @Expose
            private String dipChartName;
            @SerializedName("dip_chart_data")
            @Expose
            private String dipChartData;
            @SerializedName("insert_at")
            @Expose
            private String insertAt;
            @SerializedName("ip_address")
            @Expose
            private String ip_address;
            @SerializedName("port_no")
            @Expose
            private String port_no;

            private float volume;

            private boolean connected_Status;
            public String getId() {
                return id;
            }

            public String getIp_address() {
                return ip_address;
            }

            public void setIp_address(String ip_address) {
                this.ip_address = ip_address;
            }

            public boolean isConnected_Status() {
                return connected_Status;
            }

            public void setConnected_Status(boolean connected_Status) {
                this.connected_Status = connected_Status;
            }

            public String getPort_no() {
                return port_no;
            }

            public void setPort_no(String port_no) {
                this.port_no = port_no;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFranchiseeId() {
                return franchiseeId;
            }

            public void setFranchiseeId(String franchiseeId) {
                this.franchiseeId = franchiseeId;
            }

            public String getCapacity() {
                return capacity;
            }

            public void setCapacity(String capacity) {
                this.capacity = capacity;
            }

            public String getProduct() {
                return product;
            }

            public float getVolume() {
                return volume;
            }

            public void setVolume(float volume) {
                this.volume = volume;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getAtgType() {
                return atgType;
            }

            public void setAtgType(String atgType) {
                this.atgType = atgType;
            }

            public String getMakeOfAtg() {
                return makeOfAtg;
            }

            public void setMakeOfAtg(String makeOfAtg) {
                this.makeOfAtg = makeOfAtg;
            }

            public String getSerialNo() {
                return serialNo;
            }

            public void setSerialNo(String serialNo) {
                this.serialNo = serialNo;
            }

            public String getDipChartName() {
                return dipChartName;
            }

            public void setDipChartName(String dipChartName) {
                this.dipChartName = dipChartName;
            }

            public String getDipChartData() {
                return dipChartData;
            }

            public void setDipChartData(String dipChartData) {
                this.dipChartData = dipChartData;
            }

            public String getInsertAt() {
                return insertAt;
            }

            public void setInsertAt(String insertAt) {
                this.insertAt = insertAt;
            }

            @NonNull
            @Override
            public String toString() {
                return "\nTankDatum{" +
                        "\n\tid='" + id + '\'' +
                        "\n\tfranchiseeId='" + franchiseeId + '\'' +
                        "\n\tcapacity='" + capacity + '\'' +
                        "\n\tproduct='" + product + '\'' +
                        "\n\tproductName='" + productName + '\'' +
                        "\n\tatgType='" + atgType + '\'' +
                        "\n\tmakeOfAtg='" + makeOfAtg + '\'' +
                        "\n\tserialNo='" + serialNo + '\'' +
                        "\n\tdipChartName='" + dipChartName + '\'' +
                        "\n\tdipChartData='" + dipChartData + '\'' +
                        "\n\tip_address=" + ip_address +
                        "\n\tport_no=" + port_no +
                        "\n\tconnected=" + connected_Status +
                        "\n}";
            }
        }
    }
}