package com.longtuchecksdk;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.longtuchecksdk.utils.Longtusdk;
import com.longtuchecksdk.utils.Longtusdk.ILongtuClassListener;
import com.longtuchecksdk.utils.Longtusdk.IRequestWithoutTicketListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class LongtuClassUtils {
    private final static String TAG = "Longtusdk";

    //	public static void Longtuclass(String userid,String token,Context context,final ILongtuClassListener iclassListener){
    public static void Longtuclass(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, final ILongtuClassListener iclassListener) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        String className = "";
        for (int i = 0; i < stackTrace.length; i++) {

            StackTraceElement stackTraceElement = stackTrace[i];

            className = stackTraceElement.getClassName();

            sb.append(className + "|");

        }
        Log.i(TAG, "class:" + sb.toString());
        Bundle parameters = new Bundle();

        /*parameters.putString("classesArr", sb.toString());
        parameters.putString("userid", userid);
        parameters.putString("token", token);
        parameters.putString("deviceID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        parameters.putString("deviceName", Build.BRAND);
        parameters.putString("deviceModel", Build.MODEL);
        parameters.putString("deviceSystemVersion", Build.VERSION.RELEASE);*/

        parameters.putString("classes_arr", sb.toString());
        parameters.putString("uid", uid);
        parameters.putString("device_name", deviceName);
        parameters.putString("device_system_version", systemVersion);
        parameters.putString("device_id", deviceId);

        String url;
        if (isReServer) {
            url = HttpUtils.RE_URL + HttpUtils.CLASS;
        } else {
            url = HttpUtils.URL + HttpUtils.CLASS;
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
