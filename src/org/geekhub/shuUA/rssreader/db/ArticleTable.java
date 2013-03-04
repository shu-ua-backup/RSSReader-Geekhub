package org.geekhub.shuUA.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.geekhub.shuUA.rssreader.object.Article;


public class ArticleTable {

    public static final String TABLE_ARTICLES = "articles";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "description";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_PUBDATE = "pubdate";
    public static final String COLUMN_IMGLINK = "imglink";
    public static final String COLUMN_LIKE = "like";



    public static final String[] PROJECTION = {
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_CONTENT,
            COLUMN_LINK,
            COLUMN_PUBDATE,
            COLUMN_IMGLINK,
            COLUMN_LIKE

    };

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_ARTICLES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_CONTENT + " TEXT NOT NULL, "
            + COLUMN_LINK + " TEXT NOT NULL, "
            + COLUMN_PUBDATE + " TEXT NOT NULL, "
            + COLUMN_IMGLINK + " TEXT NOT NULL,"
            + COLUMN_LIKE + " tinyint(1) " + ")";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    public static void saveArticleToDB(Context context, Article article)
    {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CONTENT,article.getContent());
        cv.put(COLUMN_TITLE,article.getTitle());
        cv.put(COLUMN_IMGLINK,article.getImgLink());
        cv.put(COLUMN_PUBDATE,article.getPubDate().getTime());
        cv.put(COLUMN_LINK,article.getLink());

        if (article.getLike()) {
           cv.put(COLUMN_LIKE,1);
        } else {
           cv.put(COLUMN_LIKE,0);
        }

        context.getContentResolver().insert(ArticlesContentProvider.CONTENT_URI, cv);

    }
}
