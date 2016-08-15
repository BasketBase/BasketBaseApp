package com.base.basket.basketbase1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageFullScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivfullscreen);

        ImageView ivRef=(ImageView) findViewById(R.id.imagenFull);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap iv = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ivRef.setImageBitmap(iv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
