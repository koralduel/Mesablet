package com.example.mesablet.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mesablet.R;
import com.example.mesablet.data.FireBase;

import java.util.List;

public class GridAdapter extends BaseAdapter
{
    private Context context;
    private List<String> images;
    private LayoutInflater inflater;

    public GridAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
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
        FireBase.downloadImage(images.get(i),imageView);
        return view;

    }

}
