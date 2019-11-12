package com.joshua.androidparam.bean;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class WifiBean {
    private String bssid;
    private String ssid;
    private int level;
    private int frequency;
    private String capabilities;

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "WifiMode [bssid=" + bssid + ", ssid=" + ssid + ", level="
                + level + ", frequency=" + frequency + ", capabilities="
                + capabilities + "]";
    }

}
