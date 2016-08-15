package com.base.basket.basketbase1.noticias;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.base.basket.basketbase1.GetClubs;
import com.base.basket.basketbase1.R;

import java.util.ArrayList;

public class NoticiasFragment extends Fragment {

    public static ArrayList<String> tit_lista, subtit_lista, img_lista, cuerpo_lista;
    public static ListView lista;
    public static RelativeLayout preData;

    private static GetNoticias staticGn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView= inflater.inflate(R.layout.tab_noticias, container, false);

        preData=(RelativeLayout) fragView.findViewById(R.id.preData);
        lista = (ListView) fragView.findViewById(R.id.listaNoticias);
        tit_lista=new ArrayList<>();
        subtit_lista=new ArrayList<>();
        cuerpo_lista=new ArrayList<>();
        img_lista=new ArrayList<>();

        setIdClub(getContext(), 0);

        return fragView;
    }

    public static void initAdapter(Context context){
        final NoticiasArrayAdapter adapter = new NoticiasArrayAdapter(context, tit_lista, subtit_lista, cuerpo_lista, img_lista);
        lista.setAdapter(adapter);
        preData.setVisibility(View.GONE);
    }

    public static void setIdClub(Context context, int i) {
        preData.setVisibility(View.VISIBLE);
        tit_lista=new ArrayList<>();
        subtit_lista=new ArrayList<>();
        cuerpo_lista=new ArrayList<>();
        img_lista=new ArrayList<>();
        staticGn=new GetNoticias(context);
        if(i!=0) {
            staticGn.execute("noticias?idclub=" + i);
        }
        else{
            staticGn.execute("noticias");
        }
    }
}
