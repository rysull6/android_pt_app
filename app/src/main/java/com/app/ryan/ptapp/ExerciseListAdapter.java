package com.app.ryan.ptapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ryan on 4/13/2016.
 */
public class ExerciseListAdapter extends BaseAdapter {

    ArrayList<Exercise> myArrayList;
    Context c;

    public ExerciseListAdapter(Context c, ArrayList<Exercise> list) {
        myArrayList = list;
        this.c = c;

    }

    @Override
    public int getCount() {
        return myArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return myArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_list_item, null);
            holder = new ViewHolder();
            holder.labelView = (TextView) convertView.findViewById(R.id.list_item_label);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Exercise exercise = myArrayList.get(position);
        holder.labelView.setText(exercise.getTitle());

        if(holder.imageView != null){
            new GetImageFromURL(holder.imageView).execute(exercise.getImageURL());
        }

        return convertView;
    }

static class ViewHolder {
    TextView labelView;
    ImageView imageView;
}
}