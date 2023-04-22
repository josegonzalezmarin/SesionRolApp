package es.upm.sesionrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MainHub extends AppCompatActivity {
    private DrawerLayout mainhub;
    private MenuItem crearcampana;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_hub);
        mainhub = findViewById(R.id.main_hub);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mainhub,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerClosed(View drawerView){
                MenuItem item = navigation_view.getCheckedItem();
                if(item!=null) cambiar_pantalla(item.getItemId());
            }

        };
        toggle.setDrawerIndicatorEnabled(true);
        mainhub.addDrawerListener(toggle);
        toggle.syncState();

        navigation_view.setNavigationItemSelectedListener(item -> {
            navigation_view.setCheckedItem(item);
            mainhub.closeDrawer(GravityCompat.START);
            return false;
        });
    }
    public void OnBackPressed(){
        if(mainhub.isDrawerOpen(GravityCompat.START)) mainhub.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }


    public void cambiar_pantalla(int id_item){
        Intent cambio;
        if(id_item==R.id.principal){
            cambio = new Intent(findViewById(R.id.principal).getContext(), MainHub.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.crear_campana){
            cambio= new Intent(findViewById(R.id.crear_campana).getContext(), CreateCampaign.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.crear_personaje){
            cambio= new Intent(findViewById(R.id.crear_personaje).getContext(), CreateCampaign.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.cerrar_sesion){
            cambio= new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.editar_personaje){
            cambio= new Intent(findViewById(R.id.editar_personaje).getContext(), CreateCampaign.class);
            startActivity(cambio);
        }
        else{
            cambio= new Intent(findViewById(R.id.ajustes).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
}
