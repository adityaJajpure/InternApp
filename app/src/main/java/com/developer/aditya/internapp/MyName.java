package com.developer.aditya.internapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 9/2/18.
 */


public class MyName {
    private String name,skills,imageId;
    public static URL createUrl(String stringUrl){
        URL url;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e("exception", "createUrl: ",e );
            return null;
        }
        return url;
    }
    public MyName(String name,String skills,String imageId)
    {
        this.name=name;
        this.skills=skills;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public String getSkills(){
        return skills;
    }
    public String getImageString() {
        return imageId;
    }


}
