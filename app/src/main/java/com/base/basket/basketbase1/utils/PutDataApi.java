package com.base.basket.basketbase1.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.basket.basketbase1.MainActivity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class PutDataApi extends AsyncTask<String, Void, Void>{
    Context that;

    public PutDataApi(Context that){
        this.that=that;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            client.execute(new HttpGet(params[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(that, "Resultado enviado. ¡¡Gracias por ayudarnos!!", Toast.LENGTH_SHORT).show();
        Intent recarga=new Intent(that, MainActivity.class);
        recarga.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        recarga.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        that.startActivity(recarga);
    }
}
