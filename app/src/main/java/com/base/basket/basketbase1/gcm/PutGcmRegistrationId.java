package com.base.basket.basketbase1.gcm;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class PutGcmRegistrationId extends AsyncTask<String, Void, Void>{
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            client.execute(new HttpGet("http://bbpanel.advalleinclan.es/api/put/reg_gcm?id="+params[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
