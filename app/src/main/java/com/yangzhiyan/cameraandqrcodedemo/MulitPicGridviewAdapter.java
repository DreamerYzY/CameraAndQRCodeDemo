package com.yangzhiyan.cameraandqrcodedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by YZY on 2016/11/3.
 */

public class MulitPicGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<String> imagelist;
    private boolean[] selectedTag;

    public MulitPicGridviewAdapter(Context context, List<String> imagelist) {
        this.context = context;
        this.imagelist = imagelist;

        selectedTag = new boolean[imagelist.size()];
    }

    @Override
    public int getCount() {
        return imagelist.size();
    }

    @Override
    public Object getItem(int position) {
        return imagelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.mulit_gridview_item_layout,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String fileName = imagelist.get(position);
        Picasso.with(context).load(new File(fileName)).resize(330,400).into(holder.imageView);

        return convertView;
    }

    class ViewHolder{
        @ViewInject(R.id.ivpic)
        ImageView imageView;
        ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
