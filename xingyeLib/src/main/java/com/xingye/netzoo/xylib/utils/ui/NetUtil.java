package com.xingye.netzoo.xylib.utils.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xingye.netzoo.xylib.utils.MyApplication;

/**
 * Created by yx on 17/6/8.
 */

public class NetUtil {
    private static final String TAG = "NetUtils";

    private static final int NO_NET = 2147483647;
    private static final int UNKNOWN = 2147483646;
    private static final int WIFI = 2147483645;
    private static final int ROAMING = 2147483644;

    public static final String NETWORK_TYPE_2G = "2g";// 提交到服务器的网络类型：2g
    public static final String NETWORK_TYPE_3G = "3g";// 提交到服务器的网络类型：3g
    public static final String NETWORK_TYPE_4G = "4g";// 提交到服务器的网络类型：4g
    public static final String NETWORK_TYPE_WIFI = "wifi";// 提交到服务器的网络类型：wifi
    public static final String NETWORK_TYPE_UNKNOWN = "UNKNOWN";

    public enum NetworkExceptionType {
        EXCEPTION_TYPE_CONNECTION,               /**获取ConnectivityManager出错**/
        EXCEPTION_TYPE_TELEPHONY,                /**获取TelephonyManager出错**/
        EXCEPTION_TYPE_GETACTIVENETWORKINFO,     /**获取activeNetworkInfo出错**/
        EXCEPTION_TYPE_GETNETWORKINFO,           /**获取networkInfo出错**/
        EXCEPTION_TYPE_SIMOPERATORINFO,          /**获取sim卡以及系统信息出错**/
        EXCEPTION_TYPE_GETNETWORKTYPE,           /**获取当前网络类型出错**/
    };

    private static String current_type = "";// 当前网络状态
    private static boolean isOffNetwork;// 是否断开网络

    public static boolean isOffNetwork() {
        return isOffNetwork;
    }

    public static void setOffNetwork(boolean isOffNetwork) {
        NetUtil.isOffNetwork = isOffNetwork;
    }

    public static String getCurrentType() {
        return current_type;
    }

    public static void setCurrentType(String type) {
        current_type = type;
    }

    /**
     * 网络连接使用代理：默认，优先判断用户是否用代理</br> false:使用直连方式连接网络</br> true:使用当前代理IP与PORT方式连接网
     */
    public static boolean isProxy = true;

    /**
     * 网络异常埋点上报
     */

    /**
     * 封装获取网络状态的常用方法
     * 增加获取异常时候上报埋点接口
     */
    public static class NetworkUtilFactory {

        public static ConnectivityManager getConnectivityManager() {
            ConnectivityManager connectivityManager = null;
            try {
                connectivityManager = (ConnectivityManager) MyApplication.getContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connectivityManager;
        }

        public static TelephonyManager getTelephonyManager() {
            TelephonyManager telephonyManager = null;
            try {
                telephonyManager = (TelephonyManager)MyApplication.getContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return telephonyManager;
        }

        public static NetworkInfo getActiveNetworkInfo(ConnectivityManager connectivityMgr) {
            if (connectivityMgr == null)
                return null;
            NetworkInfo activeNetwork = null;
            try {
                activeNetwork = connectivityMgr.getActiveNetworkInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return activeNetwork;
        }

        public static NetworkInfo getNetworkInfo(ConnectivityManager connectivityMgr, final int type) {
            if (connectivityMgr == null)
                return null;
            NetworkInfo networkInfo = null;
            try {
                networkInfo = connectivityMgr.getNetworkInfo(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return networkInfo;
        }

        public static int getNetworkType(TelephonyManager telephonyMgr) {
            int type = -1;
            if (telephonyMgr == null)
                return type;
            try {
                type = telephonyMgr.getNetworkType();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return type;
        }
    }

    public static NetType getNetType(Context context) {

        NetType result = new NetType();// 默认为空内容

        if (!isNetworkAvailable()) {
            return result;
        }

        ConnectivityManager connectivityManager = NetworkUtilFactory.getConnectivityManager();

        int summaryType = getSummaryType(connectivityManager);

        currentNetType = summaryType;

        NetworkInfo networkInfo = null;
        networkInfo = NetworkUtilFactory.getActiveNetworkInfo(connectivityManager);

        String extraInfo = getExtraInfo(networkInfo);

        result = new NetType(summaryType, extraInfo);

        return result;
    }

    public static NetType getNetType() {
        return getNetType(MyApplication.getContext());
    }

    /**
     * 是否有网络
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = null;
        connectivityManager = NetworkUtilFactory.getConnectivityManager();

        if (null == connectivityManager) {
            return false;
        }

        NetworkInfo activeNetwork = NetworkUtilFactory.getActiveNetworkInfo(connectivityManager);
        boolean result = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (result == false) { // 小米的问题应该出在这，休眠后应该会断开网络，目前休眠超过5分钟可以重现此问题，重新唤醒后获取的状态和实际状态会有延迟，所以延迟如果获取到当前没有联网，重新获取一次服务，重新获取一次。
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            connectivityManager = NetworkUtilFactory.getConnectivityManager();
            if (null == connectivityManager) {
                return false;
            }
            activeNetwork = NetworkUtilFactory.getActiveNetworkInfo(connectivityManager);
            result = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return result;

    }

    /**
     * 判断是“手机网络”还是“无线网络”
     */
    public static int getSummaryType(ConnectivityManager connectivityManager) {

        int result = NetType.SUMMARY_TYPE_OTHER;

        NetworkInfo info;
        // mobile
        NetworkInfo.State mobile = null;
        info = NetworkUtilFactory.getNetworkInfo(connectivityManager, ConnectivityManager.TYPE_MOBILE);
        mobile = (info != null) ? info.getState() : null;

        // wifi
        NetworkInfo.State wifi = null;
        info = NetworkUtilFactory.getNetworkInfo(connectivityManager, ConnectivityManager.TYPE_WIFI);
        wifi = (info != null) ? info.getState() : null;

        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            // wifi
            result = NetType.SUMMARY_TYPE_WIFI;
        } else if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            // mobile
            result = NetType.SUMMARY_TYPE_MOBILE;
        }

        return result;
    }

    /**
     * 网络额外信息
     */
    public static String getExtraInfo(NetworkInfo networkInfo) {
        String result = "";
        if (networkInfo == null) {
            return result;
        }
        result = networkInfo.getExtraInfo();

        return result;
    }

    public static class NetType {

        public static final int NSP_CHINA_MOBILE = 1;// 移动
        public static final int NSP_CHINA_UNICOM = 2;// 联通
        public static final int NSP_CHINA_TELECOM = 3;// 电信
        public static final int NSP_OTHER = 0;// 其它
        public static final int NSP_NO = -1;// 不可用

        public static final int SUMMARY_TYPE_WIFI = 1;// WIFI
        public static final int SUMMARY_TYPE_MOBILE = 2;// MOBILE
        public static final int SUMMARY_TYPE_OTHER = 0;// 其它

        private String extraInfo;

        private int summaryType = SUMMARY_TYPE_OTHER;


        Integer simState;
        String networkType;
        String networkTypeName;
        String networkOperator;
        String networkOperatorName;

        String proxyHost;
        Integer proxyPort;

        public NetType(int summaryType, String extraInfo) {
            this.summaryType = summaryType;
            this.extraInfo = extraInfo;
            getSimAndOperatorInfo();
        }

        public NetType() {
        }

        private void getSimAndOperatorInfo() {
            TelephonyManager telephonyManager = NetworkUtilFactory.getTelephonyManager();
            if (telephonyManager == null)
                return;
            try {
                simState = telephonyManager.getSimState();
                networkOperatorName = telephonyManager.getNetworkOperatorName();
                networkOperator = telephonyManager.getNetworkOperator();
                int temp = telephonyManager.getNetworkType();
                networkType = "" + temp;
                networkTypeName = getNetworkTypeName(temp);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        public int getNSP() {

            if (null == simState || simState == TelephonyManager.SIM_STATE_UNKNOWN) {
                return NSP_NO;
            }
            if (TextUtils.isEmpty(networkOperatorName) && TextUtils.isEmpty(networkOperator)) {
                return NSP_NO;
            }

            if (("中国移动".equalsIgnoreCase(networkOperatorName))//
                    || ("CMCC".equalsIgnoreCase(networkOperatorName)) //
                    || ("46000".equalsIgnoreCase(networkOperator))//
                    || ("China Mobile".equalsIgnoreCase(networkOperatorName))) {
                // 中国移动
                return NSP_CHINA_MOBILE;
            }
            if (("中国电信".equalsIgnoreCase(networkOperatorName))//
                    || ("China Telecom".equalsIgnoreCase(networkOperatorName))//
                    || ("46003".equalsIgnoreCase(networkOperator))) {
                // 中国电信
                return NSP_CHINA_TELECOM;
            }
            if (("中国联通".equalsIgnoreCase(networkOperatorName))//
                    || ("China Unicom".equalsIgnoreCase(networkOperatorName))//
                    || ("46001".equalsIgnoreCase(networkOperator))//
                    || ("CU-GSM".equalsIgnoreCase(networkOperatorName))) {
                // 中国联通
                return NSP_CHINA_UNICOM;
            }

            return NSP_OTHER;
        }

        @SuppressLint("NewApi")
        public String getNetworkTypeName(int code) {
            switch (code) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "UMTS";
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "HSDPA";
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "HSUPA";
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "HSPA";
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "CDMA";
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "CDMA - EvDo rev. 0";
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "CDMA - EvDo rev. A";
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "CDMA - 1xRTT";
                default:
                    return "UNKNOWN";
            }
        }

        public String getDetailType() {
            // StringBuilder sb = new StringBuilder();
            return "";
        }

        public String getUploadType() {
            return networkType;
        }

        public String getProxyHost() {
            String proxyHost = Proxy.getDefaultHost();
            if (SUMMARY_TYPE_WIFI == summaryType) {// 魅族切换到WIFI时proxyHost仍然按切换前返回，导致需要如此处理。
                return null;
            } else {
                this.proxyHost = proxyHost;
                this.proxyPort = Proxy.getDefaultPort();
            }

            return this.proxyHost;
        }

        public Integer getProxyPort() {
            return proxyPort;
        }
        public String getNetworkOperator(){
            return networkOperator;
        }

        public String getNetworkOperatorName(){
            return networkOperatorName;
        }

    }

    /**
     * GPRS       2G(2.5) General Packet Radia Service 114kbps
     * EDGE       2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS       3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
     * CDMA      2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0   3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT      2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA    3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA    3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA      3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN      2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE        4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD  3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP  3G HSPAP 比 HSDPA 快些
     */
    public static boolean is2GNetwork(Context context) {

        NetType netType = getNetType();
        if (netType.summaryType == NetType.SUMMARY_TYPE_WIFI) {
            return false;
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephonyManager.getNetworkType();
        if (TelephonyManager.NETWORK_TYPE_CDMA == type || TelephonyManager.NETWORK_TYPE_GPRS == type || TelephonyManager.NETWORK_TYPE_EDGE == type) {
            return true;
        }
        return false;
    }

    public static String getNetworkType(Context context) {
        return getNetworkType();
    }

    /**
     * 获取网络类型，用于传给服务器2g/3g/wifi
     *
     * @return
     */
    public static String getNetworkType() {
        NetType netType = getNetType();
        int type = -1;
        if (netType.summaryType == NetType.SUMMARY_TYPE_WIFI) {
            return NETWORK_TYPE_WIFI;
        } else if (netType.summaryType == NetType.SUMMARY_TYPE_MOBILE) {

            final TelephonyManager telephonyManager = NetworkUtilFactory.getTelephonyManager();
            if (telephonyManager == null)
                return NETWORK_TYPE_UNKNOWN;

            type = NetworkUtilFactory.getNetworkType(telephonyManager);
            switch (type) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                case 16:  //对应隐藏网络类型 ： /** Current network is GSM {@hide} */ public static final int NETWORK_TYPE_GSM = 16;
                    return NETWORK_TYPE_2G;

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                case 17: //对应隐藏网络类型 ：  /** Current network is TD_SCDMA {@hide} */ public static final int NETWORK_TYPE_TD_SCDMA = 17;
                    return NETWORK_TYPE_3G;

                case TelephonyManager.NETWORK_TYPE_LTE:
                case 18: // 对应隐藏网络类型 ： /** Current network is IWLAN {@hide} */ public static final int NETWORK_TYPE_IWLAN = 18;
                    return NETWORK_TYPE_4G;
                default:
                    break;
            }
        }
        return NETWORK_TYPE_UNKNOWN;
    }

    /**
     * 判断是否是wifi环境
     *
     * @return
     */
    public static boolean isWifi() {
        NetType netType = getNetType();
        if (netType.summaryType == NetType.SUMMARY_TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static int currentNetType = NetType.SUMMARY_TYPE_OTHER;

    // 网络Receiver切换时收到广播之后切换
    public static void updateNetType() {
        NetType netType = getNetType();
        currentNetType = netType.summaryType;
    }

    // 图片加载的时候防止频繁调用getNetType()方法
    public static boolean isWifiForLoadImage() {
        return currentNetType == NetType.SUMMARY_TYPE_WIFI;

    }

    /**
     * 判断是否是wifi环境
     *
     * @return
     */
    public static boolean is3GOr2GNetwork() {
        NetType netType = getNetType();
        if (netType.summaryType == NetType.SUMMARY_TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static String getNetworkOperator(){
        NetType netType = getNetType();
        return netType.getNetworkOperator();
    }

    public static String getNetworkOperatorName(){
        NetType netType = getNetType();
        return netType.getNetworkOperatorName();
    }

    public static String getCurrentMicrosecond() {
        return "" + String.format("%.6f",((System.currentTimeMillis()+0.0)/1000.0f));
    }
}
