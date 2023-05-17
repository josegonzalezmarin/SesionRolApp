package es.upm.sesionrol;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainHub extends AppCompatActivity {
    private DrawerLayout mainhub;
    private MenuItem crearcampana;
    private DatabaseReference dbref;
    private ValueEventListener velistener;
    private ListView listView;
    private PersonajeListAdapter persAdapter;
    private List<PersonajeEntity> personajes;



    private FirebaseFirestore db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.main_hub);

        personajes =new ArrayList<>();
        listView = findViewById(R.id.listadoFirebase);

        cargarDatosDesdeFirestore();


        persAdapter = new PersonajeListAdapter(this,personajes);
        listView.setAdapter(persAdapter);

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
            cambio= new Intent(findViewById(R.id.crear_personaje).getContext(), CreateCharacter.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.editar_personaje){
            cambio= new Intent(findViewById(R.id.editar_personaje).getContext(), CreateCharacter.class);
            startActivity(cambio);
        }
        else{
            cambio= new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
    /*public void cargarDatosDesdeFirebase(){
        dbref = FirebaseDatabase.getInstance().getReference().child("personaje/idcEnanoGuerrero");
        dbref.setValue("idcEnanoGuerrero");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot s : ds.getChildren()) {

                    String aligm = s.child("aligm").getValue(String.class);
                    String backg = s.child("backg").getValue(String.class);
                    String bond = s.child("bond").getValue(String.class);
                    int charism = s.child("charism").getValue(Integer.class);
                    String competences = s.child("competences").getValue(String.class);
                    int constit = s.child("constit").getValue(Integer.class);
                    int dex = s.child("dex").getValue(Integer.class);
                    String dndclass = s.child("dndclass").getValue(String.class);
                    String equipment = s.child("equipment").getValue(String.class);
                    int exp = s.child("exp").getValue(Integer.class);
                    String feature = s.child("feature").getValue(String.class);
                    String flaws = s.child("flaws").getValue(String.class);
                    String ideal = s.child("ideal").getValue(String.class);
                    int intel = s.child("intel").getValue(Integer.class);
                    int lvl = s.child("lvl").getValue(Integer.class);
                    String name = s.child("name").getValue(String.class);
                    String personality = s.child("personality").getValue(String.class);
                    String race = s.child("race").getValue(String.class);
                    int str = s.child("str").getValue(Integer.class);
                    int wisd = s.child("wisd").getValue(Integer.class);

                    PersonajeEntity p = new PersonajeEntity( name,  race,  dndclass,  lvl,  str,  dex,  constit,  intel,  wisd,  charism);
                    personajes.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Se invoca cuando se produce un error en la lectura de los datos
                Log.e("TAG", "Error en la lectura: " + databaseError.getMessage());
            }


        });
    }*/

    private void cargarDatosDesdeFirestore() {
        db.collection("personaje")
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        personajes = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            PersonajeEntity personaje = documentSnapshot.toObject(PersonajeEntity.class);
                            personajes.add(personaje);
                        }

                        // Aquí puedes actualizar tu ListView o RecyclerView con los datos
                        // y crear las tarjetas de forma dinámica.
                        // Puedes usar el adaptador o manipular directamente la vista.

                        // Por ejemplo, si estás usando ListView:

                        Log.d("Lista nosesivacia",personajes.size()+"");
                        persAdapter.addAll(personajes);
                        persAdapter.notifyDataSetChanged();
                        PersonajeListAdapter personajeAdapter = new PersonajeListAdapter(MainHub.this, personajes);
                        listView.setAdapter(personajeAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AAAAAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAAError al obtener los datos de Firestore", e);
                    }
                });
    }
}
