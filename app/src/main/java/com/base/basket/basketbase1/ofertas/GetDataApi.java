package com.base.basket.basketbase1.ofertas;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.basket.basketbase1.patros.VerPatro;
import com.base.basket.basketbase1.utils.Coding;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class GetDataApi extends AsyncTask<String, Void, JSONArray> {
    Context that;
    int clase;

    public GetDataApi(Context that, int clase) {
        this.that=that;
        this.clase=clase;
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
            if(data.length()>0){
                for(int i=0; i<data.length(); i++){
                    try {
                        String mensaje=data.getJSONObject(i).getString("mensaje");
                        String titulo=data.getJSONObject(i).getString("idpatro");
                        String imagen=data.getJSONObject(i).getString("imagen");
                        String url=data.getJSONObject(i).getString("url");
                        switch(clase){
                            case 0:
                                MainActivity.titulos.add(titulo);
                                MainActivity.mensajes.add(Coding.encodingUTF8(mensaje));
                                MainActivity.imagenes.add(imagen);
                                MainActivity.urls.add(Coding.fixUrl(url));
                            break;
                            case 1:
                                VerPatro.titulos.add(titulo);
                                VerPatro.mensajes.add(Coding.encodingUTF8(mensaje));
                                VerPatro.imagenes.add(imagen);
                                VerPatro.urls.add(Coding.fixUrl(url));
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                switch(clase){
                    case 0:
                        MainActivity.initAdapter(that);
                    break;
                    case 1:
                        VerPatro.initAdapter(that);
                    break;
                }
            }
            else{
                switch(clase){
                    case 0:
                        MainActivity.titulos.add("No hay ninguna oferta disponible en estos momentos.");
                        MainActivity.initAdapter(that);
                        break;
                    case 1:
                        VerPatro.titulos.add("No hay ninguna oferta disponible en estos momentos.");
                        VerPatro.initAdapter(that);
                        break;
                }
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