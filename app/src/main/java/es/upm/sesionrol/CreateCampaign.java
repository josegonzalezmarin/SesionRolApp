package es.upm.sesionrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateCampaign extends AppCompatActivity {
    private DrawerLayout cCampaign;

    private DataSnapshot ainsertar;
    private ImageView image;
    private ListView camplistView;
    private StorageReference stref;
    private String storage_path = "camppic/*";
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    private String photo = "photo";

    private Button newPlayer;
    private Button saveCamp;
    private FirebaseAuth aut;
    private FirebaseUser act;
    private DatabaseReference dbreff;
    private TextView textV;
    private CampaignListAdapter campsAdapter;
    private List<CampaignEntity> campanas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);


        textV = findViewById(R.id.jugadoresAniadidos);
        saveCamp = findViewById(R.id.saveCamp);
        image = findViewById(R.id.campPic);
        stref = FirebaseStorage.getInstance().getReference();

        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();

        Intent intent = getIntent();

        String nameg = intent.getStringExtra("name");
        String master = intent.getStringExtra("master");
        String jugs = intent.getStringExtra("jugadores");
        if(master!=null && !jugs.contains(master)) {
            jugs += " " + master;
        }
        EditText name = findViewById(R.id.insertNameCamptxt);
        TextView jug = findViewById(R.id.jugadoresAniadidos);


        name.setText(nameg);
        if(jugs!=null) {
            jug.setText(jugs);
        }
        FirebaseAuth userA = FirebaseAuth.getInstance();
        FirebaseUser user = userA.getCurrentUser();
        String imagen ="*_photo_"+"_"+nameg;
        Log.d("Nombre ", imagen);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("profilepic/"+imagen);



        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // La descarga de la URL de la imagen se completó con éxito
                // Carga la imagen en el ImageView utilizando una biblioteca de manejo de imágenes
                Picasso.with(CreateCampaign.this).load(uri).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Missing image","No tiene imagen asociada");
            }
        });

        dbreff = FirebaseDatabase.getInstance().getReference("User");

        dbreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals("mail"))
                        if (act.getEmail().equals(snapshot.getValue())) {
                            ainsertar = snapshot;
                            break;
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cCampaign = findViewById(R.id.createcampaign);

        newPlayer = findViewById(R.id.newplayer);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCampaign.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_layout, null);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                EditText editText = dialogView.findViewById(R.id.emailinsertado);

                Button addButton = dialogView.findViewById(R.id.insertarjugador);


                alertDialog.show();


                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String texto = editText.getText().toString();
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("User");
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    boolean encontrado=false;
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        HashMap<String,String> m = (HashMap<String, String>) snapshot.getValue();
                                        if(m.containsValue(texto)){
                                            Log.d("Que",texto);
                                            encontrado=true;
                                            textV.append(texto + "\n");
                                            break;
                                        }
                                    }
                                    if(!encontrado)
                                        Toast.makeText(CreateCampaign.this,"Inserta un email de un usuario registrado",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // El método onCancelled se ejecutará si ocurre algún error o se cancela la operación
                                    Log.e("Error", "Error al obtener los datos: " + databaseError.getMessage());
                                }
                            });

                            alertDialog.dismiss();
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

                EditText name = findViewById(R.id.insertNameCamptxt);
                String names = name.getText().toString().trim();
                TextView player = findViewById(R.id.jugadoresAniadidos);
                String players = player.getText().toString().trim();


                CampaignEntity insertarc = new CampaignEntity(names,act.getEmail(), "", players);



                Query query = dbreff.orderByChild("mail").equalTo(act.getEmail());
                Log.d("El usuario es ",act.getEmail());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String clave = snapshot.getKey();
                                DatabaseReference nodoEncontradoRef = dbreff.child(clave);
                                String datos = act.getEmail().replace(".",",");

                                nodoEncontradoRef.child("campanas").child(names + "_" + datos).setValue(insertarc);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Ocurrió un error de lectura de la base de datos
                    }
                });
                String[] array=players.split(" ");
                for(int i=0;i<array.length;i++) {
                    Query query2 = dbreff.orderByChild("mail").equalTo(array[i]);
                    Log.d("El usuario es ", array[i]);

                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String clave = snapshot.getKey();
                                    DatabaseReference nodoEncontradoRef = dbreff.child(clave);
                                    String datos = act.getEmail().replace(".", ",");

                                    nodoEncontradoRef.child("campanas").child(names + "_" + datos).setValue(insertarc);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Ocurrió un error de lectura de la base de datos
                        }
                    });
                }

                if (image_url != null) {
                    insertarc.setImg(image_url.toString());
                    subirPhoto(image_url, insertarc.getName());
                }


                Toast.makeText(getApplicationContext(), "Se ha enviado los datos", Toast.LENGTH_LONG).show();

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
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
        String rute_storage = storage_path + "_" + photo  + "_" + name;
        StorageReference storeRef = stref.child(rute_storage);
        storeRef.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateCampaign.this, "Foto insertada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

