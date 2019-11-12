package com.joshua.androidparam.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.gson.Gson;
import com.joshua.androidparam.bean.WifiBean;
import com.joshua.androidparam.bean.WifiBean2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class WifiUtil {
    public static String getssid(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
    public static WifiBean2 getWifiMode(Context context) {
        WifiBean2 wifiBean = new WifiBean2();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        wifiBean.setSsid(wifiInfo.getSSID());
        wifiBean.setBssid(wifiInfo.getBSSID());
        wifiBean.setMacAddress(wifiInfo.getMacAddress());
        wifiBean.setLinkSpeed(wifiInfo.getLinkSpeed());
        wifiBean.setRssi(wifiInfo.getRssi());
        List<ScanResult> scanResults = wifiManager.getScanResults();
        ArrayList<WifiBean> wifiLists = new ArrayList<WifiBean>();
        for(ScanResult scanResult:scanResults){
            WifiBean wifiMode = new WifiBean();
            wifiMode.setBssid(scanResult.BSSID);
            wifiMode.setSsid(scanResult.SSID);
            wifiMode.setCapabilities(scanResult.capabilities);
            wifiMode.setFrequency(scanResult.frequency);
            wifiMode.setLevel(scanResult.level);
            wifiLists.add(wifiMode);
        }
        if(wifiLists!=null&&wifiLists.size()>0){
            Gson gson = new Gson();
            String wifilist = gson.toJson(wifiLists);
            wifiBean.setWifilist(wifilist);
        }
        return wifiBean;
    }

    /**
     * 1. wifiManager.WIFI_STATE_DISABLED (1)
     2. wifiManager..WIFI_STATE_ENABLED (3)
     3. wifiManager..WIFI_STATE_DISABLING (0)
     4 wifiManager..WIFI_STATE_ENABLING  (2)
     *
     * @param context
     * @return
     */
    public static String isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiManager.getWifiState();
        return wifiManager.isWifiEnabled()+" wifi state "+wifiState;
    }
}
