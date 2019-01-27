package com.example.niki.pokeapi;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String URL = "https://pokeapi.co/api/v2/";
    private static final int DETAIL_REQUEST_CODE = 3306;
    private ListView listView;
    private Intent intent;
    private JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, DetailActivity.class);
        getData(0);
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
        Integer pos = (Integer) v.getTag();
        String name = listView.getAdapter().getItem(pos).toString();
        try {
            String url = jsonArray.getJSONObject(pos).getString("url");
            intent.putExtra("URL", url);
            startActivityForResult(intent, DETAIL_REQUEST_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    };

    private void getData(int offset){
        DownloadListTask pokemonTask = new DownloadListTask(){
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                fillJSONArray(result);
                fillList();
            }
        };
        pokemonTask.execute(MainActivity.URL + "pokemon/?limit=949&offset="+offset+"/");
    }

    private void fillJSONArray(String result){
        Log.d("result", result);
        try{
            JSONObject pokemons = new JSONObject(result);
            Log.d("result", pokemons.toString());
            jsonArray = pokemons.getJSONArray("results");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private void fillList(){

        listView = findViewById(R.id.listview);

        ListAdapter adapter = new ListAdapter(){

            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return jsonArray.length();
            }

            @Override
            public Object getItem(int position) {
                try {
                    return jsonArray.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if(view == null){
                    view = getLayoutInflater().inflate(R.layout.cell, parent, false);
                }
                TextView pokemonName = view.findViewById(R.id.pokemonName);
                pokemonName.setText(""+(position + 1));
                TextView detailLink = view.findViewById( R.id.detailLink);
                try {
                    detailLink.setText(jsonArray.getJSONObject(position).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.setOnClickListener(listener);
                view.setTag(position);
                return view;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
        listView.setAdapter(adapter);
    }
}
