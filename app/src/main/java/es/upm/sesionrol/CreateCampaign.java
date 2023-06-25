package es.upm.sesionrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateCampaign extends AppCompatActivity {
    private DrawerLayout cCampaign;

    private ImageView image;
    private StorageReference stref;
    private String storage_path = "profilepic/*";
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    private String photo = "photo";

    private Button newPlayer;
    private Button saveCamp;
    private FirebaseAuth aut;
    private FirebaseUser act;
    private DatabaseReference dbreff;
    private TextView textV;
    private CampaignListAdapter persAdapter;
    private List<CampaignEntity> campanas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);


        textV = findViewById(R.id.jugadoresAniadidos);
        saveCamp = findViewById(R.id.saveCamp);
        image = findViewById(R.id.campPic);

        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
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
                        //listView.setAdapter(persAdapter);
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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });

        saveCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> jug = new ArrayList<>();
                CampaignEntity insertarc = new CampaignEntity("", "", jug);
                if (image_url != null) {
                    subirPhoto(image_url, insertarc.getName());
                }

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

    public void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, COD_SEL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("image_url", "requestCode - RESULT_OK: " + requestCode + " " + RESULT_OK);
        if (resultCode == RESULT_OK && requestCode == COD_SEL_IMAGE) {
            image_url = data.getData();
            Picasso.with(this).load(image_url).into(image);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void subirPhoto(Uri image_url, String name) {
        aut = FirebaseAuth.getInstance();
        String rute_storage = storage_path + "_" + photo + "_" + aut.getUid() + "_" + name;
        StorageReference storeRef = stref.child(rute_storage);
        storeRef.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateCampaign.this, "Foto insertada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

