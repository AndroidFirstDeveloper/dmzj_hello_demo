package com.longtuchecksdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.longtuchecksdk.utils.Longtusdk;
import com.longtuchecksdk.utils.Longtusdk.ILongtuSignListener;
import com.longtuchecksdk.utils.Longtusdk.IRequestWithoutTicketListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LongtuSignUtils {
    private final static String TAG = "Longtusdk";


    //	@SuppressLint("NewApi") public static void Longtusign(String userid,String token,Context context,final ILongtuSignListener isignListener){
    @SuppressLint("NewApi")
    public static void Longtusign(Context context, String uid, String deviceName, String systemVersion, String deviceId, boolean isReServer, final ILongtuSignListener isignListener) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Log.i(TAG, "sign:" + getMD5MessageDigest(packageInfo.signatures[0].toByteArray()));

            Bundle parameters = new Bundle();
	    
	     	/*parameters.putString("keymd5",getMD5MessageDigest(packageInfo.signatures[0].toByteArray()));
	        parameters.putString("userid", userid);
	        parameters.putString("token", token);
	        parameters.putString("deviceID",Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
	        parameters.putString("deviceName",Build.BRAND);
	        parameters.putString("deviceModel",Build.MODEL);
	        parameters.putString("deviceSystemVersion", Build.VERSION.RELEASE);*/

            //czl 2019-3-18
            parameters.putString("keymd5", getMD5MessageDigest(packageInfo.signatures[0].toByteArray()));
            parameters.putString("uid", uid);
            parameters.putString("device_name", deviceName);
            parameters.putString("device_system_version", systemVersion);
            parameters.putString("device_id", deviceId);

            String url;
            if (isReServer) {
                url = HttpUtils.RE_URL + HttpUtils.SIGN;
            } else {
                url = HttpUtils.URL + HttpUtils.SIGN;
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
                    if (isignListener != null)
                        isignListener.onComplete(0, resp);
                }
            });
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getMD5MessageDigest(byte[] bytes) {
        StringBuffer md5StringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(bytes);
            byte[] digest = messageDigest.digest();
            for (int i = 0; i < digest.length; i++) {
                String hexString = Integer.toHexString(digest[i] & 0xff);

                if (hexString.length() == 1)
                    md5StringBuffer.append("0");

                md5StringBuffer.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StringBuffer.toString();
    }
}
