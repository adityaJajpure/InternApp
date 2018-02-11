package com.developer.aditya.internapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 9/2/18.
 */

public final class QueryUtils {

     private QueryUtils(){

    }

    public static HashMap<Integer, MyName> extractRecord(String sampleJsonResponse){
        HashMap<Integer,MyName> result = new HashMap<>();
        try {
            JSONObject baseJSONObject = new JSONObject(sampleJsonResponse);
            JSONArray nameArray = baseJSONObject.getJSONArray("data");
            for (int i = 0; i < nameArray.length(); i++) {
                JSONObject mainObject = nameArray.getJSONObject(i);
                if(mainObject.has("id")){
                int id = mainObject.getInt("id");
                if(!result.containsKey(id)){
                    Log.d("id is", "extractRecord: "+id);
                    String name="Not Available",skills="Not Available",image="Not Available";
                    if(mainObject.has("name")) name=mainObject.getString("name");
                    if(mainObject.has("skills")) skills=mainObject.getString("skills");
                    if(mainObject.has("image")) {image=mainObject.getString("image");
                        Log.d("image : ", "extractRecord: "+image);}
                    result.put(id,new MyName(name,skills,image));
                }
                    
                }

            }
        }
        catch (JSONException e){
            Log.e("Query Utils : ", "extractRecord Problem: ", e);

        }
        return result;
    }
}


