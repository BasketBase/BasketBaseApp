package com.base.basket.basketbase1.utils;

public class Coding {
    private static String newLine(String text){
        String[] textSplit=text.split("\\\\n");
        String textFinal="";
        if(textSplit.length>1) {
            for (int i = 0; i < textSplit.length; i++) {
                textFinal += textSplit[i] + System.getProperty("line-separator");
            }
        }
        else{
            textFinal=text;
        }
        return textFinal;
    }

    private static String tab(String text){
        String[] textSplit=text.split("\\\\t");
        String textFinal="";
        if(textSplit.length>1) {
            for (int i = 0; i < textSplit.length; i++) {
                textFinal += textSplit[i] + "\t";
            }
        }
        else{
            textFinal=text;
        }

        return textFinal;
    }

    private static String quote(String text){
        String[] textSplit=text.split("\\\\\'");
        String textFinal="";
        if(textSplit.length>1) {
            for (int i = 0; i < textSplit.length; i++) {
                textFinal += textSplit[i] + "\'";
            }
        }
        else{
            textFinal=text;
        }

        return textFinal;
    }

    private static String doubleQuotes(String text){
        String[] textSplit=text.split("\\\\\"");
        String textFinal="";
        if(textSplit.length>1) {
            for (int i = 0; i < textSplit.length; i++) {
                textFinal += textSplit[i] + "\"";
            }
        }
        else{
            textFinal=text;
        }

        return textFinal;
    }

    private static String slash(String text){
        String[] textSplit=text.split("\\\\/");
        String textFinal="";
        if(textSplit.length>1) {
            for (int i = 0; i < textSplit.length; i++) {
                textFinal += textSplit[i] + "/";
            }
        }
        else{
            textFinal=text;
        }

        return textFinal;
    }

    public static String encodingUTF8(String text){
        String textFinal="";
        textFinal=newLine(text);
        textFinal=tab(textFinal);
        textFinal=quote(textFinal);
        textFinal=doubleQuotes(textFinal);
        textFinal=slash(textFinal);

        return textFinal;
    }

    public static String fixUrl(String url){
        String[] urlSplit=url.split("\\\\");
        String urlFinal="";
        for(int i=0; i<urlSplit.length; i++){
            urlFinal+=urlSplit[i];
        }

        return urlFinal;
    }
}
