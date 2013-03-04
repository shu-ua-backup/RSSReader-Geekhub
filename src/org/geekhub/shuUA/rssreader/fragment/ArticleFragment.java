package org.geekhub.shuUA.rssreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.object.Article;

/**
 * Created with IntelliJ IDEA.
 * User: shu
 * Date: 13.02.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */

public class ArticleFragment extends SherlockFragment {
    private static String link, title, content;
    TextView tw;
    public Article article;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_frag,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        link = getActivity().getIntent().getStringExtra("Link");
        title = getActivity().getIntent().getStringExtra("Title");
        content = getActivity().getIntent().getStringExtra("Content");
        getSherlockActivity().getSupportActionBar().setTitle(title);
        tw = (TextView) getView().findViewById(R.id.art_content);
        tw.setText(content);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                item.setIcon(R.drawable.ic_menu_liked);
                Like();
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.putExtra(Intent.EXTRA_TITLE,title);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);    //To change body of overridden methods use File | Settings | File Templates.
        inflater.inflate(R.menu.article_menu, menu);
    }

    private void Like() {
       article.saveToDB(getActivity());
    }



}
