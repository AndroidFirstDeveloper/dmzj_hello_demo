package com.example.myapplication.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

public class NetWorkUtils {

	/**
	 * 判断是否连接网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否连接Mobile网络
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean isMobile(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State state = connManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		if (State.CONNECTED == state) {
			return true;
		}
		return false;
	}

	/**
	 * 获得当前网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean getNetWorkType(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
			switch (info.getSubtype()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
			}
			// if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
			// || info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
			// || info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
			// System.out.println("mobile connected");
			// } else {
			// System.out.println("type:" + info.getSubtype());
			// System.out.println("not mobile");
			// }
		} else
			System.out.println("not mobile connected");
		return false;
	}

}
