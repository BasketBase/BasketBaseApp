package com.base.basket.basketbase1.patros;

import android.content.Context;
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

public class GetDataApi extends AsyncTask<String, Void, JSONArray>{
    Context that;

    public GetDataApi(Context that) {
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
            if(data.length()>0)
                getDataListArray(data);
            else{
                emptyList();
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

    private void getDataListArray(JSONArray data){
        //Iterador
        int i=0;
        //Variables para los datos del elemento
        String nombre, imagen, enlace;
        int id;
        //Carga los datos de la base de datos
        for(i=0; i<data.length(); i++){
            try {
                nombre=data.getJSONObject(i).getString("nombre");
                imagen=data.getJSONObject(i).getString("imagen");
                id=data.getJSONObject(i).getInt("id");
                enlace=data.getJSONObject(i).getString("url");
                MainActivity.datos_lista.add(nombre);
                MainActivity.img_lista.add(imagen);
                MainActivity.id_lista.add(id);
                MainActivity.links_lista.add(enlace);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        MainActivity.initAdapter(that);
    }

    //En caso de que sea una lista vacía
    private void emptyList(){
        MainActivity.datos_lista.add("No hay patrocinadores disponibles.");

        MainActivity.initAdapter(that);
    }
}