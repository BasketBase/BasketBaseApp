package com.base.basket.basketbase1.patros;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static ArrayList<String> datos_lista=null, img_lista=null, links_lista=null;
    public static ArrayList<Integer> id_lista=null;
    public static ArrayList<Bitmap> imgBm_lista;
    public static ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patros);

        lista = (ListView) findViewById(R.id.listaPatros);
        datos_lista=new ArrayList<>();
        img_lista=new ArrayList<>();
        id_lista=new ArrayList<>();
        links_lista=new ArrayList<>();
        imgBm_lista=new ArrayList<>();

        GetDataApi data=new GetDataApi(this);
        data.execute("patrocinadores");
    }

    public static void patroClick(final Context context){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verPatro=new Intent(context, VerPatro.class);
                verPatro.putExtra("id", id_lista.get(position));
                verPatro.putExtra("nombre", datos_lista.get(position));
                verPatro.putExtra("imagen", img_lista.get(position));
                verPatro.putExtra("enlace", links_lista.get(position));
                context.startActivity(verPatro);
            }
        });
    }

    public static void initAdapter(Context context){
        final PatroArrayAdapter adapter = new PatroArrayAdapter(context, datos_lista, img_lista);
        lista.setAdapter(adapter);

        patroClick(context);
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