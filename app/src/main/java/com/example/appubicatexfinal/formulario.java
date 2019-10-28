package com.example.appubicatexfinal;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appubicatexfinal.model.Marcador;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class formulario extends AppCompatActivity implements View.OnClickListener {

    EditText textCodigo;
    EditText textlongitud;
    EditText txtlatitud;
    EditText txtnombre;
    Button btnGuardar;
    ListView lista_marcadores;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Marcador>  lisMarcador = new ArrayList<Marcador>();
    ArrayAdapter<Marcador>marcadorArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        textCodigo = findViewById(R.id.lcodigo);
        textlongitud = findViewById(R.id.longitud);
        txtlatitud = findViewById(R.id.latitud);
        txtnombre = findViewById(R.id.name);


       // spiner = findViewById(R.id.categoria);
        btnGuardar =  findViewById(R.id.btn_guardar);
        btnGuardar.setOnClickListener(this);
        lista_marcadores = findViewById(R.id.lv_datosMarcadores);

      inicializarFirebase();
      listarDatos();


    }

    private void listarDatos() {



           databaseReference.child("Marcadores").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   lisMarcador.clear();
                   for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                             Marcador m = objSnapshot.getValue(Marcador.class);
                           lisMarcador.add(m);

                           marcadorArrayAdapter = new ArrayAdapter<Marcador>(formulario.this, android.R.layout.simple_list_item_1, lisMarcador);
                           lista_marcadores.setAdapter(marcadorArrayAdapter);


                       }


               }
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
       }



    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void validacion(){

        String codigo = textCodigo.getText().toString();
        String longitud = textlongitud.getText().toString();
        String latitud = txtlatitud.getText().toString();
        String nombre = txtnombre.getText().toString();

        if (codigo.equals("")){
            textCodigo.setError("Requerido");

        } else if (longitud.equals("")){
            textlongitud.setError("Requerido");

        }else if (latitud.equals("")){
            txtlatitud.setError("Requerido");

        }else if (nombre.equals("")){
            txtnombre.setError("Requerido");

        }

    }
    private void limpiar(){
        textCodigo.setText("");
        textlongitud.setText("");
        txtlatitud.setText("");
        txtnombre.setText("");

    }

    @Override
    public void onClick(View v) {
        int codigo = Integer.parseInt(textCodigo.getText().toString());
        double longitud =Double.parseDouble(textlongitud.getText().toString());
        double latitud = Double.parseDouble(txtlatitud.getText().toString());
        String nombre = txtnombre.getText().toString();


        validacion();

        Marcador m = new Marcador();
        m.setCodigo(codigo);
        m.setLongitud(longitud);
        m.setLatitud(latitud);
        m.setNombre(nombre);

        databaseReference.child("Marcadores").child(m.getNombre()).setValue(m);
        Toast.makeText(formulario.this,"Marcador guardado exitosamente!",Toast.LENGTH_LONG).show();
        limpiar();
    }



}
