package com.example.quansb.qbstore.entity;

public class AddressEntity {
    private String consignee;
    private String is_default;
    private String address;
    private String address_id;
    private String phone;
    private String isChoose="0"; // 0未被选中 1选中
    public String getConsignee() {
        return consignee == null ? "" : consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getIs_default() {
        return is_default == null ? "" : is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_id() {
        return address_id == null ? "" : address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsChoose() {
        return isChoose == null ? "" : isChoose;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }
}
