package org.geekhub.shuUA.rssreader.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.activity.ArticleActivity;
import org.geekhub.shuUA.rssreader.db.ArticleTable;
import org.geekhub.shuUA.rssreader.db.ArticlesContentProvider;
import org.geekhub.shuUA.rssreader.db.DatabaseHelper;
import org.geekhub.shuUA.rssreader.object.Article;
import org.geekhub.shuUA.rssreader.utill.MyListAdapter;
import org.geekhub.shuUA.rssreader.utill.XmlParser;

import java.util.Vector;


public class NewsListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    static Vector<Article> ArtColl;
    ListView lv;
    private SQLiteDatabase database;
    MyListAdapter scAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_list_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (ArtColl == null) {
            getData();
        }

        getListFromDB();
        lvOnClick();
        super.onViewCreated(view, savedInstanceState);
    }

    public void getData() {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        //dbHelper.onUpgrade(database,1,2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new XmlParser().parseXml(getActivity());

            }
        });

        thread.start();
        try {
            thread.join(15000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void lvOnClick () {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(i);
                int id =  cursor.getInt(cursor.getColumnIndex(ArticleTable.COLUMN_ID));
                String title =  cursor.getString(cursor.getColumnIndex(ArticleTable.COLUMN_TITLE));
                String content =  cursor.getString(cursor.getColumnIndex(ArticleTable.COLUMN_CONTENT));
                String link =  cursor.getString(cursor.getColumnIndex(ArticleTable.COLUMN_LINK));

                Intent intent2 = new Intent(getActivity(), ArticleActivity.class);
                intent2.putExtra("Title" , title);
                intent2.putExtra("Content" , content);
                intent2.putExtra("Link" , link);
                startActivity(intent2);
            }
        });
    }
    public void getListFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(ArticleTable.TABLE_ARTICLES, null, null, null , null, null, null);
        getActivity().startManagingCursor(cursor);
        String[] from = new String[] {ArticleTable.COLUMN_TITLE, ArticleTable.COLUMN_PUBDATE, ArticleTable.COLUMN_IMGLINK };
        int[] to = new int[] { R.id.menu_title, R.id.date_title, R.id.img_title };
        getLoaderManager().initLoader(0, null, this);
        scAdapter = new MyListAdapter(getActivity(), R.layout.list_style, cursor, from, to);
        lv = (ListView) getView().findViewById(R.id.news_list);
        lv.setAdapter(scAdapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ArticleTable.COLUMN_ID,
                ArticleTable.COLUMN_TITLE,
                ArticleTable.COLUMN_CONTENT,
                ArticleTable.COLUMN_LINK,
                ArticleTable.COLUMN_PUBDATE,
                ArticleTable.COLUMN_IMGLINK,
                ArticleTable.COLUMN_LIKE
        };

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                ArticlesContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
