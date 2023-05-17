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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
    private List<PersonajeEntity> personajes;



    private FirebaseFirestore db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.main_hub);

        personajes =new ArrayList<>();
        listView = findViewById(R.id.listadoFirebase);

        cargarDatosDesdeFirestore();

        /*db.collection("personajes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("FirestoreListener", "Error al escuchar cambios", error);
                    return;
                }
                if (value != null && !value.isEmpty()) {
                    // Se han actualizado los documentos en la colección, puedes procesar los nuevos datos aquí
                    personajes = value.toObjects(PersonajeEntity.class);
                    // Realiza las acciones necesarias con la lista de personajes actualizada



                    // Por ejemplo, si estás usando ListView:

                    Log.d("Lista nosesivacia",personajes.size()+"");
                    persAdapter.addAll(personajes);
                    persAdapter.notifyDataSetChanged();
                    PersonajeListAdapter personajeAdapter = new PersonajeListAdapter(MainHub.this, personajes);
                    listView.setAdapter(personajeAdapter);
                }
            }

        });
*/
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
            cambio= new Intent(findViewById(R.id.activity_main).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }


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
