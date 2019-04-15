package com.longtuchecksdk;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;


public class HttpUtils {

    public static final String URL = "https://pay.myfcomic.com/";
    public static final String RE_URL = "https://repay.myfcomic.com/";
    public static final String SIGN = "api/check/key";
    public static final String ACTIVITY = "api/check/activity";
    public static final String PACKAGE = "api/check/package";
    public static final String CLASS = "api/check/classes";


    private final static String TAG = "HttpUtils";
    static TrustManager[] xtmArray = new MytmArray[]{new MytmArray()};
    private final static int CONNENT_TIMEOUT = 15000;
    private final static int READ_TIMEOUT = 15000;

    public static String openUrl(String url, String method, Bundle params, String accept)
            throws MalformedURLException, IOException {
        HttpURLConnection conn;


        // random string as boundary for multi-part http post
        String strBoundary = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
        String endLine = "\r\n";


        OutputStream os;

        if (method.equals("GET") && params != null) {
            url = url + "?" + encodeUrl(params);
        }
        Log.i(TAG, "" + method + " URL: " + url + "");


        java.net.URL httpsurl = new URL(url);
        if (httpsurl.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            //http = (HttpsURLConnection) url.openConnection();
            conn = (HttpURLConnection) httpsurl.openConnection();

            ((HttpsURLConnection) conn).setHostnameVerifier(DO_NOT_VERIFY);
        } else {
            conn = (HttpURLConnection) httpsurl.openConnection();
        }


        conn.setConnectTimeout(CONNENT_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);


        conn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36");
//		conn.setRequestProperty("User-Agent", System.getProperties()
//				.getProperty("http.agent") + " LemonGame Android SDK " +"Android2.0.2");
        if (!accept.equals("")) {
            conn.setRequestProperty("Accept", accept);
        }
        if (method.equals("POST") && params != null) {
            Bundle dataparams = new Bundle();
            for (String key : params.keySet()) {
                Object parameter = params.get(key);
                Log.i(TAG, "" + key + " : " + parameter + "..........1111");
                if (parameter instanceof byte[]) {
                    dataparams.putByteArray(key, (byte[]) parameter);
                }
            }
            //conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + strBoundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            os = new BufferedOutputStream(conn.getOutputStream());

            os.write(("--" + strBoundary + endLine).getBytes());
            os.write((encodePostBody(params, strBoundary)).getBytes());
            os.write((endLine + "--" + strBoundary + endLine).getBytes());

            if (!dataparams.isEmpty()) {

                for (String key : dataparams.keySet()) {
                    os.write(("Content-Disposition: form-data; filename=\""
                            + key + "\"" + endLine).getBytes());
                    os.write(("Content-Type: content/unknown" + endLine + endLine)
                            .getBytes());
                    os.write(dataparams.getByteArray(key));
                    os.write((endLine + "--" + strBoundary + endLine)
                            .getBytes());

                }
            }
            os.flush();
        } else if (method.equals("DELETE")) {

            conn.setConnectTimeout(20000);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + strBoundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();

            os = new BufferedOutputStream(conn.getOutputStream());

            os.write(("--" + strBoundary + endLine).getBytes());
            os.write((encodePostBody(params, strBoundary)).getBytes());
            os.write((endLine + "--" + strBoundary + endLine).getBytes());
            os.flush();

        }
        String response = "";
        try {
            response = read(conn.getInputStream());
        } catch (FileNotFoundException ex) {
            response = read(conn.getErrorStream());
        } catch (IOException ex) {
            response = read(conn.getErrorStream());
        }

        if (response.equals("") || response == null)
            response = "{\"code\":-1,\"message\":\"NetWork error.\"}";

        Log.i(TAG, ":Response:" + response);
        return response;
    }

    public static String encodeUrl(Bundle parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (!(parameter instanceof String)) {
                continue;
            }

            if (first) first = false;
            else sb.append("&");
            sb.append(URLEncoder.encode(key) + "=" +
                    URLEncoder.encode(parameters.getString(key)));
        }
        return sb.toString();
    }

    static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHosts() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, xtmArray, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encodePostBody(Bundle parameters, String boundary) {
        if (parameters == null) return "";
        StringBuilder sb = new StringBuilder();

        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (!(parameter instanceof String)) {
                continue;
            }

            sb.append("Content-Disposition: form-data; name=\"" + key +
                    "\"\r\n\r\n" + (String) parameter);
            sb.append("\r\n" + "--" + boundary + "\r\n");
        }

        return sb.toString();
    }


    private static String read(InputStream in) throws IOException {
        if (in == null) return "";
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1024);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
}
