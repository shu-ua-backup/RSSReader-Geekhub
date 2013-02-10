package org.geekhub.shuUA.rssreader.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.fragment.NewsListFragment;

public class NewsListActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_act);
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (savedInstanceState == null) {
            NewsListFragment newsListFragment = new NewsListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.news_list_frag, newsListFragment).commit();
        }
    }
}
