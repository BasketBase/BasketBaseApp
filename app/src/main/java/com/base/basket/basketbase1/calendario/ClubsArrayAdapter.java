package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;
import java.util.List;

public class ClubsArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List names;

    public ClubsArrayAdapter(Context context, ArrayList<String> names){
        super(context, -1, names);
        this.context=context;
        this.names=names;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.clubs, parent, false);
        final SharedPreferences idClubs = context.getSharedPreferences("IdClubs", 0);

        TextView nombre=(TextView) rowView.findViewById(R.id.nombre);
        nombre.setText(names.get(position).toString());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent equipos=new Intent(context, EquiposActivity.class);
                equipos.putExtra("idClub", idClubs.getInt("id"+position, -99));
                context.startActivity(equipos);
            }
        });

        return rowView;
    }
}