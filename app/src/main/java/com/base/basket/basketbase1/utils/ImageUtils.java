package com.base.basket.basketbase1.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.base.basket.basketbase1.ImageFullScreen;

public class ImageUtils {
    public static void verImagen(final Context ctx, final ImageView iv){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ifs=new Intent(ctx, ImageFullScreen.class);
                Bundle extras=new Bundle();
                extras.putParcelable("image", iv.getDrawingCache());
                ifs.putExtras(extras);
                ctx.startActivity(ifs);
            }
        });
    }
}
