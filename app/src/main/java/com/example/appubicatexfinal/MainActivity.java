package com.example.appubicatexfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     //public static final String user = "names";
     //TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
       // txtUser = navigationView.getHeaderView(0).findViewById(R.id.txtUser);

       //if(false){
       //     user = getIntent().getExtras().getString("usuario");
      //  }


        //txtUser.setText("marcotally95@gmail.com");

        //txtUser = (TextView)findViewById(R.id.email);
        //String user= getIntent().getStringExtra("names");
        //txtUser.setText(user);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Debes iniciar sesion o registrarse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


       /* Intent inten = getIntent();
        String message = inten.getStringExtra("user");
        TextView textView = findViewById(R.id.textemail);
        textView.setText(message);*/
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        Intent i;
        switch (item.getItemId()){

            case R.id.action_cerrarsesion:
                i = new Intent(this,InicioActivity.class);
                startActivity(i);
                break;

            //case R.id.action_inicio:
               // i = new Intent(this,InicioActivity.class);
               // startActivity(i);
               // break;
             }
             return true;
        }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentHome()).commit();

        } else if (id == R.id.nav_comercio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentComerciales()).commit();

        } else if (id == R.id.nav_turismo) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentTurismo()).commit();

        } else if (id == R.id.nav_parada) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentParadas()).commit();

        } else if(id == R.id.nav_solicitud){
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentSolicitud()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
