package com.base.basket.basketbase1.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail extends AsyncTask<String, Void, Void>{
    Context mContext;

    public Mail(Context mContext) {
        this.mContext = mContext;
    }

    public void sendEmail(String asunto, String texto){
        final String username = "basketbaseapp@gmail.com";
        final String password = "advalleinclan.es";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("basketbaseapp@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("basketbaseapp@gmail.com"));
            message.setSubject(asunto);
            message.setText(texto);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        sendEmail(params[0], params[1]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mContext, "Enviado correctamente", Toast.LENGTH_SHORT).show();
    }
}
