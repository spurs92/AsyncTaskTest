package com.spurs.asynctasktest;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by alfo06-11 on 2017-06-01.
 */

public class MyAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Bitmap> bitmaps;

    public MyAdapter(LayoutInflater inflater, ArrayList<Bitmap> bitmaps) {
        this.inflater = inflater;
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_item,parent,false);
        }
        ImageView img=(ImageView)convertView.findViewById(R.id.img);
        img.setImageBitmap(bitmaps.get(position));

        return convertView;
    }
}
