package com.base.basket.basketbase1.ofertas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

import java.util.ArrayList;
import java.util.List;

public class OfertaArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List msg, img, tit, url;

    public OfertaArrayAdapter(Context context, ArrayList<String> tit, ArrayList<String> msg, ArrayList<String> img, ArrayList<String> url){
        super(context, -1, img);
        this.context=context;
        this.msg=msg;
        this.img=img;
        this.tit=tit;
        this.url=url;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.ofertas, parent, false);

        TextView mensaje=(TextView) rowView.findViewById(R.id.mensajeOf);
        TextView titulo=(TextView) rowView.findViewById(R.id.tituloOf);
        TextView tvUrl=(TextView) rowView.findViewById(R.id.urlOferta);
        titulo.setText(tit.get(position).toString());
        mensaje.setText(msg.get(position).toString());

        if(url.get(position).toString()!=null&&!url.get(position).toString().equals("")&&!url.get(position).toString().equals("null")){
            tvUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse(url.get(position).toString()));
                    context.startActivity(web);
                }
            });
        }
        else{
            tvUrl.setText("");
        }

        if(!img.get(position).toString().equals("")) {
            GetImage gi = new GetImage(rowView, img.get(position).toString());
            gi.execute();
        }
        else{
            rowView.findViewById(R.id.preImg).setVisibility(View.GONE);
        }

        return rowView;
    }
}
