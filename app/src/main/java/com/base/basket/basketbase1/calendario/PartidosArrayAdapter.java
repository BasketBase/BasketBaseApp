package com.base.basket.basketbase1.calendario;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.utils.Formats;
import com.base.basket.basketbase1.utils.PutDataApi;
import com.base.basket.basketbase1.utils.TextFlashes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PartidosArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List ids, fechas, local, visitante, ptsLocal, ptsVis, ptsProvLoc, ptsProvVis;
    private List categorias=null;

    public PartidosArrayAdapter(Context context,
                                ArrayList<Integer> ids,
                                ArrayList<String> fechas,
                                ArrayList<String> local,
                                ArrayList<String> visitante,
                                ArrayList<Integer> ptsLocal,
                                ArrayList<Integer> ptsVis,
                                ArrayList<Integer> ptsProvLoc,
                                ArrayList<Integer> ptsProvVis){
        super(context, -1, fechas);
        this.context=context;
        this.ids=ids;
        this.fechas=fechas;
        this.local=local;
        this.visitante=visitante;
        this.ptsLocal=ptsLocal;
        this.ptsVis=ptsVis;
        this.ptsProvLoc=ptsProvLoc;
        this.ptsProvVis=ptsProvVis;
    }

    public PartidosArrayAdapter(Context context,
                                ArrayList<Integer> ids,
                                ArrayList<String> fechas,
                                ArrayList<String> categorias,
                                ArrayList<String> local,
                                ArrayList<String> visitante,
                                ArrayList<Integer> ptsLocal,
                                ArrayList<Integer> ptsVis,
                                ArrayList<Integer> ptsProvLoc,
                                ArrayList<Integer> ptsProvVis){
        super(context, -1, fechas);
        this.context=context;
        this.ids=ids;
        this.fechas=fechas;
        this.categorias=categorias;
        this.local=local;
        this.visitante=visitante;
        this.ptsLocal=ptsLocal;
        this.ptsVis=ptsVis;
        this.ptsProvLoc=ptsProvLoc;
        this.ptsProvVis=ptsProvVis;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.partidos, parent, false);

        RelativeLayout eqLocal=(RelativeLayout) rowView.findViewById(R.id.local);
        TextView fecha=(TextView) rowView.findViewById(R.id.fecha);
        TextView nomLocal=(TextView) rowView.findViewById(R.id.nomLocal);
        TextView tvPtsLocal=(TextView) rowView.findViewById(R.id.ptsLocal);
        TextView nomVis=(TextView) rowView.findViewById(R.id.nomVis);
        TextView tvPtsVis=(TextView) rowView.findViewById(R.id.ptsVis);
        LinearLayout provisional=(LinearLayout) rowView.findViewById(R.id.provisional);
        TextView enviarRes=(TextView) rowView.findViewById(R.id.enviar);
        Date partido1=null;
        Date hoy=new Date();

        if(fechas.get(position)!=null){
            String fechaBD=fechas.get(position).toString();
            Date fechaFin= Formats.toDate(fechaBD);
            Calendar fechaPartido2=Calendar.getInstance();
            try {
                fechaPartido2.setTime(new SimpleDateFormat("dd-MM-yy hh:mm").parse(fechaBD));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fechaPartido2.add(Calendar.HOUR, 1);
            partido1=fechaPartido2.getTime();
            String diaDeSemana= Formats.getDayOfWeek(fechaFin);
            String cat="";
            if(categorias!=null){
                cat=categorias.get(position)+", ";
            }

            fecha.setText(cat+diaDeSemana+" "+fechaBD);
        }

        String equipoLoc=local.get(position).toString();
        String equipoVis=visitante.get(position).toString();

        String puntosLoc="-", puntosVis="-";
        if(ptsLocal.get(position)!=null){
            puntosLoc=ptsLocal.get(position).toString();
            puntosVis=ptsVis.get(position).toString();

            if(Formats.toInt(puntosLoc)>Formats.toInt(puntosVis)){
                nomLocal.setTypeface(null, Typeface.BOLD);
                tvPtsLocal.setTypeface(null, Typeface.BOLD);
            }
            else{
                nomVis.setTypeface(null, Typeface.BOLD);
                tvPtsVis.setTypeface(null, Typeface.BOLD);
            }
        }
        else if(ptsProvLoc.get(position)!=null){
            puntosLoc=ptsProvLoc.get(position).toString();
            puntosVis=ptsProvVis.get(position).toString();
            new TextFlashes(getContext(), tvPtsLocal);
            new TextFlashes(getContext(), tvPtsVis);
        }

        if(ptsLocal.get(position)==null && partido1!=null && partido1.compareTo(hoy)<=0){
            provisional.setVisibility(View.VISIBLE);
            enviarRes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EditText etAddLocal=(EditText) rowView.findViewById(R.id.ptsProvLoc);
                    EditText etAddVis=(EditText) rowView.findViewById(R.id.ptsProvVis);
                    String newPtsLoc=etAddLocal.getText().toString();
                    String newPtsVis=etAddVis.getText().toString();
                    if(!newPtsLoc.equals("") && !newPtsVis.equals("")){
                        PutDataApi put=new PutDataApi(getContext());

                        if(ptsProvLoc.get(position)!=null){
                            put.execute("http://bbpanel.advalleinclan.es/api/upload/ptsProv?" +
                                    "idpartido="+ids.get(position)+"&"+
                                    "ptsLoc="+newPtsLoc+"&"+
                                    "ptsVis="+newPtsVis);
                        }
                        else{
                            put.execute("http://bbpanel.advalleinclan.es/api/put/ptsProv?" +
                                    "idpartido="+ids.get(position)+"&"+
                                    "ptsLoc="+newPtsLoc+"&"+
                                    "ptsVis="+newPtsVis);
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Introduce puntos en las dos casillas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(fechas.get(position)!=null) {
            tvPtsLocal.setBackgroundResource(R.color.colorMarc);
            tvPtsVis.setBackgroundResource(R.color.colorMarc);
            tvPtsLocal.setText(puntosLoc);
            tvPtsVis.setText(puntosVis);
        }

        if(equipoVis.equals("")){
            eqLocal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nomLocal.setText(equipoLoc);
        }
        else{
            nomLocal.setText(equipoLoc);
            nomVis.setText(equipoVis);
        }

        return rowView;
    }
}