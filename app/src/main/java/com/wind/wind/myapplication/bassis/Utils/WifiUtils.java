package com.library.util;

import android.annotation.SuppressLint;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * 添加人：  Tom Hawk
 * 添加时间：2018/7/16 14:59
 * 功能描述：wifi实用类
 * <p>
 * 修改人：  Tom Hawk
 * 修改时间：2018/7/16 14:59
 * 修改内容：添加
 */
@SuppressLint("MissingPermission")
public class WifiUtils {
    /**
     * 扫描wifi
     *
     * @param wifiManager
     * @param openWifiIfClosed true:没有开启wifi的话进行开启
     */
    public static void startScan(@NonNull WifiManager wifiManager, boolean openWifiIfClosed) {
        if (openWifiIfClosed) {
            openWifiIfClosed(wifiManager);
        }
        wifiManager.startScan();
    }

    /**
     * 如果没有开启wifi则进行开启
     *
     * @param wifiManager
     */
    private static void openWifiIfClosed(@NonNull WifiManager wifiManager) {
        if (!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);
    }

    /**
     * 连接到指定wifi
     *
     * @param wifiManager
     * @param SSID
     * @param password
     * @return
     */
    public static boolean connectToSSID(@NonNull WifiManager wifiManager, @NonNull String SSID, @Nullable String password) {
        openWifiIfClosed(wifiManager);

        WifiConfiguration configuration = TextUtils.isEmpty(password) ? createOpenWifiConfiguration(wifiManager, SSID) : createWifiConfiguration(SSID, password);
        Log.e("**", "Priority assigned to configuration is " + configuration.priority);

        int networkId = wifiManager.addNetwork(configuration);
        Log.e("**", "networkId assigned while adding network is " + networkId);

        return enableNetwork(wifiManager, SSID, networkId);
    }

    /**
     * 连接到指定的开放的(不需要密码)wifi
     *
     * @param wifiManager
     * @param SSID
     * @return
     */
    private static WifiConfiguration createOpenWifiConfiguration(@NonNull WifiManager wifiManager, @NonNull String SSID) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = String.format("\"%s\"", SSID);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        assignHighestPriority(wifiManager, configuration);
        return configuration;
    }

    /**
     * 连接到指定的需要密码的wifi
     *
     * @param SSID
     * @param password
     * @return
     */
    private static WifiConfiguration createWifiConfiguration(@NonNull String SSID, @NonNull String password) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = String.format("\"%s\"", SSID);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        configuration.allowedAuthAlgorithms.set(0);
        configuration.status = WifiConfiguration.Status.ENABLED;
        configuration.preSharedKey = String.format("\"%s\"", password);
        return configuration;
    }

    /**
     * 设置最高优先级
     *
     * @param wifiManager
     * @param config
     */
    private static void assignHighestPriority(@NonNull WifiManager wifiManager, @NonNull WifiConfiguration config) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration existingConfig : configuredNetworks) {
                if (config.priority <= existingConfig.priority) {
                    config.priority = existingConfig.priority + 1;
                }
            }
        }
    }

    /**
     * 启用wifi
     *
     * @param wifiManager
     * @param SSID
     * @param networkId
     * @return
     */
    private static boolean enableNetwork(@NonNull WifiManager wifiManager, @NonNull String SSID, int networkId) {
        if (networkId == -1) {
            networkId = getExistingNetworkId(wifiManager, SSID);

            if (networkId == -1) {
                Log.e("**", "Couldn't add network with SSID: " + SSID);
                return false;
            }
        }

        return wifiManager.enableNetwork(networkId, true);
    }

    /**
     * 获取现有的网络
     *
     * @param wifiManager
     * @param SSID
     * @return
     */
    private static int getExistingNetworkId(@NonNull WifiManager wifiManager, @NonNull String SSID) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration existingConfig : configuredNetworks) {
                if (areEqual(trimQuotes(existingConfig.SSID), trimQuotes(SSID))) {
                    return existingConfig.networkId;
                }
            }
        }
        return -1;
    }

    public static boolean areEqual(String SSID, String anotherSSID) {
        return TextUtils.equals(trimQuotes(SSID), trimQuotes(anotherSSID));
    }

    @NonNull
    public static String trimQuotes(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.replaceAll("^\"*", "").replaceAll("\"*$", "");
        }
        return str;
    }

    /**
     * 判断是否是开放wifi:不需要输入连接密码
     *
     * @param scanResult
     * @return
     */
    public static boolean isOpenWifi(@NonNull ScanResult scanResult) {
        String capabilities = scanResult.capabilities.trim().toLowerCase();
        return !(TextUtils.isEmpty(capabilities) || capabilities.contains("wpa2") || capabilities.contains("wpa") || capabilities.contains("wep"));
    }

    /**
     * 获取连接的wifi
     *
     * @param wifiManager
     * @return
     */
    public static WifiInfo getConnectedWifiInfo(WifiManager wifiManager) {
        return wifiManager.isWifiEnabled() ? wifiManager.getConnectionInfo() : null;
    }
}
