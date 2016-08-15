package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.basket.basketbase1.PartidosFragment;
import com.base.basket.basketbase1.utils.Formats;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class GetPartidos extends AsyncTask<String, Void, JSONArray> {
    Context that;
    int activity=-99;

    public GetPartidos(Context that){
        this.that=that;
    }
    public GetPartidos(Context that, int activity){
        this.that=that;
        this.activity=activity;
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
                String fecha, local, visitante, resultado, categoria;
                int ptsLoc, ptsVis, ptsProvLoc, ptsProvVis, idpartido;
                for(int i=0; i<data.length(); i++) {
                    try {
                        categoria = data.getJSONObject(i).getString("categoria");
                        fecha = data.getJSONObject(i).getString("fecha");
                        local = data.getJSONObject(i).getString("local");
                        visitante = data.getJSONObject(i).getString("visitante");
                        idpartido = data.getJSONObject(i).getInt("idpartido");
                        resultado= data.getJSONObject(i).getString("resultado");
                        if(resultado.length()>3){
                            ptsLoc = Formats.toInt(resultado.split(" - ")[0]);
                            ptsVis = Formats.toInt(resultado.split(" - ")[1]);
                            if(activity!=-99){
                                PartidosFragment.ptsLoc.add(ptsLoc);
                                PartidosFragment.ptsVis.add(ptsVis);
                                PartidosFragment.ptsProvLoc.add(null);
                                PartidosFragment.ptsProvVis.add(null);
                            }else {
                                PartidosActivity.ptsLoc.add(ptsLoc);
                                PartidosActivity.ptsVis.add(ptsVis);
                                PartidosActivity.ptsProvLoc.add(null);
                                PartidosActivity.ptsProvVis.add(null);
                            }
                        }
                        else{
                            if(activity!=-99){
                                PartidosFragment.ptsLoc.add(null);
                                PartidosFragment.ptsVis.add(null);
                                if(data.getJSONObject(i).getString("ptsProvLoc").equals("null")){
                                    PartidosFragment.ptsProvLoc.add(null);
                                    PartidosFragment.ptsProvVis.add(null);
                                }
                                else{
                                    PartidosFragment.ptsProvLoc.add(data.getJSONObject(i).getInt("ptsProvLoc"));
                                    PartidosFragment.ptsProvVis.add(data.getJSONObject(i).getInt("ptsProvVis"));
                                }
                            }
                            else {
                                PartidosActivity.ptsLoc.add(null);
                                PartidosActivity.ptsVis.add(null);
                                if(data.getJSONObject(i).getString("ptsProvLoc").equals("null")){
                                    PartidosActivity.ptsProvLoc.add(null);
                                    PartidosActivity.ptsProvVis.add(null);
                                }
                                else{
                                    PartidosActivity.ptsProvLoc.add(data.getJSONObject(i).getInt("ptsProvLoc"));
                                    PartidosActivity.ptsProvVis.add(data.getJSONObject(i).getInt("ptsProvVis"));
                                }
                            }
                        }
                        if(activity!=-99){
                            PartidosFragment.idpartido.add(idpartido);
                            PartidosFragment.categorias.add(categoria);
                            PartidosFragment.fechas.add(fecha);
                            PartidosFragment.local.add(local);
                            PartidosFragment.visitante.add(visitante);
                        }
                        else {
                            PartidosActivity.idpartido.add(idpartido);
                            PartidosActivity.fechas.add(fecha);
                            PartidosActivity.local.add(local);
                            PartidosActivity.visitante.add(visitante);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(activity!=-99){
                    PartidosFragment.initAdapter(that);
                }
                else {
                    PartidosActivity.initAdapter(that);
                }
            }
            else{
                if(activity!=-99){
                    PartidosFragment.ptsLoc.add(null);
                    PartidosFragment.ptsVis.add(null);
                    PartidosFragment.ptsProvLoc.add(null);
                    PartidosFragment.ptsProvVis.add(null);
                    PartidosFragment.idpartido.add(0);
                    PartidosFragment.categorias.add("");
                    PartidosFragment.fechas.add(null);
                    PartidosFragment.local.add("Este club no disputa partidos este fin de semana o aún no pertenece a Basket Base");
                    PartidosFragment.visitante.add("");

                    PartidosFragment.initAdapter(that);
                }
                else {
                    PartidosActivity.ptsLoc.add(null);
                    PartidosActivity.ptsVis.add(null);
                    PartidosActivity.ptsProvLoc.add(null);
                    PartidosActivity.ptsProvVis.add(null);
                    PartidosActivity.idpartido.add(0);
                    PartidosActivity.fechas.add(null);
                    PartidosActivity.local.add("No hay partidos disponibles.");
                    PartidosActivity.visitante.add("");

                    PartidosActivity.initAdapter(that);
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