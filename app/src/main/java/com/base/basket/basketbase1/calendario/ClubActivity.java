package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class ClubActivity extends AppCompatActivity {
    public static ListView lista;
    public static ArrayList<String> nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int id;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        lista = (ListView) findViewById(R.id.listaClubs);
        nombres=new ArrayList<>();

        SharedPreferences idclub=getSharedPreferences("idclub", 0);
        id=getIntent().getIntExtra("idProv", -99);
        if(id<0){
            id=idclub.getInt("id", -99);
        }
        else{
            idclub.edit().putInt("id", id).apply();
        }

        GetClubs gc=new GetClubs(this);
        gc.execute("clubs?bb=1&idprovincia="+id);
    }

    public static void initAdapter(Context context){
        final ClubsArrayAdapter adapter = new ClubsArrayAdapter(context, nombres);
        lista.setAdapter(adapter);
    }
}
