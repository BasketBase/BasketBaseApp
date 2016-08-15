package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;
import java.util.List;

public class EquiposArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List names, cats;

    public EquiposArrayAdapter(Context context, ArrayList<String> names, ArrayList<String> cats){
        super(context, -1, names);
        this.context=context;
        this.names=names;
        this.cats=cats;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.equipos, parent, false);

        final SharedPreferences equiposFav=context.getSharedPreferences("Favs", 0);

        TextView nombre=(TextView) rowView.findViewById(R.id.nombre);
        TextView cate=(TextView) rowView.findViewById(R.id.categoria);
        final ImageView fav=(ImageView) rowView.findViewById(R.id.favorito);

        final int id=EquiposActivity.ids.get(position);
        if(equiposFav.getInt("id"+id, -99)==id){
            fav.setImageResource(android.R.drawable.btn_star_big_on);
        }

        nombre.setText(names.get(position).toString());
        cate.setText(cats.get(position).toString());

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equiposFav.getInt("id"+id, -99)==id){
                    fav.setImageResource(android.R.drawable.btn_star_big_off);
                    equiposFav.edit().remove("id"+id).apply();
                }
                else{
                    fav.setImageResource(android.R.drawable.btn_star_big_on);
                    equiposFav.edit().putInt("id"+id, id).apply();
                }
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent partidos=new Intent(context, PartidosActivity.class);
                partidos.putExtra("idEquipo", id);
                context.startActivity(partidos);
            }
        });

        return rowView;
    }
}