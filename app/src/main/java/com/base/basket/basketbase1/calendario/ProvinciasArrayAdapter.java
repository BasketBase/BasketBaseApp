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

public class ProvinciasArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List names;

    public ProvinciasArrayAdapter(Context context, ArrayList<String> names){
        super(context, -1, names);
        this.context=context;
        this.names=names;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.provincias, parent, false);
        final SharedPreferences idProvs = context.getSharedPreferences("IdProvs", 0);

        TextView nombre=(TextView) rowView.findViewById(R.id.nombre);
        nombre.setText(names.get(position).toString());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clubs=new Intent(context, ClubActivity.class);
                clubs.putExtra("idProv", idProvs.getInt("id"+position, -99));
                context.startActivity(clubs);
            }
        });

        return rowView;
    }
}