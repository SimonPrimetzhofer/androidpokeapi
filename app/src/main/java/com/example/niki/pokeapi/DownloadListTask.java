package com.example.niki.pokeapi;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadListTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        try {
            URL url = new URL(strings[0]);
            Log.d("URL",url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                result = IOUtils.toString(urlConnection.getInputStream(), "UTF-8");
            }finally {
            }
        } catch (Exception e) {
            Log.e("FAILED TO LOAD",e.getMessage(),e);
        }
        return result;
    }
}