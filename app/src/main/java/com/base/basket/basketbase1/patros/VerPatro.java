package com.base.basket.basketbase1.patros;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.basket.basketbase1.utils.Coding;
import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.ofertas.OfertaArrayAdapter;

import java.util.ArrayList;

public class VerPatro extends AppCompatActivity {
    public static RelativeLayout rlPreLoad;
    public static ListView lista;
    public static ArrayList<String> mensajes;
    public static ArrayList<String> imagenes;
    public static ArrayList<String> titulos;
    public static ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_patro);
        final Intent intent=getIntent();

        TextView tvNombre=(TextView) findViewById(R.id.nombre);
        ImageView ivView=(ImageView) findViewById(R.id.imagen);
        TextView tvEnlace=(TextView) findViewById(R.id.enlace);

        rlPreLoad=(RelativeLayout)findViewById(R.id.preImg);

        tvNombre.setText(intent.getStringExtra("nombre"));
        if(intent.getStringExtra("enlace")==null || intent.getStringExtra("enlace").equals("") || intent.getStringExtra("enlace").equals("null")) {
            tvEnlace.setText("");
        }
        else{
            tvEnlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(Coding.fixUrl(intent.getStringExtra("enlace"))));
                    startActivity(web);
                }
            });
        }

        lista=(ListView) findViewById(R.id.listOfPatros);
        mensajes=new ArrayList<>();
        imagenes=new ArrayList<>();
        titulos=new ArrayList<>();
        urls=new ArrayList<>();

        com.base.basket.basketbase1.ofertas.GetDataApi gda=new com.base.basket.basketbase1.ofertas.GetDataApi(this, 1);
        gda.execute("ofertas?format=text&idpatro="+intent.getIntExtra("id", -99));

        GetImage gi = new GetImage(ivView, intent.getStringExtra("imagen"));
        gi.execute();
    }

    public static void initAdapter(Context context){
        final OfertaArrayAdapter adapter = new OfertaArrayAdapter(context, titulos, mensajes, imagenes, urls);
        lista.setAdapter(adapter);
    }
}
