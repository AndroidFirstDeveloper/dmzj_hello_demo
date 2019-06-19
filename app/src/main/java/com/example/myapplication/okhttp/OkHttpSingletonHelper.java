package com.example.myapplication.okhttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.myapplication.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hantao on 2018/2/7.
 */

public class OkHttpSingletonHelper {

    private OkHttpClient mOkHttpClient;

    private OkHttpSingletonHelper() {
        OkHttpClient.Builder ClientBuilder = new OkHttpClient.Builder();
        ClientBuilder.readTimeout(20, TimeUnit.SECONDS);//读取超时
        ClientBuilder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
        ClientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        //支持HTTPS请求，跳过证书验证
        ClientBuilder.sslSocketFactory(createSSLSocketFactory());
        ClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        mOkHttpClient = ClientBuilder.build();
    }

    public static OkHttpSingletonHelper getInstance() {
        return OkHttpSingleton.instance;
    }

    private static class OkHttpSingleton {
        public static final OkHttpSingletonHelper instance = new OkHttpSingletonHelper();
    }

    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response getDataSynFromNet(String url) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        //2 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //3 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post请求，同步方式，提交数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response postDataSynToNet(String url, Map<String, String> bodyParams) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 自定义网络回调接口
     */
    public interface MyNetCall {
        void success(Call call, Response response) throws IOException;

        void failed(Call call, IOException e);

        void netError(String note);
    }


    public interface GetCallback {
        void success(Call call, String result);

        void failed(Call call, IOException e);

        void netError(String note);
    }

    public interface PostCallback {
        void success(Call call, String result);

        void failed(Call call, IOException e);

        void netError(String note);
    }

    /**
     * get请求，异步方式，获取网络数据，是在子线程中执行的，需要切换到主线程才能更新UI
     *
     * @param url
     * @param myNetCall
     * @return
     */
    public void getDataAsynFromNet(Context applicationContext, final String url, final MyNetCall myNetCall) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
            try {
                //添加头不能有中文;URLEncoder.encode(ssid)
                //1 构造Request
                Request.Builder builder = new Request.Builder().get().url(url);
                builder.addHeader("Android", "android");  //将请求头以键值对形式添加，可添加多个请求头
                Request request = builder.build();

                //2 将Request封装为Call
                Call call = mOkHttpClient.newCall(request);
                //3 执行Call
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        myNetCall.failed(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        myNetCall.success(call, response);
                        //关闭防止内存泄漏
                        if (response.body() != null) {
                            response.body().close();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            myNetCall.netError("");
        }
    }

    private final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public void requestPostJson(Context applicationContext, String url, String json, final PostCallback callback) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
            RequestBody requestBody = RequestBody.create(JSON, json);
//            if (HttpConstant.IS_RE_SERVER) {
//                if (url.contains("codetest.myfcomic.com")) {
//                    url = url.replace("codetest.myfcomic.com", "recodetest.myfcomic.com");
//                } else if (url.contains("pay.myfcomic.com")) {
//                    url = url.replace("pay", "repay");
//                } else if (url.contains("commentapi.myfcomic.com")) {
//                    url = url.replace("commentapi", "recommentapi");
//                }
//            }
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Version",Utils.getVersionName(applicationContext))
//                    .addHeader("Platform","1")
                    .post(requestBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null)
                        callback.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if (response.body() != null) {
                        response.body().close();//关闭防止内存泄漏
                    }
                    if (callback != null)
                        callback.success(call, result);
                }
            });
        } else {
            callback.netError("");
        }
    }

    private String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }

    public void requestPostFormData(Context applicationContext, String url, Map<String, String> paramsMap, String[] files, final PostCallback callback) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
//            if (HttpConstant.IS_RE_SERVER) {
//                if (url.contains("codetest.myfcomic.com")) {
//                    url = url.replace("codetest.myfcomic.com", "recodetest.myfcomic.com");
//                } else if (url.contains("pay.myfcomic.com")) {
//                    url = url.replace("pay", "repay");
//                } else if (url.contains("commentapi.myfcomic.com")) {
//                    url = url.replace("commentapi", "recommentapi");
//                }
//            }
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            if (paramsMap != null) {
                //czl20194222
//                if (!paramsMap.containsKey("client_version")) {
//                    paramsMap.put("client_version", Utils.getVersionName(applicationContext));
//                }
//                if (!paramsMap.containsKey("platform")) {
//                    paramsMap.put("platform", "1");
//                }
                //czl2019422
                for (String key1 : paramsMap.keySet()) {
                    if (TextUtils.isEmpty(paramsMap.get(key1))) {
                        paramsMap.put(key1, "");
                    }
                }

                for (String key : paramsMap.keySet()) {
                    builder.addFormDataPart(key, paramsMap.get(key));
                }
            }

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = new File(files[i]);
                    String fileType = getMimeType(file.getName());
                    RequestBody requestBody = RequestBody.create(MediaType.parse(fileType), file);
                    builder.addFormDataPart("img[" + i + "]", file.getName(), requestBody);
                }
            }

            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Version",Utils.getVersionName(applicationContext))
//                    .addHeader("Platform","1")
                    .post(requestBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null)
                        callback.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if (response.body() != null) {
                        response.body().close();//关闭防止内存泄漏
                    }
                    if (callback != null)
                        callback.success(call, result);
                }
            });
        } else {
            callback.netError("");
        }
    }

    public void requestPostFormData2(Context applicationContext, String url, Map<String, String> paramsMap, Map<String, String> files, final PostCallback callback) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
//            if (HttpConstant.IS_RE_SERVER) {
//                if (url.contains("codetest.myfcomic.com")) {
//                    url = url.replace("codetest.myfcomic.com", "recodetest.myfcomic.com");
//                } else if (url.contains("pay.myfcomic.com")) {
//                    url = url.replace("pay", "repay");
//                } else if (url.contains("commentapi.myfcomic.com")) {
//                    url = url.replace("commentapi", "recommentapi");
//                }
//            }
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            if (paramsMap != null) {
//                if (!paramsMap.containsKey("client_version")) {
//                    paramsMap.put("client_version", Utils.getVersionName(applicationContext));
//                }
//                if (!paramsMap.containsKey("platform")) {
//                    paramsMap.put("platform", "1");
//                }
                for (String key : paramsMap.keySet()) {
                    builder.addFormDataPart(key, paramsMap.get(key));
                }
            }

            if (files != null) {
                for (String key : files.keySet()) {
                    File file = new File(files.get(key));
                    String fileType = getMimeType(file.getName());
                    RequestBody requestBody = RequestBody.create(MediaType.parse(fileType), file);
                    builder.addFormDataPart(key, file.getName(), requestBody);
                }
            }

            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Version",Utils.getVersionName(applicationContext))
//                    .addHeader("Platform","1")
                    .post(requestBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null)
                        callback.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if (response.body() != null) {
                        response.body().close();//关闭防止内存泄漏
                    }
                    if (callback != null)
                        callback.success(call, result);
                }
            });
        } else {
            callback.netError("");
        }
    }

    public void requestPostFormData(Context applicationContext, String url, Map<String, String> paramsMap, final PostCallback callback) {
        requestPostFormData(applicationContext, url, paramsMap, null, callback);
    }

    public void requestGetBySync(Context applicationContext, String url, Map<String, String> paramsMap, final GetCallback callback) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
            StringBuilder paramsBuilder = new StringBuilder();
            int position = 0;
            String requestUrl = url;
//            if (HttpConstant.IS_RE_SERVER) {
//                if (requestUrl.contains("codetest.myfcomic.com")) {
//                    requestUrl = requestUrl.replace("codetest.myfcomic.com", "recodetest.myfcomic.com");
//                } else if (requestUrl.contains("pay.myfcomic.com")) {
//                    requestUrl = requestUrl.replace("pay", "repay");
//                } else if (requestUrl.contains("commentapi.myfcomic.com")) {
//                    requestUrl = requestUrl.replace("commentapi", "recommentapi");
//                }
//            }

            if (paramsMap != null) {
                //czl2019422
                if (!paramsMap.containsKey("client_version")) {
                    paramsMap.put("client_version", Utils.getVersionName(applicationContext));
                }
                if (!paramsMap.containsKey("platform")) {
                    paramsMap.put("platform", "1");
                }
                //czl2019422
                for (String key1 : paramsMap.keySet()) {
                    if (TextUtils.isEmpty(paramsMap.get(key1))) {
                        paramsMap.put(key1, "");
                    }
                }
                for (String key : paramsMap.keySet()) {
                    if (position > 0) {
                        paramsBuilder.append("&");
                    }
                    try {
                        paramsBuilder.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    } catch (UnsupportedEncodingException e) {
                        //Log.e("OkHttpSingletonHelper", "编码出错:" + e.getMessage());
                    }
                    position++;
                }
                requestUrl = String.format("%s?%s", requestUrl, paramsBuilder.toString());
            }

            Request.Builder builder = new Request.Builder().get()
                    .url(requestUrl);
//                    .addHeader("Version",Utils.getVersionName(applicationContext))
//                    .addHeader("Platform","1");
            Request request = builder.build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null)
                        callback.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if (response.body() != null) {
                        response.body().close();//关闭防止内存泄漏
                    }
                    if (callback != null)
                        callback.success(call, result);
                }
            });
        } else {
            callback.netError("");
        }
    }

    /**
     * post请求，异步方式，提交数据，是在子线程中执行的，需要切换到主线程才能更新UI
     */
    public void postDataAsynToNet(Context applicationContext, String url, final Map<String, String> paramsMap, final PostCallback callback) {
        if (NetWorkUtils.isNetworkAvailable(applicationContext)) {
            try {
//                if (HttpConstant.IS_RE_SERVER) {
//                    if (url.contains("codetest.myfcomic.com")) {
//                        url = url.replace("codetest.myfcomic.com", "recodetest.myfcomic.com");
//                    } else if (url.contains("pay.myfcomic.com")) {
//                        url = url.replace("pay", "repay");
//                    } else if (url.contains("commentapi.myfcomic.com")) {
//                        url = url.replace("commentapi", "recommentapi");
//                    }
//                }
                if (paramsMap != null) {
                    //czl2019422
//                    if (!paramsMap.containsKey("client_version")) {
//                        paramsMap.put("client_version", Utils.getVersionName(applicationContext));
//                    }
//                    if (!paramsMap.containsKey("platform")) {
//                        paramsMap.put("platform", "1");
//                    }
                    //czl2019422
                    for (String key1 : paramsMap.keySet()) {
                        if (TextUtils.isEmpty(paramsMap.get(key1))) {
                            paramsMap.put(key1, "");
                        }
                    }
                }

                //1构造RequestBody
                RequestBody body = setRequestBody(paramsMap);
                //2 构造Request
                Request.Builder requestBuilder = new Request.Builder();
                Request request = requestBuilder.post(body).url(url).build();
                //3 将Request封装为Call
                Call call = mOkHttpClient.newCall(request);
                //4 执行Call
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.failed(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callback.success(call, response.body().string());
                        //关闭防止内存泄漏
                        if (response.body() != null) {
                            response.body().close();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            callback.netError("");
        }
    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param BodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * 上传文件
     *
     * @param url      接口地址
     * @param file     文件
     * @param fileName 文件名
     */
    public void loadFile(final String url, final File file, final String fileName) {
        try {
            //创建RequestBody 封装file参数
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            //创建RequestBody 设置类型等
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
            //创建Request
            Request request = new Request.Builder().url(url).post(requestBody).build();

            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 上传json字符
     *
     * @param url        接口地址
     * @param jsonParams Json串
     * @param callback   接口回调
     */
    public void doPostJson(String url, String jsonParams, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call callbacll = mOkHttpClient.newCall(request);
        callbacll.enqueue(callback);
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
            } else {
                //如果仅仅是用来判断网络连接
                //则可以使用cm.getActiveNetworkInfo().isAvailable();
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    /**
     * 用于信任所有证书
     */
    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    /**
     * 下载
     *
     * @param context
     * @param url     下载地址
     * @param saveDir 保存的位置
     */
    public void downFile(final Activity context, final String url, final String saveDir) {
        try {
            if (context == null) {
                return;
            }
            //创建Request
            Request request = new Request.Builder().url(url).build();
            //创建Call
            Call call = mOkHttpClient.newCall(request);
            //同步
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        //apk保存路径
                        final String fileDir = isExistDir(saveDir);
                        //文件
                        File file = new File(fileDir, getNameFromUrl(url));
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "下载成功:" + fileDir + "," + getNameFromUrl(url), Toast.LENGTH_SHORT).show();
                            }
                        });
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }

                        fos.flush();
                        //apk下载完成后 调用系统的安装方法
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) is.close();
                        if (fos != null) fos.close();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public String isExistDir(String saveDir) throws IOException {
        try {
            // 下载位置
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
                if (!downloadFile.mkdirs()) {
                    downloadFile.createNewFile();
                }
                String savePath = downloadFile.getAbsolutePath();
                //Log.e("savePath", savePath);
                return savePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private String getNameFromUrl(String url) {
        String urlStr = "";
        try {
            urlStr = url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlStr;
    }
}
