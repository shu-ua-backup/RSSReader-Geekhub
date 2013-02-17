package org.geekhub.shuUA.rssreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.activity.ArticleActivity;
import org.geekhub.shuUA.rssreader.object.Article;
import org.geekhub.shuUA.rssreader.utill.MyListAdapter;
import org.geekhub.shuUA.rssreader.utill.XmlParser;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: shu
 * Date: 03.02.13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class NewsListFragment extends SherlockFragment {
   Vector<Article> ArtColl;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_list_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        getData();
        getList();
        super.onViewCreated(view, savedInstanceState);    
    }

    public void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArtColl = new XmlParser().parseXml();
                ArtColl.getClass();
            }
        });

        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void getList() {

        if (!ArtColl.isEmpty()) {
            lv = (ListView) getView().findViewById(R.id.news_list);
            MyListAdapter adapter = new MyListAdapter(getActivity(),R.id.news_list,ArtColl);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent2 = new Intent(getActivity(), ArticleActivity.class);
                    intent2.putExtra("Title" , ArtColl.get(i).getTitle());
                    intent2.putExtra("Content" , ArtColl.get(i).getContent());
                    intent2.putExtra("Link" , ArtColl.get(i).getLink());
                    startActivity(intent2);
                }
            });
        }


    }
}
