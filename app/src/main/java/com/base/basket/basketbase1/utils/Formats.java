package com.base.basket.basketbase1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formats {
    public static String getDayOfWeek(Date fecha){
        if(fecha!=null){
            Calendar c = Calendar.getInstance();
            c.setTime(fecha);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            switch(dayOfWeek){
                case 0: return "Lunes";
                case 1: return "Martes";
                case 2: return "Miércoles";
                case 3: return "Jueves";
                case 4: return "Viernes";
                case 5: return "Sábado";
                case 6: return "Domingo";
            }
        }

        return "";
    }

    public static Date toDate(String fecha){
        try {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int toInt(String puntos){
        return Integer.parseInt(puntos);
    }
}
