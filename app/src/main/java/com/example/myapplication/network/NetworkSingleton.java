package com.example.myapplication.network;

public class NetworkSingleton {

    private NetworkSingleton() {
    }

    public static NetworkSingleton getInstance() {
        return SingletonState.networkStateSingleton;
    }

    private static class SingletonState {
        public static NetworkSingleton networkStateSingleton = new NetworkSingleton();
    }

    private boolean mobileEnabled;
    private boolean wifiEnabled;
    private boolean isWifi;
    private boolean isMobile;
    private boolean isConnected;

    public boolean isMobileEnabled() {
        return mobileEnabled;
    }

    public void setMobileEnabled(boolean mobileEnabled) {
        this.mobileEnabled = mobileEnabled;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public boolean isConnected() {
        return isWifi | isMobile;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
