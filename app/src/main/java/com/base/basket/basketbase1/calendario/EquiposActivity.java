package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class EquiposActivity extends AppCompatActivity{
    public static ListView lista;
    public static ArrayList<String> nombres, categorias;
    public static ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int id;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        lista = (ListView) findViewById(R.id.listaEquipos);
        nombres=new ArrayList<>();
        categorias=new ArrayList<>();
        ids=new ArrayList<>();

        SharedPreferences idequipo=getSharedPreferences("idequipo", 0);
        id=getIntent().getIntExtra("idClub", -99);
        if(id<0){
            id=idequipo.getInt("id", -99);
        }
        else{
            idequipo.edit().putInt("id", id).apply();
        }

        GetEquipos ge=new GetEquipos(this);
        ge.execute("equipos?format=text&idclub="+id);
    }

    public static void initAdapter(Context context){
        final EquiposArrayAdapter adapter = new EquiposArrayAdapter(context, nombres, categorias);
        lista.setAdapter(adapter);
    }
}
