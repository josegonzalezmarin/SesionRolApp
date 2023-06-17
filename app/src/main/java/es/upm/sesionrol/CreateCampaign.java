package es.upm.sesionrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateCampaign extends AppCompatActivity {
    private DrawerLayout cCampaign;

    private Button newPlayer;
    private DatabaseReference dbreff;
    private ListView listView;
    private CampaignListAdapter persAdapter;
    private List<CampaignEntity> campanas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);


        listView = findViewById(R.id.jugadoresAniadidos);

        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        dbreff = FirebaseDatabase.getInstance().getReference("User");

        dbreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<PersonajeEntity> listPer = new ArrayList<>();
                    User actualusu = new User();
                    CampaignEntity cam = new CampaignEntity();
                    Gson gson = new Gson();
                    Object email = snapshot.child("mail").getValue();
                    if (act.getEmail().equals(email)) {
                        for (DataSnapshot childSnapshot : snapshot.child("personaje").getChildren()) {
                            HashMap<String, Object> data = (HashMap<String, Object>) childSnapshot.getValue();
                            String json = gson.toJson(data);
                            cam = gson.fromJson(json, CampaignEntity.class);
                            campanas.add(cam);
                        }

                        actualusu = new User((String) email, listPer, campanas);


                        persAdapter = new CampaignListAdapter(CreateCampaign.this, R.layout.campaign_summary, campanas);
                        listView.setAdapter(persAdapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        cCampaign = findViewById(R.id.createcampaign);

        newPlayer =

                findViewById(R.id.newplayer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigation_view = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, cCampaign, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                MenuItem item = navigation_view.getCheckedItem();
                if (item != null) cambiar_pantalla(item.getItemId());
            }

        };
        toggle.setDrawerIndicatorEnabled(true);
        cCampaign.addDrawerListener(toggle);
        toggle.syncState();

        navigation_view.setNavigationItemSelectedListener(item ->

        {
            navigation_view.setCheckedItem(item);
            cCampaign.closeDrawer(GravityCompat.START);
            return false;
        });

        newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(CreateCampaign.this);
                popup.setTitle("Introducir valor");
                EditText editText = new EditText(CreateCampaign.this);
                popup.setView(editText);
                popup.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String valorIntroducido = editText.getText().toString();
                        // Realiza las acciones necesarias con el valor introducido
                        // ...
                    }
                });
            }
        });
    }

    public void OnBackPressed() {
        if (cCampaign.isDrawerOpen(GravityCompat.START))
            cCampaign.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }


    public void cambiar_pantalla(int id_item) {
        Intent cambio;
        if (id_item == R.id.principal) {
            cambio = new Intent(findViewById(R.id.principal).getContext(), MainHub.class);
            startActivity(cambio);
        } else if (id_item == R.id.crear_campana) {
            cambio = new Intent(findViewById(R.id.crear_campana).getContext(), CreateCampaign.class);
            startActivity(cambio);
        } else if (id_item == R.id.crear_personaje) {
            cambio = new Intent(findViewById(R.id.crear_personaje).getContext(), CreateCharacter.class);
            startActivity(cambio);
        } else {
            cambio = new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
}

