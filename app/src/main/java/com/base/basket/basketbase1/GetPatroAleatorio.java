package com.base.basket.basketbase1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

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

public class GetPatroAleatorio  extends AsyncTask<String, Void, JSONArray> {
    Context that;

    public GetPatroAleatorio(Context that) {
        this.that=that;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        try {
            return readJsonFromUrl("http://bbpanel.advalleinclan.es/api/2.0/patrocinadores?random=true");
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
            try {
                String imagen=data.getJSONObject(0).getString("imagen");
                final String url=data.getJSONObject(0).getString("url");

                GetImage gi=new GetImage(MainActivity.ivPatro, imagen);
                gi.execute();

                if(url!=null && !url.equals("")){
                    MainActivity.ivPatro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse(Coding.fixUrl(url)));
                        that.startActivity(web);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
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