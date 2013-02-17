package org.geekhub.shuUA.rssreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.shuUA.rssreader.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_frag,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        link = getActivity().getIntent().getStringExtra("Link");
        title = getActivity().getIntent().getStringExtra("Title");
        content = getActivity().getIntent().getStringExtra("Content");
        getSherlockActivity().getSupportActionBar().setTitle(title);
        tw = (TextView) getView().findViewById(R.id.art_content);
        tw.setText(content);
    }
}
