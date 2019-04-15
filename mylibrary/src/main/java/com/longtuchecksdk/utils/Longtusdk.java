package com.longtuchecksdk.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.longtuchecksdk.HttpUtils;
import com.longtuchecksdk.LongtuActivityUtils;
import com.longtuchecksdk.LongtuClassUtils;
import com.longtuchecksdk.LongtuPackegUtils;
import com.longtuchecksdk.LongtuSignUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


public class Longtusdk {

    private final static String TAG = "Longtusdk";

    /**
     * 签名检测
     */
    public static ILongtuSignListener isignListener = null;

    public static interface ILongtuSignListener {
        public void onComplete(int code, String message);
    }

    public static void LongtuSign(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, ILongtuSignListener isignListeners) {
        isignListener = isignListeners;
        LongtuSignUtils.Longtusign(context, uid, deviceName, systemVersion, deviceId, isReServer, isignListener);

    }

    /**
     * 类名检测
     */
    public static ILongtuClassListener iclassListener = null;

    public static interface ILongtuClassListener {
        public void onComplete(int code, String message);
    }

    public static void LongtuClass(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, ILongtuClassListener iclassListeners) {
        iclassListener = iclassListeners;
        LongtuClassUtils.Longtuclass(context, uid, deviceName, systemVersion, deviceId, isReServer, iclassListener);
    }


    /**
     * 包名检测
     */
    public static ILongtuPackageListener ipackListener = null;

    public static interface ILongtuPackageListener {
        public void onComplete(int code, String message);
    }

    public static void LongtuPackage(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, ILongtuPackageListener ipackListeners) {
        ipackListener = ipackListeners;
        LongtuPackegUtils.Longtupackeg(context, uid, deviceName, systemVersion, deviceId, isReServer, ipackListener);
    }


    /**
     * activity检测
     */
    public static ILongtuActivityListener iactivityListener = null;

    public static interface ILongtuActivityListener {
        public void onComplete(int code, String message);
    }

    public static void LongtuActivity(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, ILongtuActivityListener iactivityListeners) {
        iactivityListener = iactivityListeners;
        LongtuActivityUtils.Longtuactivity(context, uid, deviceName, systemVersion, deviceId, isReServer, iactivityListener);
    }


    public static void asyncRequestWithoutTicket(final String requestUrl, final Bundle parameters, final String httpMethod, final IRequestWithoutTicketListener requestListener) {
        if (requestUrl.equals("")) {
            Log.i(TAG, "Async request url is empty.");
        } else {
            new Thread() {
                public void run() {
                    try {
                        String resp = HttpUtils.openUrl(requestUrl,
                                httpMethod, parameters, "");
                        requestListener.onComplete(resp);
                    } catch (FileNotFoundException ex) {

                        Log.i(TAG, "onFileNotFoundException:" + ex.getMessage());
                        requestListener.onFileNotFoundException(ex);
                    } catch (MalformedURLException ex) {

                        Log.i(TAG, "MalformedURLException:" + ex.getMessage());
                        requestListener.onMalformedURLException(ex);
                    } catch (IOException ex) {

                        Log.i(TAG, "IOException:" + ex.getMessage());
                        requestListener.onIOException(ex);
                    }
                }
            }.start();
        }
    }

    public static interface IRequestWithoutTicketListener {

        public void onComplete(String resp);

        public void onIOException(IOException ex);

        public void onFileNotFoundException(FileNotFoundException ex);

        public void onMalformedURLException(MalformedURLException ex);
    }
}
