package com.longtuchecksdk;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {
	private final static String TAG = "ClassUtils";
    private ClassUtils(){}

    public final static List<String> getActivitiesClass(Context context, String packageName, List<String> excludeList){
            
            List<String> returnClassList = new ArrayList<String>();
            try {
                    //Get all activity classes in the AndroidManifest.xml
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                    if(packageInfo.activities!=null){
                            Log.d(TAG, "Found "+packageInfo.activities.length +" activity in the AndrodiManifest.xml");
                            for(ActivityInfo ai: packageInfo.activities){
                                    String c;
                                    try {
                                            c = ai.name;
                                            // Maybe isAssignableFrom is unnecessary
                                            if(Activity.class.isAssignableFrom(Class.forName(ai.name))){
                                                    returnClassList.add(c);
                                                    Log.d(TAG, ai.name +"...OK");
                                            }
                                    } catch (ClassNotFoundException e) {
                                            Log.d(TAG, "Class Not Found:"+ai.name);
//                                            e.printStackTrace();
                                    }
                            }
                            Log.d(TAG, "Filter out, left "+ returnClassList.size()  +" activity," + Arrays.toString(returnClassList.toArray()));
                            
                            //Exclude some activity classes
                            if(excludeList!=null){
                                    returnClassList.removeAll(excludeList);
                                    Log.d(TAG, "Exclude " + excludeList.size() +" activity,"+ Arrays.toString(excludeList.toArray()));
                            }
                            Log.d(TAG, "Return "+ returnClassList.size()  +" activity," + Arrays.toString(returnClassList.toArray()));
                    }
            } catch (NameNotFoundException e) {
                    e.printStackTrace();
            }

            return returnClassList;
    }

}
