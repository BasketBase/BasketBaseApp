package com.base.basket.basketbase1.noticias;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class GetNoticias extends AsyncTask<String, Void, JSONArray> {
    Context that;

    public GetNoticias(Context that){
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
            if(data.length()>1) {
                String titulo, subtitulo, cuerpo, imagen;
                for(int i=0; i<data.length(); i++) {
                    try {
                        titulo = data.getJSONObject(i).getString("titulo");
                        subtitulo = data.getJSONObject(i).getString("subtitulo");
                        cuerpo = data.getJSONObject(i).getString("cuerpo");
                        imagen = data.getJSONObject(i).getString("imagen");
                        NoticiasFragment.tit_lista.add(titulo);
                        NoticiasFragment.subtit_lista.add(subtitulo);
                        NoticiasFragment.cuerpo_lista.add(cuerpo);
                        NoticiasFragment.img_lista.add(imagen);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                NoticiasFragment.initAdapter(that);
            }
            else{
                NoticiasFragment.tit_lista.add("Este club no ha escrito noticias.");
                NoticiasFragment.subtit_lista.add("");
                NoticiasFragment.cuerpo_lista.add("");
                NoticiasFragment.img_lista.add("");
                NoticiasFragment.initAdapter(that);
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
