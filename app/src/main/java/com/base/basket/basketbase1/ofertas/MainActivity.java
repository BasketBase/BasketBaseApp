package com.base.basket.basketbase1.ofertas;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ListView lista;
    public static ArrayList<String> mensajes;
    public static ArrayList<String> imagenes;
    public static ArrayList<String> titulos;
    public static ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        lista = (ListView) findViewById(R.id.listaOfertas);
        mensajes=new ArrayList<>();
        imagenes=new ArrayList<>();
        titulos=new ArrayList<>();
        urls=new ArrayList<>();

        GetDataApi gda=new GetDataApi(this, 0);
        gda.execute("ofertas?format=text");
    }

    public static void initAdapter(Context context){
        final OfertaArrayAdapter adapter = new OfertaArrayAdapter(context, titulos, mensajes, imagenes, urls);
        lista.setAdapter(adapter);
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
