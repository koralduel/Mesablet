package com.example.mesablet.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mesablet.R;

public class GridAdapter extends BaseAdapter
{
    private Context context;
    private String[] images;
    private LayoutInflater inflater;

    public GridAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = inflater.inflate(R.layout.photo_item,viewGroup,false);
        ImageView imageView = view.findViewById(R.id.imageView_photo);
        imageView.setImageURI(Uri.parse(images[i]));
        return view;

    }

}
