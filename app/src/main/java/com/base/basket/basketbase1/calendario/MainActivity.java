package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static ListView lista;
    public static ArrayList<String> nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provincias);

        lista = (ListView) findViewById(R.id.listaProvincias);
        nombres=new ArrayList<>();

        GetProvincias gp=new GetProvincias(this);
        gp.execute("provincias?club=existe");
    }

    public static void initAdapter(Context context){
        final ProvinciasArrayAdapter adapter = new ProvinciasArrayAdapter(context, nombres);
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
