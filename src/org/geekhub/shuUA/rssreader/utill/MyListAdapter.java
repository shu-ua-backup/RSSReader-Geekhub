package org.geekhub.shuUA.rssreader.utill;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import org.geekhub.shuUA.rssreader.object.Article;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<Article> {
    private List<Article> entries;
    private Activity activity;

    public MyListAdapter(Activity a, int textViewResourceId, List<Article> entries) {
        super(a,textViewResourceId,entries);
        this.entries = entries;
        this.activity = a;
    }

    public static class ViewHolder{
        public TextView item1, dateTitle;
        public ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_style, null);
            holder = new ViewHolder();
            holder.item1 = (TextView) v.findViewById(R.id.menu_title);
            holder.dateTitle = (TextView) v.findViewById(R.id.date_title);
            holder.imageView = (ImageView) v.findViewById(R.id.img_title);
            v.setTag(holder);
        }
        else {
            holder=(ViewHolder)v.getTag();
        }

        Article article = entries.get(position);

        if (article != null) {
            holder.item1.setText(article.getTitle());
            holder.dateTitle.setText(getDate(article.getPubDate()));
            String imageUrl = article.getImgLink();

            File cacheDir = StorageUtils.getCacheDirectory(getContext());

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder((getContext()))
                    .memoryCacheExtraOptions(120, 80) // width, height
                    .discCacheExtraOptions(120, 80, Bitmap.CompressFormat.JPEG, 75) // width, height, compress format, quality
                    .threadPoolSize(4)
                    .threadPriority(6)
                    .imageDownloader(new BaseImageDownloader(getContext()))
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
            imageLoader.displayImage(imageUrl, holder.imageView,options);

        }
        return v;
    }

    private String getDate(Date date) {
        String str;
        long sec = (System.currentTimeMillis() - date.getTime()) / 1000;

        if (sec < 60) {
            str = Long.toString(sec) + " seconds ago";
        } else if (sec < 60*60)  {
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
