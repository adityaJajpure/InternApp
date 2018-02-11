package com.developer.aditya.internapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 9/2/18.
 */


public class AdapterClass extends ArrayAdapter<MyName> {

    Context context;

    public AdapterClass(Activity context , ArrayList<MyName> wword)
    {
        super(context,0,wword);
        this.context=context;
        //this.colorResourceId=colorResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.prototype_cell, parent, false);
        }
        MyName myString = getItem(position);

        TextView name = (TextView)listItem.findViewById(R.id.name_id);
        name.setText(myString.getName());

        TextView skills = (TextView)listItem.findViewById(R.id.skills_id);
        skills.setText(myString.getSkills());

        ImageView imageView = (ImageView)listItem.findViewById(R.id.img_id);
        if(myString.getImageString().equalsIgnoreCase("Not Available"))
        {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            try
            {
                Picasso.with(context).load(myString.getImageString()).into(imageView);
            }
            catch (Exception e)
            {
                Log.d("exec",e.toString());
            }
        }
        return  listItem;
    }
}
