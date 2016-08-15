package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class GetClubs extends AsyncTask<String, Void, JSONArray> {
    Context that;

    public GetClubs(Context that){
        this.that=that;
    }
    @Override
    protected JSONArray doInBackground(String... params) {
        try {
            return readJsonFromUrl("http://bbpanel.advalleinclan.es/api/2.0/" + params[0]);
        }catch (JSONException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray data) {
        //Filtro de error en la consulta
        if(data!=null){
            //Filtro de tamaño de datos
            if(data.length()>0) {
                String nombre;
                int id;
                SharedPreferences idClubs = that.getSharedPreferences("IdClubs", 0);
                for(int i=0; i<data.length(); i++) {
                    try {
                        nombre = data.getJSONObject(i).getString("nombre");
                        id = data.getJSONObject(i).getInt("id");
                        idClubs.edit().putInt("id"+i, id).apply();
                        ClubActivity.nombres.add(nombre);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ClubActivity.initAdapter(that);
            }
            else{
                ClubActivity.nombres.add("No hay clubs disponibles.");
                ClubActivity.initAdapter(that);
            }
        }
        else{
            Toast.makeText(that, "Hubo un error en el servidor. Disculpe las molestias.", Toast.LENGTH_SHORT).show();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        } finally {
            is.close();
        }
    }
}