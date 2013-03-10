package org.geekhub.shuUA.rssreader.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.fragment.LikeListFragment;

public class LikeListActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.likelist_act);
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (savedInstanceState == null) {
            LikeListFragment likeListFragment = new LikeListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.likelist_frag, likeListFragment).commit();
        }
    }
}
