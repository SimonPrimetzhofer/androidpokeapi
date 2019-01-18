package com.example.niki.pokeapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bm = null;

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bm = BitmapFactory.decodeStream(input);
        }catch (Exception e){
            Log.d("DownloadImageTask",e.getMessage());
        }
        return bm;
    }
}
