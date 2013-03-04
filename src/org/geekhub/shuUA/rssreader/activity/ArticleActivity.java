package org.geekhub.shuUA.rssreader.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.fragment.ArticleFragment;

/**
 * Created with IntelliJ IDEA.
 * User: shu
 * Date: 13.02.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class ArticleActivity extends SherlockFragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_act);

        if (savedInstanceState == null) {
            ArticleFragment articleFragment = new ArticleFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.article_frag, articleFragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
