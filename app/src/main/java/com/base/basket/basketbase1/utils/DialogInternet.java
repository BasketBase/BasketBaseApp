package com.base.basket.basketbase1.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.base.basket.basketbase1.R;

public class DialogInternet extends DialogFragment{
    public DialogInternet(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.conexionFallo)
                .setMessage(R.string.textoConexionFallo)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                .setPositiveButton(R.string.salir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Build.VERSION.SDK_INT>=21)
                            getActivity().finishAndRemoveTask();
                        else
                            getActivity().finish();
                    }
                });

        return builder.create();
    }
}
