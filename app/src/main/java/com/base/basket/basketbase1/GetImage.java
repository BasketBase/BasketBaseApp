package com.base.basket.basketbase1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImage extends AsyncTask<Void, Void, Bitmap> {
    private ImageView ivView=null;
    private String ruta;
    private static final String folder="http://bbpanel.advalleinclan.es/img/patros/";

    public GetImage(ImageView ivView, String name){
        this.ivView=ivView;
        ruta=name;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bm=null;

        try {
            URL imageUrl=new URL(folder+ruta);
            bm= BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null) {
            if(ivView!=null){
                ivView.setImageBitmap(bitmap);
                MainActivity.rlPreLoad.setVisibility(View.GONE);
            }
        }
    }
}
