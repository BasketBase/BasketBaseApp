package com.base.basket.basketbase1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.calendario.GetPartidos;
import com.base.basket.basketbase1.calendario.PartidosArrayAdapter;

import java.util.ArrayList;

public class PartidosFragment extends Fragment {
    public static ListView lista;
    public static ArrayList<String> fechas, local, visitante, categorias;
    public static ArrayList<Integer> idpartido, ptsLoc, ptsVis, ptsProvLoc, ptsProvVis;
    public static RelativeLayout preData;

    private static GetPartidos staticGp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView=inflater.inflate(R.layout.tab_marcadores, container, false);

        lista = (ListView) rowView.findViewById(R.id.listaPartidos);
        preData=(RelativeLayout) rowView.findViewById(R.id.preData);

        setIdClub(getContext(), 0);

        return rowView;
    }

    public static void initAdapter(Context context){
        final PartidosArrayAdapter adapter = new PartidosArrayAdapter(context, idpartido, fechas, categorias, local, visitante, ptsLoc, ptsVis, ptsProvLoc, ptsProvVis);
        lista.setAdapter(adapter);
        preData.setVisibility(View.GONE);
    }

    public static void setIdClub(Context context, int i) {
        preData.setVisibility(View.VISIBLE);
        idpartido=new ArrayList<>();
        fechas=new ArrayList<>();
        categorias=new ArrayList<>();
        local=new ArrayList<>();
        visitante=new ArrayList<>();
        ptsLoc=new ArrayList<>();
        ptsVis=new ArrayList<>();
        ptsProvLoc=new ArrayList<>();
        ptsProvVis=new ArrayList<>();

        staticGp=new GetPartidos(context, 0);
        staticGp.execute("marcadores?idclub=" + i);
    }
}