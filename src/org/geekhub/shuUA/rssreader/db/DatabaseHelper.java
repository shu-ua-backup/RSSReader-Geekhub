package org.geekhub.shuUA.rssreader.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="rss.db";

    private static final int DATABASE_VERSION = 1;

    private ArticleDAO goalDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, ArticleDB.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            TableUtils.dropTable(connectionSource, ArticleDB.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public ArticleDAO getArticleDAO() throws SQLException {
        if(goalDao == null){
            goalDao = new ArticleDAO(getConnectionSource(), ArticleDB.class);
        }
        return goalDao;
    }

    public void Clear()
    {
        try {
            TableUtils.clearTable(connectionSource, ArticleDB.class);
            TableUtils.dropTable(connectionSource, ArticleDB.class, true);
            TableUtils.createTable(connectionSource, ArticleDB.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        super.close();
        goalDao = null;
    }
}