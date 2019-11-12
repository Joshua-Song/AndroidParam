package com.joshua.androidparam.model;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.webkit.WebView;

import com.joshua.androidparam.R;
import com.joshua.androidparam.bean.DisplayBean;
import com.joshua.androidparam.bean.ParamBean;
import com.joshua.androidparam.bean.SimInfoBean;
import com.joshua.androidparam.bean.WifiBean2;
import com.joshua.androidparam.contract.MainActivityContract;
import com.joshua.androidparam.utils.BuildUtil;
import com.joshua.androidparam.utils.ConvertUtil;
import com.joshua.androidparam.utils.DisplayUtil;
import com.joshua.androidparam.utils.GetLocationUtil;
import com.joshua.androidparam.utils.ImeiUtil;
import com.joshua.androidparam.utils.InnerIPUtil;
import com.joshua.androidparam.utils.NetUtil;
import com.joshua.androidparam.utils.OtherUtil;
import com.joshua.androidparam.utils.P2P0MACUtil;
import com.joshua.androidparam.utils.ProviderUtil;
import com.joshua.androidparam.utils.TelephoneUtil;
import com.joshua.androidparam.utils.WifiUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class MainActivityModel implements MainActivityContract.Model {

    private List<ParamBean> paramList;

    public List<ParamBean> getParamList() {
        return paramList;
    }

    public MainActivityModel() {
        this.paramList = new ArrayList<>();
    }

    @Override
    public void loadParam(Context context, ModelListener modelListener) {

        final List<ParamBean> list = new ArrayList<>();
        Location location = GetLocationUtil.getLocation(context);
        list.add(new ParamBean(context.getString(R.string.longitude), location!=null?location.getLongitude():null, "", true));
        list.add(new ParamBean(context.getString(R.string.latitude), location!=null?location.getLatitude():null, "", true));
        list.add(new ParamBean(context.getString(R.string.location_accuracy),location!=null?location.getAccuracy():null,"",false));
        CellLocation cellLocation = GetLocationUtil.getcellLocation(context);
        if(cellLocation instanceof GsmCellLocation){
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            list.add(new ParamBean(context.getString(R.string.cellid_gms), gsmCellLocation!=null?gsmCellLocation.getCid():null, "", true));
            list.add(new ParamBean(context.getString(R.string.lac_gms), gsmCellLocation!=null?gsmCellLocation.getLac():null, "", true));
        }
        if(cellLocation instanceof CdmaCellLocation){
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            list.add(new ParamBean(context.getString(R.string.cellid_cdma), cdmaCellLocation!=null?cdmaCellLocation.getBaseStationId():null, "", true));
            list.add(new ParamBean(context.getString(R.string.lac_cdma), cdmaCellLocation!=null?cdmaCellLocation.getNetworkId():null, "", true));
            list.add(new ParamBean(context.getString(R.string.longitude_cdma), cdmaCellLocation!=null?cdmaCellLocation.getBaseStationLongitude() / 14400:null, "", false));
            list.add(new ParamBean(context.getString(R.string.latitude_cdma), cdmaCellLocation!=null?cdmaCellLocation.getBaseStationLatitude() / 14400:null, "", false));
        }
        List<CellInfo> allCellInfo = GetLocationUtil.getAllCellInfo(context);
        HashMap<String, Integer> hashMap = new HashMap<>();
        if (allCellInfo != null)
            for (CellInfo cellInfo : allCellInfo) {
                if (cellInfo instanceof CellInfoGsm) {
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                    CellIdentityGsm cellIdentityGsm = cellInfoGsm.getCellIdentity();
                    int mcc = cellIdentityGsm.getMcc();
                    int mnc = cellIdentityGsm.getMnc();
                    int cid = cellIdentityGsm.getCid();
                    int lac = cellIdentityGsm.getLac();
                    String a = "Gsm" + mcc + "a" + mnc + "a" + cid + "a" + lac;
                    if (!hashMap.containsKey(a)) {
                        hashMap.put(a, 1);
                        list.add(new ParamBean(context.getString(R.string.mcc_gms), mcc, "", false));
                        list.add(new ParamBean(context.getString(R.string.mnc_gms), mnc, "", false));
                        list.add(new ParamBean(context.getString(R.string.cellid_gms), cid, "", false));
                        list.add(new ParamBean(context.getString(R.string.lac_gms), lac, "", false));
                    }
                } else if (cellInfo instanceof CellInfoCdma) {
                    CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                    CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
                    int latitude = cellIdentity.getLatitude() / 14400;
                    int longitude = cellIdentity.getLongitude() / 14400;
                    int networkId = cellIdentity.getNetworkId();
                    int basestationId = cellIdentity.getBasestationId();
                    int systemId = cellIdentity.getSystemId();
                    String a = "Cdma" + latitude + "a" + longitude + "a" + networkId + "a" + basestationId + "a"
                            + systemId;
                    if (!hashMap.containsKey(a)) {
                        hashMap.put(a, 1);
                        list.add(new ParamBean(context.getString(R.string.latitude_cdma), latitude, "", false));
                        list.add(new ParamBean(context.getString(R.string.longitude_cdma), longitude, "", false));
                        list.add(new ParamBean(context.getString(R.string.cellid_cdma), networkId, "", false));
                        list.add(new ParamBean(context.getString(R.string.lac_cdma), basestationId, "", false));
                        list.add(new ParamBean(context.getString(R.string.system_id_cdma), systemId, "", false));
                    }
                } else if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    CellIdentityLte cellIdentity = cellInfoLte.getCellIdentity();
                    int mcc = cellIdentity.getMcc();
                    int mnc = cellIdentity.getMnc();
                    int ci = cellIdentity.getCi();
                    int tac = cellIdentity.getTac();
                    String a = "Lte" + mcc + "a" + mnc + "a" + ci + "a" + tac;
                    if (!hashMap.containsKey(a)) {
                        hashMap.put(a, 1);
                        list.add(new ParamBean(context.getString(R.string.cellid_lte), ci, "", false));
                        list.add(new ParamBean(context.getString(R.string.lac_lte), tac, "", false));
                        list.add(new ParamBean(context.getString(R.string.mcc_lte), mcc, "", false));
                        list.add(new ParamBean(context.getString(R.string.mnc_lte), mnc, "", false));
                    }
                } else if (cellInfo instanceof CellInfoWcdma) {
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                    CellIdentityWcdma cellIdentity = cellInfoWcdma.getCellIdentity();
                    int mcc = cellIdentity.getMcc();
                    int mnc = cellIdentity.getMnc();
                    int cid = cellIdentity.getCid();
                    int lac = cellIdentity.getLac();
                    String a = "Wcdma" + mcc + "a" + mnc + "a" + cid + "a" + lac;
                    if (!hashMap.containsKey(a)) {
                        hashMap.put(a, 1);
                        list.add(new ParamBean(context.getString(R.string.cellid_wcdma), cid, "", false));
                        list.add(new ParamBean(context.getString(R.string.lac_wcdma), lac, "", false));
                        list.add(new ParamBean(context.getString(R.string.mcc_wcdma), mcc, "", false));
                        list.add(new ParamBean(context.getString(R.string.mnc_wcdma), mnc, "", false));
                    }
                }
            }
        list.add(new ParamBean(context.getString(R.string.inner_ip), InnerIPUtil.getInnerIP(context), "", false));
        list.add(new ParamBean(context.getString(R.string.android_id_1), ProviderUtil.getAndroidID(context),"",false));
        list.add(new ParamBean(context.getString(R.string.android_id_2), ProviderUtil.getAndroidID2(context),"",false));
        try{
            list.add(new ParamBean(context.getString(R.string.user_agent_1), new WebView(context).getSettings().getUserAgentString(),"",false));
//				list.add(new ParamBean(context.getString(R.string.user_agent_2), WebSettings.getDefaultUserAgent(context),"",false));

        }catch(Exception e){
            e.printStackTrace();
        }
        SimInfoBean simInfoBean = TelephoneUtil.getSimInfoBean(context);

        list.add(new ParamBean(context.getString(R.string.imei_1), simInfoBean.getImei(), ImeiUtil.getInstance(context).getImei(),false));
        list.add(new ParamBean(context.getString(R.string.gsf_id), OtherUtil.getGsfAndroidId(context),"",false));
        list.add(new ParamBean(context.getString(R.string.sim_serial_1), simInfoBean.getSim_serial(),"",false));
        list.add(new ParamBean(context.getString(R.string.phone_number_1), simInfoBean.getPhone_num(),"",true));
        list.add(new ParamBean(context.getString(R.string.phone_type_1), simInfoBean.getPhoneType_1() == -1 ? null : ConvertUtil.phoneTypeint2Str(simInfoBean.getPhoneType_1()),"",true));
        list.add(new ParamBean(context.getString(R.string.simoperator_name), simInfoBean.getSim_operator_name(),"",false));
        list.add(new ParamBean(context.getString(R.string.sim_state_1), simInfoBean.getSim_state() == -1 ? null : simInfoBean.getSim_state(),"",true));
        list.add(new ParamBean(context.getString(R.string.icccard_1), TelephoneUtil.getHasIccCard(context),"",true));
        list.add(new ParamBean(context.getString(R.string.sim_operator_name_1), simInfoBean.getOperator()/*MccMncConver.n_str2Str(simInfoBean.getOperator())*/,"",true));
        list.add(new ParamBean(context.getString(R.string.sim_operator_id_1), simInfoBean.getSim_operator(),ConvertUtil.mccMnc_str2Str(simInfoBean.getSim_operator()),true));
        list.add(new ParamBean(context.getString(R.string.imsi_1), simInfoBean.getSubscriberId(),"",true));
        list.add(new ParamBean(context.getString(R.string.sim_operator_country_id), simInfoBean.getSim_country_iso(),"",true));
        list.add(new ParamBean(context.getString(R.string.spn_operator_name_1), simInfoBean.getNetwork_operator_name(),"",true));
        WifiBean2 wifiBean2 = WifiUtil.getWifiMode(context);
        list.add(new ParamBean(context.getString(R.string.ssid), wifiBean2.getSsid(), "",true));
        list.add(new ParamBean(context.getString(R.string.bssid), wifiBean2.getBssid(), "",true));
        list.add(new ParamBean(context.getString(R.string.wifi_link_speed), wifiBean2.getLinkSpeed(), "",true));
        list.add(new ParamBean(context.getString(R.string.rssi), wifiBean2.getRssi(), "",true));
        list.add(new ParamBean(context.getString(R.string.mac), wifiBean2.getMacAddress(), "",true));
        list.add(new ParamBean(context.getString(R.string.p2p0_mac), P2P0MACUtil.getP2P0Mac(), "",true));
        list.add(new ParamBean(context.getString(R.string.wifi_list), wifiBean2.getWifilist(), "",true));
        list.add(new ParamBean(context.getString(R.string.is_wifi_open), WifiUtil.isWifiEnabled(context),"",true));
        list.add(new ParamBean(context.getString(R.string.has_available_net), NetUtil.isHasNet(context),"",false));
        list.add(new ParamBean(context.getString(R.string.is_mobile_connect),NetUtil.isMobileConnect(context.getApplicationContext()),"",false));
        list.add(new ParamBean(context.getString(R.string.is_wifi_connected), NetUtil.isWifiConnect(context),"",false));
        list.add(new ParamBean(context.getString(R.string.net_type_class_2g),NetUtil.getNetTypeClass(context),ConvertUtil.netWorkClassint2String(NetUtil.getNetTypeClass(context)),true));
        list.add(new ParamBean(context.getString(R.string.net_work_type_1),NetUtil.getNetWorkType1(context),"",false));

        list.add(new ParamBean(context.getString(R.string.active_net_info),NetUtil.getActiveNetTypeName(context)+"_"
                +NetUtil.getActiveNetType(context)+"_"+NetUtil.getActiveNetSubtypeName(context)+"_"+NetUtil.getActiveSubNetType(context),
                "NetworkInfo:getTypeName_getType_getSubtypeName_getSubtype",true));

        list.add(new ParamBean(context.getString(R.string.active_net_state),NetUtil.getNetState(context),"",false));
        list.add(new ParamBean(context.getString(R.string.wifi_mobile_net_state),NetUtil.getWifiMobileConnectState(context),"",false));

        list.add(new ParamBean(context.getString(R.string.build_release_1), BuildUtil.getRelease(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_brand_1), BuildUtil.getBrand(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_model_1), BuildUtil.getModel(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_board), BuildUtil.getBoard(),"",false));


        list.add(new ParamBean(context.getString(R.string.build_product_1), BuildUtil.getProduct(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_cpu_abi_1), BuildUtil.getCpuAbi(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_cpu_abi2_1), BuildUtil.getCpuAbi2(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_id_1), BuildUtil.getID(),"",true));

        list.add(new ParamBean(context.getString(R.string.build_display_1), BuildUtil.getDisplay(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_fingerprint_1), BuildUtil.getFingerprint(),"",true));

        list.add(new ParamBean(context.getString(R.string.build_code_name), BuildUtil.getCodename(),"",false));

        list.add(new ParamBean(context.getString(R.string.build_manufacture_1), BuildUtil.getManufacturer(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_host_1), BuildUtil.getHost(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_type_1), BuildUtil.getType(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_sdk_1), BuildUtil.getSDK(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_tags_1), BuildUtil.getTags(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_devices_1), BuildUtil.getDevice(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_serial_1), BuildUtil.getSerial(),"",true));

        list.add(new ParamBean(context.getString(R.string.build_bootloader), BuildUtil.getBootloader(),"",false));
        list.add(new ParamBean(context.getString(R.string.build_incremental), BuildUtil.getIncremental(),"",false));

        list.add(new ParamBean(context.getString(R.string.build_radioVersion_1), BuildUtil.getRadioVersion(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_hardware), BuildUtil.getHardWare(),"",true));
        list.add(new ParamBean(context.getString(R.string.build_user_1), BuildUtil.getUser(),"",false));

        DisplayBean displayBean = DisplayUtil.getDisplayBean((Activity) context);
        DisplayBean displayBean2 = DisplayUtil.getDisplayBean2((Activity) context);
        list.add(new ParamBean(context.getString(R.string.width), displayBean.getWidth(),"",false));
        list.add(new ParamBean(context.getString(R.string.height), displayBean.getHeight(),"",false));
        list.add(new ParamBean(context.getString(R.string.display_size), displayBean.getDisplaySize(),"",false));
        list.add(new ParamBean(context.getString(R.string.scaled_density_1), displayBean.getScaledDensity(),"",false));
        list.add(new ParamBean(context.getString(R.string.scaled_density_2), displayBean2.getScaledDensity(),"",true));
        list.add(new ParamBean(context.getString(R.string.width_pixels_1), displayBean.getWidthPixels(),"",false));
        list.add(new ParamBean(context.getString(R.string.width_pixels_2), displayBean2.getWidthPixels(),"",true));
        list.add(new ParamBean(context.getString(R.string.height_pixels_1), displayBean.getHeightPixels(),"",false));
        list.add(new ParamBean(context.getString(R.string.height_pixels_2), displayBean2.getHeightPixels(),"",true));
        list.add(new ParamBean(context.getString(R.string.density_dpi_1), displayBean.getDensityDpi(),"",false));
        list.add(new ParamBean(context.getString(R.string.density_dpi_2), displayBean2.getDensityDpi(),"",true));
        list.add(new ParamBean(context.getString(R.string.density_1), displayBean.getDensity(),"",false));
        list.add(new ParamBean(context.getString(R.string.density_2), displayBean2.getDensity(),"",true));
        list.add(new ParamBean(context.getString(R.string.xdpi_1), displayBean.getXdpi(),"",false));
        list.add(new ParamBean(context.getString(R.string.xdpi_2), displayBean2.getXdpi(),"",true));
        list.add(new ParamBean(context.getString(R.string.ydpi_1), displayBean.getYdpi(),"",false));
        list.add(new ParamBean(context.getString(R.string.ydpi_2), displayBean2.getYdpi(),"",true));

        list.add(new ParamBean(context.getString(R.string.bluetooth_mac), OtherUtil.getBluetoothMac(),"",true));
        list.add(new ParamBean(context.getString(R.string.bluetooth_name), OtherUtil.getBluetoothName(),"",true));

        list.add(new ParamBean(context.getString(R.string.cpu_frequency), OtherUtil.getCpuFrequency(),"Root ",false));
        list.add(new ParamBean(context.getString(R.string.kernel_version), OtherUtil.getkernelVersion(),"Root",false));

        paramList.clear();
        paramList.addAll(list);
        modelListener.onSuccess(list);
    }
}
