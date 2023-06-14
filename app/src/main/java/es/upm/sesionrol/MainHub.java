package es.upm.sesionrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainHub extends AppCompatActivity {
    private DrawerLayout mainhub;
    private ListView listView;
    private PersonajeListAdapter persAdapter;
    private List<PersonajeEntity> personajes = new ArrayList<>();

    DataSnapshot sactual;

    DatabaseReference dbref;

    private User actualusu;
    private FirebaseFirestore db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.main_hub);

        listView=findViewById(R.id.listadoFirebase);

        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Accede a los datos de cada hijo del nodo
                    Object listPers = new ArrayList<>();
                    Object listCamp = new ArrayList<>();
                    User actualusu = new User();
                    if (snapshot.getKey().equals("characters")) {
                        listPers = snapshot.getValue();
                    }
                    if (snapshot.getKey().equals("campanas")) {
                        listCamp = snapshot.getValue();
                    }
                    if (snapshot.getKey().equals("mail")) {
                        Object email = snapshot.getValue();
                        if (act.getEmail().equals(email)) {

                            actualusu = new User((String) email, (List<PersonajeEntity>) listPers, (List<CampaignEntity>) listCamp);

                            persAdapter.addAll(actualusu.getCharacters());
                            persAdapter.notifyDataSetChanged();
                            PersonajeListAdapter personajeAdapter = new PersonajeListAdapter(MainHub.this,R.layout.character_summary, actualusu.getCharacters());
                            listView.setAdapter(personajeAdapter);
                        }
                    }
                    // Haz algo con los datos obtenidos
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error en caso de que la consulta falle
            }
        });


        if(actualusu==null||actualusu.getCharacters()==null)
            personajes = null;
        else {
            personajes = actualusu.getCharacters();
            listView = findViewById(R.id.listadoFirebase);

            persAdapter = new PersonajeListAdapter(this, R.layout.character_summary, personajes);

            listView.setAdapter(persAdapter);
        }



        mainhub = findViewById(R.id.main_hub);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainhub, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                MenuItem item = navigation_view.getCheckedItem();
                if (item != null) cambiar_pantalla(item.getItemId());
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

    public void OnBackPressed() {
        if (mainhub.isDrawerOpen(GravityCompat.START)) mainhub.openDrawer(GravityCompat.END);
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
        } else if (id_item == R.id.editar_personaje) {
            cambio = new Intent(findViewById(R.id.editar_personaje).getContext(), CreateCharacter.class);
            startActivity(cambio);
        } else {
            cambio = new Intent(findViewById(R.id.activity_main).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
}
