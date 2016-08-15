package com.base.basket.basketbase1.colabora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base.basket.basketbase1.R;
import com.base.basket.basketbase1.utils.Mail;

public class MainActivity extends AppCompatActivity {
    TextView intro;
    EditText correoMail, nombreMail, personaMail, telefonoMail, provinciaMail, localidadMail,textoMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colabora);

        TextView textoNombre=(TextView)findViewById(R.id.textoNombre);
        textoNombre.setText(getIntent().getStringExtra("origen") + ":");

        Button enviarBoton=(Button)findViewById(R.id.enviarBoton);
        intro=(TextView)findViewById(R.id.textoIntro);
        if(!getIntent().getStringExtra("origen").equals("Club"))
            intro.setText("Colaborando con Basket Base, los usuarios podrán ver sus ofertas que desee y acceder a su página web.\n\nRellene este formulario o contacte directamente en el correo electónico: basketbaseapp@gmail.com");
        else
            intro.setText("Haciendo que su equipo forme parte de Basket Base, los usuarios podrán ver sus noticias y resultados al instante.\n\nRellene este formulario o contacte directamente en el correo electónico: basketbaseapp@gmail.com");
        correoMail=(EditText)findViewById(R.id.correoMail);
        nombreMail=(EditText)findViewById(R.id.nombreMail);
        personaMail=(EditText)findViewById(R.id.personaMail);
        telefonoMail=(EditText)findViewById(R.id.telefonoMail);
        textoMail=(EditText)findViewById(R.id.textoMail);
        provinciaMail=(EditText)findViewById(R.id.provinciaMail);
        localidadMail=(EditText)findViewById(R.id.localidadMail);
        enviarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo=correoMail.getText().toString();
                String nombre=nombreMail.getText().toString();
                String persona=personaMail.getText().toString();
                String telefono=telefonoMail.getText().toString();
                String provincia=provinciaMail.getText().toString();
                String localidad=localidadMail.getText().toString();
                String texto=textoMail.getText().toString();
                if(correo.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca su correo", Toast.LENGTH_SHORT).show();
                }
                else if(provincia.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca una provincia", Toast.LENGTH_SHORT).show();
                }
                else if(localidad.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca una localidad", Toast.LENGTH_SHORT).show();
                }
                else if(persona.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca una persona de contacto", Toast.LENGTH_SHORT).show();
                }
                else if(nombre.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca su nombre", Toast.LENGTH_SHORT).show();
                }
                else if(texto.equals("")){
                    Toast.makeText(getBaseContext(), "Introduzca texto", Toast.LENGTH_SHORT).show();
                }
                else {
                    Mail m=new Mail(getBaseContext());
                    m.execute(getIntent().getStringExtra("origen"), "Correo: "+correo+
                            "\n\nNombre: "+nombre+
                            "\n\nPersona de contacto: "+persona+
                            "\n\nTeléfono de contacto: "+telefono+
                            "\n\nLocalidad: "+localidad+
                            "\n\nProvincia: "+provincia+
                            "\n\nTexto:\n"+texto);
                    Toast.makeText(getBaseContext(), "Enviando...", Toast.LENGTH_SHORT).show();
                    finish();
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}