package com.base.basket.basketbase1.patros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;
import java.util.List;

public class PatroArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List names, img;

    public PatroArrayAdapter(Context context, ArrayList<String> names, ArrayList<String> img){
        super(context, -1, names);
        this.context=context;
        this.names=names;
        this.img=img;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.patrocinadores, parent, false);

        TextView nombre=(TextView) rowView.findViewById(R.id.nombre);
        nombre.setText(names.get(position).toString());

        GetImage gi=new GetImage(rowView, img.get(position).toString());
        gi.execute();

        return rowView;
    }
}