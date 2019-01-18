package com.example.niki.pokeapi;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends Activity {

    public static final String TAG = DetailActivity.class.getSimpleName();
    private String url;
    private JSONObject pokemon;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.url = getIntent().getStringExtra("URL");
        Log.d("URL", this.url);
        getData();
    }

    private void getData(){
        DownloadListTask pokemonTask = new DownloadListTask(){
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                try {
                    pokemon = new JSONObject(result);
                    fill();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        pokemonTask.execute(this.url);
    }

    private void fill(){
        Log.d("Pokemon", pokemon.toString());

        DownloadImageTask imageTask = new DownloadImageTask(){
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                try{
                    image = findViewById(R.id.pokemonImage);
                    image.setImageBitmap(bitmap);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        //Set name and type of pokemon
        TextView name = findViewById(R.id.name);
        TextView type = findViewById(R.id.type);
        try {
            name.setText(pokemon.getString("name").toString());
            JSONArray typesArray = pokemon.getJSONArray("types");
            for(int i = 0; i < typesArray.length(); i++) {
                type.setText(type.getText() + typesArray.getJSONObject(i).getJSONObject("type").getString("name").toString() + "\n");
            }
            imageTask.execute(pokemon.getJSONObject("sprites").getString("front_default"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // image.setImageResource();
    }

    public void backButtonClicked(View view){
        setResult(17);
        Intent intent = new Intent();
        // intent.putExtra(DATA, "Ergebnis");
        setResult(17, intent);
        finish();
    }

}
