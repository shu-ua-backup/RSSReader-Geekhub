package utill;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.geekhub.shuUA.rssreader.R;
import org.geekhub.shuUA.rssreader.object.Article;

import java.io.*;
import java.net.URL;
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
        public TextView item1;
        public ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_style, null);
            holder = new ViewHolder();
            holder.item1 = (TextView) v.findViewById(R.id.menu_title);
            holder.imageView = (ImageView) v.findViewById(R.id.img_title);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
        final Article article = entries.get(position);
        if (article != null) {
            holder.item1.setText(article.getTitle());

            if (article.getImgLink() != null) {
                Bitmap bitmap = null;
                try {

                    // dont work correct!

                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(article.getImgLink()).getContent());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100,100,false);
                    holder.imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        }
        return v;
    }


}