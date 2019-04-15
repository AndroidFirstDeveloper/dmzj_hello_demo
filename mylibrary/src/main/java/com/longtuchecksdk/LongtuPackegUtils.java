package com.longtuchecksdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.longtuchecksdk.utils.Longtusdk;
import com.longtuchecksdk.utils.Longtusdk.ILongtuPackageListener;
import com.longtuchecksdk.utils.Longtusdk.IRequestWithoutTicketListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class LongtuPackegUtils {
    private final static String TAG = "Longtusdk";

    private static List<ResolveInfo> apps = new ArrayList<>();

    //	public static void Longtupackeg(String userid,String token,Context context,final ILongtuPackageListener iclassListener){
    public static void Longtupackeg(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, final ILongtuPackageListener iclassListener) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = context.getPackageManager().queryIntentActivities(intent, 0);
        StringBuilder sb = new StringBuilder();
//			   String packname = "";
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            sb.append(packageName + "|");
        }
        Log.i(TAG, "packeg:" + sb.toString());

        Bundle parameters = new Bundle();

			/*parameters.putString("packageArr",sb.toString());
	        parameters.putString("userid", userid);
	        parameters.putString("token", token);
	        parameters.putString("deviceID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
	        parameters.putString("deviceName",Build.BRAND);
	        parameters.putString("deviceModel",Build.MODEL);
	        parameters.putString("deviceSystemVersion", Build.VERSION.RELEASE);*/


        parameters.putString("package_arr", sb.toString());
        parameters.putString("uid", uid);
        parameters.putString("device_name", deviceName);
        parameters.putString("device_system_version", systemVersion);
        parameters.putString("device_id", deviceId);

        String url;
        if (isReServer) {
            url = HttpUtils.RE_URL + HttpUtils.PACKAGE;
        } else {
            url = HttpUtils.URL + HttpUtils.PACKAGE;
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
