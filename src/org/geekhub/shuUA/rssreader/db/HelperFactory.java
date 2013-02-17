package org.geekhub.shuUA.rssreader.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class HelperFactory{

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper GetHelper(){
        return databaseHelper;
    }

    public static void SetHelper(Context context){
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void ReleaseHelper(){
        databaseHelper.Clear();
        OpenHelperManager.releaseHelper();
        //databaseHelper=null;
    }

    public static void ClearDB(){
        databaseHelper.Clear();
    }
}
