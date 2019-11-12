package com.joshua.androidparam.bean;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class GaoTongDoubleBean {
    private String imei_1;
    private String imei_2;
    private boolean gaoTongDoubleSim;

    public boolean isGaoTongDoubleSim() {
        return gaoTongDoubleSim;
    }
    public void setGaoTongDoubleSim(boolean gaoTongDoubleSim) {
        this.gaoTongDoubleSim = gaoTongDoubleSim;
    }
    public String getImei_1() {
        return imei_1;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public String getImei_2() {
        return imei_2;
    }

    public void setImei_2(String imei_2) {
        this.imei_2 = imei_2;
    }
    @Override
    public String toString() {
        return "GaotongDoubleInfo [imei_1=" + imei_1 + ", imei_2=" + imei_2
                + ", gaotongDoubleSim=" + gaoTongDoubleSim + "]";
    }
}
