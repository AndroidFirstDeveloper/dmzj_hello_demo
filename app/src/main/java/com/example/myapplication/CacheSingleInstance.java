package com.example.myapplication;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.telecom.Call;
import android.telecom.Connection;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.model.ImageBean;
import com.longtuchecksdk.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CacheSingleInstance {

    private final String TAG = "CacheSingleInstance";

    private CacheSingleInstance() {

    }

    public static CacheSingleInstance getInstance() {
        return CacheInner.cacheSingleInstance;
    }

    private static class CacheInner {
        private static final CacheSingleInstance cacheSingleInstance = new CacheSingleInstance();
    }

    public void cacheImage(final String comicId, final String chapterId, final List<ImageBean> list, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: 开始缓存图片,总共" + list.size() + "张图片");
                int counter = 0;
                final int page = 10;
                for (ImageBean imageBean : list) {
//                    Log.e(TAG, "run: 图片名称=" + list.get(0).getName());
                    if (haveCacheTarget(context, comicId, chapterId, imageBean.getName())) {
//                        Log.e(TAG, "run: 文件已经存在");
                        continue;
                    }
                    try {
                        File file = Glide.with(context)
                                .load(imageBean.getName())
                                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                        if (file != null && file.exists()) {
//                            Log.e(TAG, "run: glide缓存图片路径=" + file.getPath());
                            counter++;
                            //保存图片到指定目录下
                            storageFile(context, file, comicId, chapterId, imageBean.getName());
                        }
                        if (counter % page == 0) {
                            Log.e(TAG, "run: 已缓存了" + counter + "张图片");
                        }
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: 缓存报错1：" + e.getMessage());
                    } catch (ExecutionException e) {
                        Log.e(TAG, "run: 缓存报错2：" + e.getMessage());
                    }
                }
                Log.e(TAG, "run: 缓存图片结束,总共缓存了" + counter + "张图片");
            }
        }).start();
    }

    //    private final String BASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "myfuncomic" + File.separator;
//    private final String BASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "myfuncomic" + File.separator;

    private void storageFile(Context context, File source, String comicId, String chapterId, String url) {
        final String grandFile = md5FileFolder(comicId);
        final String parentFile = md5FileFolder(chapterId);
        final String fileName = md5FileName(url);

//        String basePath = BASE_FILE_PATH + grandFile + File.separator + parentFile;
        //context.getCacheDir() 这个目录是默认用来给应用程序做数据缓存的,该方法返回指向/data/data/<Package Name>/cache/目录的一个File对象。
        String basePath = context.getCacheDir().getPath() + File.separator + grandFile + File.separator + parentFile;
//        Log.e(TAG, "storageFile: 基本路径=" + BASE_FILE_PATH + "\t\t加密后文件名称=" + fileName);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File folderFile = new File(basePath);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        try {
            File saveFile = new File(folderFile, fileName);
            if (saveFile.exists() && saveFile.isFile()) {
                return;
            } else {
                saveFile.createNewFile();
            }
            fis = new FileInputStream(source);
            fos = new FileOutputStream(saveFile);
            byte[] bytes = new byte[1024];
            while (fis.read(bytes) > 0) {
                fos.write(bytes);
            }
            Log.e(TAG, "storageFile: 图片另存成功,路径=" + saveFile.getPath());
        } catch (IOException e) {
            Log.e(TAG, "storageFile: ", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "storageFile: ", e);
            }
        }
    }

    public boolean haveCacheTarget(Context context, String comicId, String chapterId, String url) {
        final String grandFile = md5FileFolder(comicId);
        final String parentFile = md5FileFolder(chapterId);
        final String fileName = md5FileName(url);
//        final String basePath = BASE_FILE_PATH + grandFile + File.separator + parentFile + File.separator + fileName;
        String basePath = context.getCacheDir().getPath() + File.separator + grandFile + File.separator + parentFile + File.separator + fileName;
        File file = new File(basePath);
        if (file.exists() && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否存在指定文件
     */
    public String getCachePath(Context context, String comicId, String chapterId, String url) {
        final String grandFile = md5FileFolder(comicId);
        final String parentFile = md5FileFolder(chapterId);
        final String fileName = md5FileName(url);

//        final String basePath = BASE_FILE_PATH + grandFile + File.separator + parentFile + File.separator + fileName;
        String basePath = context.getCacheDir().getPath() + File.separator + grandFile + File.separator + parentFile + File.separator + fileName;
        File file = new File(basePath);
        if (file.exists() && file.isFile()) {
            Log.e(TAG, "getCachePath: 返回缓存路径");
            return basePath;
        } else {
            Log.e(TAG, "getCachePath: 返回原始路径");
            return url;
        }
    }

    /**
     * 对文件夹进行md5加密
     */
    private String md5FileFolder(String folderName) {
        if (TextUtils.isEmpty(folderName)) {
            return MD5.MD5Encode("czl");
        }
        return MD5.MD5Encode(folderName);
    }

    /**
     * 对文件名进行加密(不包括父目录)
     */
    private String md5FileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "aa.jpg";
        }
        final int endIndex = fileName.indexOf("?");
        if (endIndex > 0) {
            return MD5.MD5Encode(fileName.substring(0, endIndex)) + ".jpg";
        }
        return "aa.jpg";
    }

    /**
     * -----------------------------------------------------------------------分割线(代码放在一个类里是为了保证多线程访问文件的安全性)-------------------------------------------------------------------
     */

   /* public static interface FileCallback {
        void call(boolean success, String result);
    }

    private static class MyThread extends Thread {
        private WeakReference<FileCallback> weakReference;
        private Context context;

        public MyThread(Context context, FileCallback callback) {
            this.weakReference = new WeakReference<>(callback);
            this.context = context;
        }

        @Override
        public void run() {
            super.run();

            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().call(true, result);
            }
        }
    }*/

    /**
     * 清除缓存功能代码
     */
    public String getTotalCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getApplicationContext().getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getApplicationContext().getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public void clearAllCache(Context context) {
        deleteDir(context.getApplicationContext().getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getApplicationContext().getExternalCacheDir());
        }
    }

    /**
     * 删除文件（用到了递归的功能）
     */
    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();//删除文件夹
        } else if (dir == null || dir.isDirectory()) {
            return true;
        }
        return dir.delete();//删除文件
    }

    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getApplicationContext().getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getApplicationContext().getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getApplicationContext().getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public void cleanDatabaseByName(Context context, String dbName) {
        context.getApplicationContext().deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getApplicationContext().getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getApplicationContext().getExternalCacheDir());
        }
    }

    // /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
    // public  void cleanCustomCache(String filePath) {
    // deleteFilesByDirectory(new File(filePath));
    // }
    //
    // /** * 清除本应用所有的数据 * * @param context * @param filepath */
    // public  void cleanApplicationData(Context context, String...
    // filepath) {
    // cleanInternalCache(context);
    // cleanExternalCache(context);
    // cleanDatabases(context);
    // cleanSharedPreference(context);
    // cleanFiles(context);
    // for (String filePath : filepath) {
    // cleanCustomCache(filePath);
    // }
    // }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
