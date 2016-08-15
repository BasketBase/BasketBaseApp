package com.base.basket.basketbase1.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.base.basket.basketbase1.MainActivity;
import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.utils.Formats;
import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String modal = data.getString("modal");
        String title = data.getString("title");
        String message = data.getString("message");
        String imagen = data.getString("imagen");
        String idNom = data.getString("idNom");

        sendNotification(modal, title, message, imagen, idNom);
    }
    private void sendNotification(String modal, String title, String message, String imagen, String idNom) {
        int color;

        if(Build.VERSION.SDK_INT>=23)
            color=getBaseContext().getColor(R.color.colorPrimary);
        else{
            color= Color.rgb(63, 81, 181);
        }

        Intent intent=null;

        SharedPreferences sp=getSharedPreferences("PREFERENCE", 0);

        switch (modal) {
            /*case "pruebas":
                intent = new Intent(getBaseContext(), MainActivity.class);

                sp.edit().putInt("Popup", 1).apply();
                sp.edit().putString("titulo", title).apply();
                sp.edit().putString("mensaje", message).apply();
                sp.edit().putString("imagen", imagen).apply();*/
            case "ofertas":
                intent = new Intent(getBaseContext(), com.base.basket.basketbase1.ofertas.MainActivity.class);

                sp.edit().putInt("Popup", 1).apply();
                sp.edit().putString("titulo", title).apply();
                sp.edit().putString("mensaje", message).apply();
                sp.edit().putString("imagen", imagen).apply();
                break;
            case "general":
                intent = new Intent(getBaseContext(), MainActivity.class);

                sp.edit().putInt("Popup", 2).apply();
                sp.edit().putString("titulo", title).apply();
                sp.edit().putString("mensaje", message).apply();
                sp.edit().putString("imagen", imagen).apply();
                break;
            default:
                SharedPreferences equiposFav = getSharedPreferences("Favs", 0);
                int id = Formats.toInt(idNom);
                if (equiposFav.getInt("id" + id, -99) == id)
                    intent = new Intent(getBaseContext(), MainActivity.class);
                break;
        }

        if(intent!=null){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(color)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message));

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            SharedPreferences spCont= getSharedPreferences("Contador", 0);
            int cont=spCont.getInt("i", 0);
            cont++;
            spCont.edit().putInt("i", cont).apply();

            notificationManager.notify(cont, notificationBuilder.build());
        }
    }
}