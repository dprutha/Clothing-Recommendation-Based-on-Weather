package com.hackumbc.prutha.slayit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Prutha on 10/2/2016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private List<String> imageList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        String imageItem = imageList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load("file:///assets" + imageItem)
                //.error(R.drawable.placeholder)
                //.placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);

        //Setting text view title
       // customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != imageList ? imageList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}

