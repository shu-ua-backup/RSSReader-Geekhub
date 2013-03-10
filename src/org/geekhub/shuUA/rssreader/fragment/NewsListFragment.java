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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.activity.ArticleActivity;
import org.geekhub.shuUA.rssreader.activity.LikeListActivity;
import org.geekhub.shuUA.rssreader.db.ArticleTable;
import org.geekhub.shuUA.rssreader.db.ArticlesContentProvider;
import org.geekhub.shuUA.rssreader.db.DatabaseHelper;
import org.geekhub.shuUA.rssreader.utill.MyListAdapter;
import org.geekhub.shuUA.rssreader.utill.XmlParser;


public class NewsListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView lv;
    private SQLiteDatabase database;
    MyListAdapter scAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.news_list_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListFromDB();
        getData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.list_menu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_all:
                Intent intent1 = new Intent(getActivity(), LikeListActivity.class);
                startActivity(intent1);
                break;
            case R.id.refresh:
                getData();
                getListFromDB();
        }



        return super.onOptionsItemSelected(item);
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
    }


    public void lvOnClick () {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(i);
                int id =  cursor.getInt(cursor.getColumnIndex(ArticleTable.COLUMN_ID));
                Intent intent2 = new Intent(getActivity(), ArticleActivity.class);
                intent2.putExtra("ID" , id);
                startActivity(intent2);

            }
        });
    }
    public void getListFromDB() {

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(ArticleTable.TABLE_ARTICLES, null, null, null , null, null, ArticleTable.COLUMN_PUBDATE + " DESC");
        getActivity().startManagingCursor(cursor);
        String[] from = new String[] {ArticleTable.COLUMN_TITLE, ArticleTable.COLUMN_PUBDATE, ArticleTable.COLUMN_IMGLINK };
        int[] to = new int[] { R.id.menu_title, R.id.date_title, R.id.img_title };
        getLoaderManager().initLoader(0, null, this);
        scAdapter = new MyListAdapter(getActivity(), R.layout.list_style, cursor, from, to);
        lv = (ListView) getView().findViewById(R.id.news_list);
        lv.setAdapter(scAdapter);
        lvOnClick();
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
