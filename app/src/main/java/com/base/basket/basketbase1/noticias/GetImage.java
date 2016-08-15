package com.base.basket.basketbase1.noticias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.basket.basketbase1.ImageFullScreen;
import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImage extends AsyncTask<Void, Void, Bitmap> {
    private View rowView=null;
    private ImageView ivView=null;
    private RelativeLayout preView=null;
    private String ruta;
    private static final String folder="http://bbpanel.advalleinclan.es/img/news/";

    public GetImage(View rowView, String name){
        this.rowView=rowView;
        ruta=name;
    }

    public GetImage(ImageView ivView, String name, RelativeLayout preView){
        this.ivView=ivView;
        this.preView=preView;
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
    protected void onPostExecute(final Bitmap bitmap) {
        if(bitmap!=null) {
            if(rowView!=null) {
                ImageView iv = (ImageView) rowView.findViewById(R.id.imgNoticia);
                iv.setImageBitmap(bitmap);
                rowView.findViewById(R.id.preImg).setVisibility(View.GONE);
            }
            else if(ivView!=null){
                ivView.setImageBitmap(bitmap);
                ivView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(ivView.getContext(), ImageFullScreen.class);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        byte[] bytes = stream.toByteArray();
                        in.putExtra("image", bytes);

                        ivView.getContext().startActivity(in);
                    }
                });
                preView.setVisibility(View.GONE);
            }
        }
    }
}
