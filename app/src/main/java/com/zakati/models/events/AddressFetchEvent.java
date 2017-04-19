package com.zakati.models.events;

import android.location.Address;

/**
 * Created by rahil on 4/4/16.
 */
public class AddressFetchEvent {

    String msg;
    boolean isAddressFetched;

    String fullAddress;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isAddressFetched() {
        return isAddressFetched;
    }

    public void setAddressFetched(boolean addressFetched) {
        isAddressFetched = addressFetched;
    }

    private Address address;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
