package com.example.appubicatexfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appubicatexfinal.model.Marcador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class Sodas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MapView mapView;
    private DatabaseReference mDatabase;
    private MapboxMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoiam9yZGFuMjM5NCIsImEiOiJjazFqczR0c20xMGg3M3BqeXlzbm5jZWF6In0.W5GGmyAO8gbVBKjf-3vxRQ");
        setContentView(R.layout.activity_sodas);




        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override

            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                // mMap = mapboxMap;

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });
                //mapboxMap.addMarker(new MarkerOptions()
                // .position(new LatLng(10.6179731, -85.4501943))
                // .title("Local de prueba")
                // .snippet("Se vende de todo"));
                mDatabase.child("Marcadores").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Marcador mk = snapshot.getValue(Marcador.class);
                            double latitud = mk.getLatitud();
                            double longitud = mk.getLongitud();
                            String nombre = mk.getNombre();
                            int codigo = mk.getCodigo();
                            // int telefono = mk.getTelefono();
                            if (codigo == 6) {
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitud, longitud))
                                        .title(nombre));

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_home) {

            Intent intent2 = new Intent(Sodas.this,MainActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_turismo) {


        } else if (id == R.id.nav_parada) {



        }else if (id == R.id.nav_formulario){
            Intent intent3 = new Intent(Sodas.this,formulario.class);
            startActivity(intent3);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
