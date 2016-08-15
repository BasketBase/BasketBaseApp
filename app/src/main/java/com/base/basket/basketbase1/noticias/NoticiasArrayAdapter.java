package com.base.basket.basketbase1.noticias;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;
import java.util.List;

public class NoticiasArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List titulos, subtitulos, cuerpos, img;

    public NoticiasArrayAdapter(Context context, ArrayList<String> titulos, ArrayList<String> subtitulos, ArrayList<String> cuerpos,ArrayList<String> img){
        super(context, -1, titulos);
        this.context=context;
        this.titulos=titulos;
        this.subtitulos=subtitulos;
        this.cuerpos=cuerpos;
        this.img=img;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.noticias, parent, false);

        TextView titulo=(TextView) rowView.findViewById(R.id.titulo);
        titulo.setText(titulos.get(position).toString());
        if(!subtitulos.get(position).toString().equals("")){
            TextView subtitulo=(TextView) rowView.findViewById(R.id.subtitulo);
            subtitulo.setText(subtitulos.get(position).toString());

            if(!img.get(position).toString().equals("")){
                GetImage gi=new GetImage(rowView, img.get(position).toString());
                gi.execute();
            }
            else{
                rowView.findViewById(R.id.preImg).setVisibility(View.GONE);
                LinearLayout llNot=(LinearLayout) rowView.findViewById(R.id.contentNoticia);
                llNot.removeView(rowView.findViewById(R.id.imgNoticia));
            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent verNoticia=new Intent(context, VerNoticia.class);
                    verNoticia.putExtra("titulo", titulos.get(position).toString());
                    verNoticia.putExtra("subtitulo", subtitulos.get(position).toString());
                    verNoticia.putExtra("cuerpo", cuerpos.get(position).toString());
                    verNoticia.putExtra("imagen", img.get(position).toString());
                    verNoticia.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(verNoticia);
                }
            });
        }
        else{
            rowView.findViewById(R.id.preImg).setVisibility(View.GONE);
            titulo.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        }

        return rowView;
    }
}