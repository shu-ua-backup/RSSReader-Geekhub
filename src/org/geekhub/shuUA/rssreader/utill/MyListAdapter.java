package org.geekhub.shuUA.rssreader.utill;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.db.ArticleTable;

import java.io.File;
import java.text.SimpleDateFormat;

public class MyListAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    public MyListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_style, null);
        }
        this.c.moveToPosition(pos);
        String title = this.c.getString(this.c.getColumnIndex(ArticleTable.COLUMN_TITLE));
        String date = this.c.getString(this.c.getColumnIndex(ArticleTable.COLUMN_PUBDATE));
        String imgLink = this.c.getString(this.c.getColumnIndex(ArticleTable.COLUMN_IMGLINK));

        ImageView iv = (ImageView) v.findViewById(R.id.img_title);
        if (imgLink != null) {

            File cacheDir = StorageUtils.getCacheDirectory(context);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .memoryCacheExtraOptions(120, 80) // width, height
                    .discCacheExtraOptions(120, 80, Bitmap.CompressFormat.JPEG, 75) // width, height, compress format, quality
                    .threadPoolSize(4)
                    .threadPriority(6)
                    .imageDownloader(new BaseImageDownloader(context))
                    .denyCacheImageMultipleSizesInMemory()
                    .offOutOfMemoryHandling()
                    .memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024)) // 2 Mb
                    .discCache(new UnlimitedDiscCache(cacheDir))
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .enableLogging()
                    .build();

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.err)
                    .showImageForEmptyUri(R.drawable.search)
                    .showImageOnFail(R.drawable.err)
                    .cacheInMemory()
                    .cacheOnDisc()
                    .build();

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            imageLoader.displayImage(imgLink, iv,options);

        }

        TextView tvdate = (TextView) v.findViewById(R.id.date_title);
        tvdate.setText(getDate(date));

        TextView tvtitle = (TextView) v.findViewById(R.id.menu_title);
        tvtitle.setText(title);
        return(v);
    }

    private String getDate(String strdate) {
        String str;
        long date = Long.valueOf(strdate);
        long sec = (System.currentTimeMillis() - date) / 1000;

        if (sec < 60) {
            str = Long.toString(sec) + " seconds ago";
        } else if (sec < 60*60) {
            str = Long.toString(sec/60) + " minutes ago";
        } else if (sec < 60*60*24) {
            str = Long.toString(sec/60/60) + " hours ago";
            //str = Long.toString(sec) + " hours ago";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            str = dateFormat.format(date);
        }

        return str;
    }



}