package com.example.appubicatexfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegistrarse;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnIngresar =  findViewById(R.id.btn_iniciaprincipal);
        btnRegistrarse = findViewById(R.id.btn_registrarseprincipal);
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_iniciaprincipal:
                Intent inten1 = new Intent(PrincipalActivity.this,InicioActivity.class);
                startActivity(inten1);
                break;

            case R.id.btn_registrarseprincipal:
                Intent inten = new Intent(PrincipalActivity.this,RegistrarseActivity.class);
                startActivity(inten);
                break;

        }
    }
}
