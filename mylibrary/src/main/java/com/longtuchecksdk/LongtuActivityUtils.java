package com.longtuchecksdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.longtuchecksdk.utils.Longtusdk;
import com.longtuchecksdk.utils.Longtusdk.ILongtuActivityListener;
import com.longtuchecksdk.utils.Longtusdk.IRequestWithoutTicketListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.util.List;

public class LongtuActivityUtils {
    private final static String TAG = "Longtusdk";

    static String sum = "";

    //    public static void Longtuactivity(String userid, String token, Context context, final ILongtuActivityListener iclassListener) {
    //czl 2019-3-18
    public static void Longtuactivity(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, final ILongtuActivityListener iclassListener) {
        List<String> list = ClassUtils.getActivitiesClass(context, context.getPackageName(), null);
        if (sum != null && !sum.equals("")) {
            sum = "";
        } else {
            for (String d : list) {
                sum += d;
            }
            Log.i(TAG, "activity:" + sum);
            Log.i(TAG, "activity:" + md5(sum));
            Bundle parameters = new Bundle();
            /*parameters.putString("actmd5", md5(sum));
            parameters.putString("userid", userid);
            parameters.putString("token", token);
            parameters.putString("deviceID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
            parameters.putString("deviceName", Build.BRAND);
            parameters.putString("deviceModel", Build.MODEL);
            parameters.putString("deviceSystemVersion", Build.VERSION.RELEASE);*/

            //czl 2019-3-18
            parameters.putString("actmd5", md5(sum));
            parameters.putString("uid", uid);
            parameters.putString("device_name", deviceName);
            parameters.putString("device_system_version", systemVersion);
            parameters.putString("device_id", deviceId);

            String url;
            if (isReServer) {
                url = HttpUtils.RE_URL + HttpUtils.ACTIVITY;
            } else {
                url = HttpUtils.URL + HttpUtils.ACTIVITY;
            }
            Longtusdk.asyncRequestWithoutTicket(url, parameters, "POST", new IRequestWithoutTicketListener() {
                @Override
                public void onMalformedURLException(MalformedURLException ex) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onIOException(IOException ex) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFileNotFoundException(FileNotFoundException ex) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(String resp) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, resp);
                    if (iclassListener != null)
                        iclassListener.onComplete(0, resp);

                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    public static String md5(String source) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString().toLowerCase();
    }
}
