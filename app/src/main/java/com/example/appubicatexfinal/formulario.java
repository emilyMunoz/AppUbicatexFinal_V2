package com.example.appubicatexfinal;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;
import static java.lang.Double.parseDouble;

public class formulario extends AppCompatActivity implements View.OnClickListener,
        PermissionsListener, OnMapReadyCallback {

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

    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Button selectLocationButton;
    private PermissionsManager permissionsManager;
    private ImageView hoveringMarker;
    private Layer droppedMarkerLayer;
    private String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_formulario);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        textCodigo = findViewById(R.id.lcodigo);
        textlongitud = findViewById(R.id.longitud);
        txtlatitud = findViewById(R.id.latitud);
        txtnombre = findViewById(R.id.name);
        nombreusuario = getIntent().getExtras().getString("usuario");
        FloatingActionButton fabf = findViewById(R.id.fabformulario);

        fabf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(formulario.this,PruebaMenu.class);
                in.putExtra("usuario",nombreusuario);
                startActivity(in);
            }
        });

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

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        formulario.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                enableLocationPlugin(style);

// Toast instructing user to tap on the mapboxMap
                Toast.makeText(
                        formulario.this,
                        "Instrucciones", Toast.LENGTH_SHORT).show();

// When user is still picking a location, we hover a marker above the mapboxMap in the center.
// This is done by using an image view with the default marker found in the SDK. You can
// swap out for your own marker image, just make sure it matches up with the dropped marker.
                hoveringMarker = new ImageView(formulario.this);
                hoveringMarker.setImageResource(R.drawable.circle_marker_o);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                hoveringMarker.setLayoutParams(params);
                mapView.addView(hoveringMarker);

// Initialize, but don't show, a SymbolLayer for the marker icon which will represent a selected location.
                initDroppedMarker(style);

// Button for user to drop marker or to pick marker back up.
                selectLocationButton = findViewById(R.id.select_location_button);
                selectLocationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hoveringMarker.getVisibility() == View.VISIBLE) {

// Use the map target's coordinates to make a reverse geocoding search
                            final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;

                            txtlatitud.setText(mapTargetLatLng.getLatitude() + "");
                            textlongitud.setText(mapTargetLatLng.getLongitude() + "");

// Hide the hovering red hovering ImageView marker
                            hoveringMarker.setVisibility(View.INVISIBLE);

// Transform the appearance of the button to become the cancel button
                            selectLocationButton.setBackgroundColor(
                                    ContextCompat.getColor(formulario.this,
                                            R.color.common_google_signin_btn_text_light_pressed));
                            selectLocationButton.setText("Seleccionar otro Punto");


// Show the SymbolLayer icon to represent the selected map location
                            if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                                GeoJsonSource source = style.getSourceAs("dropped-marker-source-id");
                                if (source != null) {
                                    source.setGeoJson(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));
                                }
                                droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
                                if (droppedMarkerLayer != null) {
                                    droppedMarkerLayer.setProperties(visibility(VISIBLE));
                                }
                            }

// Use the map camera target's coordinates to make a reverse geocoding search
                            //reverseGeocode(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));

                        } else {

                            txtlatitud.setText("");
                            textlongitud.setText("");

// Switch the button appearance back to select a location.
                            selectLocationButton.setBackgroundColor(
                                    ContextCompat.getColor(formulario.this,
                                            R.color.colorAccent));
                            selectLocationButton.setText("Seleccionar un Punto");

// Show the red hovering ImageView marker
                            hoveringMarker.setVisibility(View.VISIBLE);

// Hide the selected location SymbolLayer
                            droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
                            if (droppedMarkerLayer != null) {
                                droppedMarkerLayer.setProperties(visibility(NONE));
                            }
                        }
                    }
                });
            }
        });
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
// Add the marker image to map
        loadedMapStyle.addImage("dropped-icon-image", BitmapFactory.decodeResource(
                getResources(), R.drawable.x_circle));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer(DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                visibility(NONE),
                iconAllowOverlap(true)
        ));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted && mapboxMap != null) {
            Style style = mapboxMap.getStyle();
            if (style != null) {
                enableLocationPlugin(style);
            }
        } else {
            //Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            //finish();
        }
    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationPlugin(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component. Adding in LocationComponentOptions is also an optional
// parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(
                    this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

}
