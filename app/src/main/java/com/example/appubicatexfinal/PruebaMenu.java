package com.example.appubicatexfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;

public class PruebaMenu extends AppCompatActivity implements View.OnClickListener{

    CardView cardPuperia;
    CardView cardLLantera;
    CardView cardTurismo;
    CardView cardParada;
    CardView cardSodas;
    CardView cardMarcador;
    CardView cardInicio;
    CardView cardCerrar;
    private String nombreusuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_menu);

        cardPuperia = findViewById(R.id.primero);
        cardSodas = findViewById(R.id.segundo);
        cardParada = findViewById(R.id.tercero);
        cardTurismo = findViewById(R.id.cuarto);
        cardLLantera = findViewById(R.id.quinto);
        cardMarcador = findViewById(R.id.sexto);
        cardInicio = findViewById(R.id.setimo);
        cardCerrar = findViewById(R.id.octavo);

        cardPuperia.setOnClickListener(this);
        cardSodas.setOnClickListener(this);
        cardParada.setOnClickListener(this);
        cardTurismo.setOnClickListener(this);
        cardLLantera.setOnClickListener(this);
        cardMarcador.setOnClickListener(this);
        cardInicio.setOnClickListener(this);
        cardCerrar.setOnClickListener(this);

        nombreusuario = getIntent().getExtras().getString("usuario");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.primero:
                Intent inten1 = new Intent(PruebaMenu.this,comercios.class);
                inten1.putExtra("usuario",nombreusuario);
                startActivity(inten1);
                break;

            case R.id.segundo:
                Intent inten2 = new Intent(PruebaMenu.this,Sodas.class);
                inten2.putExtra("usuario",nombreusuario);
                startActivity(inten2);
                break;

            case R.id.tercero:
                Intent inten3 = new Intent(PruebaMenu.this,Paradas.class);
                inten3.putExtra("usuario",nombreusuario);
                startActivity(inten3);
                break;

            case R.id.cuarto:
                Intent inten4 = new Intent(PruebaMenu.this,Centros_turisticos.class);
                inten4.putExtra("usuario",nombreusuario);
                startActivity(inten4);
                break;

            case R.id.quinto:
                Intent inten5 = new Intent(PruebaMenu.this,Llanteras.class);
                inten5.putExtra("usuario",nombreusuario);
                startActivity(inten5);
                break;

              case R.id.sexto:
            if (nombreusuario.equals("teampv2018@gmail.com") ){

                Intent inten6 = new Intent(PruebaMenu.this,formulario.class);
                inten6.putExtra("usuario",nombreusuario);
                startActivity(inten6);
                break;

            }else {
                Toast.makeText(PruebaMenu.this,
                      "No puedes ingresar a este modulo lo sentimos !", Toast.LENGTH_LONG).show();


            }


            case R.id.setimo:
                Intent inten7 = new Intent(PruebaMenu.this,MainActivity.class);
                inten7.putExtra("usuario",nombreusuario);
                startActivity(inten7);
                break;

            case R.id.octavo:
                Intent inten8 = new Intent(PruebaMenu.this,InicioActivity.class);
                inten8.putExtra("usuario",nombreusuario);
                startActivity(inten8);
                break;

            }

        }
}

