package com.tringapps.xmlparsingwithvolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;

/**
 * Created by geethu on 9/12/16.
 */

public class CustomAdapter extends BaseAdapter {


    ArrayList<ChannelItem> arraytListOfItems;
    Context context;
    LayoutInflater inflater;
    Bitmap[] bitmap;
    private ImageLoader mImageLoader;

    public CustomAdapter(MainActivity mainActivity, ArrayList<ChannelItem> itemsArrayList) {

        context = mainActivity;
        arraytListOfItems = itemsArrayList;
        bitmap = new Bitmap[arraytListOfItems.size()];


    }

    @Override
    public int getCount() {
        return arraytListOfItems.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MyHolderClass holderTag;

        if(view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.substitute,null);
            holderTag = new MyHolderClass(view);
            view.setTag(holderTag);

        }
        else
        {
            holderTag = (MyHolderClass) view.getTag();
        }


        holderTag.title.setText(arraytListOfItems.get(i).title);
        holderTag.description.setText(arraytListOfItems.get(i).description.trim());
        holderTag.author.setText(arraytListOfItems.get(i).author.trim());
        holderTag.pubDate.setText(arraytListOfItems.get(i).pubDate);


            mImageLoader = CustomVolleyRequestQueue.getInstance(context)
                    .getImageLoader();
            holderTag.image.setImageUrl(arraytListOfItems.get(i).url, mImageLoader);

        return view;
        }

    private class MyHolderClass {
        TextView title;
        TextView description;
        TextView author;
        TextView pubDate;
        NetworkImageView image;

        public MyHolderClass(View view) {

            title = (TextView) view.findViewById(R.id.titleView);
            description = (TextView) view.findViewById(R.id.descriptionView);
            author = (TextView) view.findViewById(R.id.authorView);
            pubDate = (TextView) view.findViewById(R.id.pubDateView);
            image = (NetworkImageView) view.findViewById(R.id.imageView);


        }

    }



}

