package com.developer.aditya.internapp;

import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String dataUrl = " https://test-api.nevaventures.com/";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.prototype_cell);
        new InternAppAsyncTask().execute(createUrl(dataUrl));
    }

    public void updateUI(String jsonData){

        HashMap<Integer,MyName> myNameHashMap = new HashMap<>();
        ArrayList<MyName> myNameArrayList = new ArrayList<>();
        myNameHashMap=QueryUtils.extractRecord(jsonData);
        Log.d("myNameHAshMAp", "updateUI: "+myNameArrayList);
        for (Map.Entry<Integer,MyName> eachName: myNameHashMap.entrySet()) {
            myNameArrayList.add(eachName.getValue());
        }
        AdapterClass itemAdapter = new AdapterClass(this,myNameArrayList);
        listView.setAdapter(itemAdapter);

    }

  public class InternAppAsyncTask extends AsyncTask<URL,Void,String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL url = createUrl(dataUrl);
            String jsonResponse = "";
            try{
                jsonResponse = makeHttpRequest(url);
                Log.d("json data-------------", "doInBackground: "+jsonResponse);
            }catch (IOException e){

            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse){
            progressDialog.dismiss();
            if(jsonResponse==null){
                return;
            }
            updateUI(jsonResponse);
        }

    }
    private String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line= reader.readLine();
            }
        }
        return output.toString();
    }
    private String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException e){
            Log.e("Exception", "makeHttpRequest: ", e.getCause());
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    public URL createUrl(String stringUrl){
        URL url;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e("exception", "createUrl: ",e );
            return null;
        }
        return url;
    }

  /*  private class GetNameAsyncTask extends AsyncTask<URL,Void,Event>{


        @Override
        protected Event doInBackground(URL... params) {
            URL url = createUrl()
        }
    }*/
}
