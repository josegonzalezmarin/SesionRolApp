package es.upm.sesionrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upm.sesionrol.clasesAPI.ApiElementAdapter;
import es.upm.sesionrol.clasesAPI.ObjectResult;
import es.upm.sesionrol.clasesAPI.RequestAnswer;
import es.upm.sesionrol.interfaces.PersonajeAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


public class CreateCharacter extends AppCompatActivity {
    private DrawerLayout cCharacter;

    private FirebaseAuth aut;
    private FirebaseUser act;
    private Spinner raceSpinner;
    private Spinner classSpinner;
    private Spinner aligmSpinner;
    private Spinner backgSpinner;
    private List<ObjectResult> totalClases;
    private List<ObjectResult> totalRaces;
    private List<ObjectResult> totalAligm;
    private List<ObjectResult> totalBackg;




    private DataSnapshot ainsertar;

    ImageView image;
    StorageReference stref;
    String storage_path = "profilepic/*";
    private static final int COD_SEL_IMAGE =300;

    private Uri image_url;
    private String photo = "photo";



    DatabaseReference dbreff;
    private FirebaseFirestore mFirestore;
    private CollectionReference personajesRef;
    private Button savebt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_character);


        raceSpinner = findViewById(R.id.race_spinner);
        classSpinner = findViewById(R.id.class_spinner);
        aligmSpinner = findViewById(R.id.aligment_spinner);
        backgSpinner = findViewById(R.id.background_spinner);

        totalClases = new ArrayList<>();
        totalRaces = new ArrayList<>();
        totalAligm = new ArrayList<>();
        totalBackg = new ArrayList<>();
        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();
        image = findViewById(R.id.imageProf);




        cCharacter = findViewById(R.id.createcharacter);
        ApiElementAdapter adapterclass = new ApiElementAdapter(this,totalClases);
        ApiElementAdapter adapterrace = new ApiElementAdapter(this,totalRaces);
        ApiElementAdapter adapteraligm = new ApiElementAdapter(this,totalAligm);
        ApiElementAdapter adapterbackg = new ApiElementAdapter(this,totalBackg);



        getAPIData(adapterclass,adapterrace,adapteraligm,adapterbackg);

        aligmSpinner.setSelection(0);
        backgSpinner.setSelection(0);
        raceSpinner.setSelection(0);
        classSpinner.setSelection(0);



        savebt = findViewById(R.id.saveC);







        Intent intent = getIntent();
        String nameg = intent.getStringExtra("name");
        String race = intent.getStringExtra("race");
        String dndclass = intent.getStringExtra("dndclass");
        String expg = intent.getStringExtra("exp");
        String aligm = intent.getStringExtra("aligm");
        String backg = intent.getStringExtra("backg");
        int lvlg = intent.getIntExtra("lvl", 0);
        int strg = intent.getIntExtra("str", 0);
        int dexg = intent.getIntExtra("dex", 0);
        int constitg = intent.getIntExtra("constit", 0);
        int intelg = intent.getIntExtra("intel", 0);
        int wisdg = intent.getIntExtra("wisd", 0);
        int charismg = intent.getIntExtra("charism", 0);
        String competencesg = intent.getStringExtra("competences");
        String equipmentg = intent.getStringExtra("equipment");
        String idealg = intent.getStringExtra("ideal");
        String bondg = intent.getStringExtra("bond");
        String featureg = intent.getStringExtra("feature");
        String personalityg = intent.getStringExtra("personality");
        String flawsg = intent.getStringExtra("flaws");

        EditText name = findViewById(R.id.insertNametxt);
        EditText lvl = findViewById(R.id.insertLevel);
        EditText str = findViewById(R.id.strTxt);
        EditText dex = findViewById(R.id.dexTxt);
        EditText consti = findViewById(R.id.constTxt);
        EditText intel = findViewById(R.id.intTxt);
        EditText wisd = findViewById(R.id.sabTxt);
        EditText charism = findViewById(R.id.charTxt);
        EditText exp = findViewById(R.id.insertExpPoints);
        EditText comp = findViewById(R.id.comptxt);
        EditText bond = findViewById(R.id.bondtxt);
        EditText equip = findViewById(R.id.equipmentxt);
        EditText feat = findViewById(R.id.featuretxt);
        EditText flaw = findViewById(R.id.flawstxt);
        EditText ideal = findViewById(R.id.idealtxt);
        EditText pers = findViewById(R.id.perstxt);

        if (race != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dnd_races, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            raceSpinner.setAdapter(adapter);
            raceSpinner.setSelection(adapter.getPosition(race));
        }
        if (dndclass != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dnd_classes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classSpinner.setAdapter(adapter);
            classSpinner.setSelection(adapter.getPosition(dndclass));
        }
        if (aligm != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dnd_aligment, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            aligmSpinner.setAdapter(adapter);
            aligmSpinner.setSelection(adapter.getPosition(aligm));
        }
        if (backg != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dnd_background, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            backgSpinner.setAdapter(adapter);
            backgSpinner.setSelection(adapter.getPosition(backg));
        }

        name.setText(nameg);
        lvl.setText(String.valueOf(lvlg));
        str.setText(String.valueOf(strg));
        dex.setText(String.valueOf(dexg));
        consti.setText(String.valueOf(constitg));
        intel.setText(String.valueOf(intelg));
        wisd.setText(String.valueOf(wisdg));
        charism.setText(String.valueOf(charismg));
        exp.setText(expg);
        comp.setText(competencesg);
        equip.setText(equipmentg);
        bond.setText(bondg);
        ideal.setText(idealg);
        feat.setText(featureg);
        pers.setText(personalityg);
        flaw.setText(flawsg);

        FirebaseAuth userA = FirebaseAuth.getInstance();
        FirebaseUser user = userA.getCurrentUser();
        String imagen ="*_photo_"+user.getUid()+"_"+nameg;
        Log.d("Nombre ", imagen);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("profilepic/"+imagen);



        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // La descarga de la URL de la imagen se completó con éxito
                // Carga la imagen en el ImageView utilizando una biblioteca de manejo de imágenes
                Picasso.with(CreateCharacter.this).load(uri).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Missing image","No tiene imagen asociada");
            }
        });

        stref = FirebaseStorage.getInstance().getReference();



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });

        mFirestore = FirebaseFirestore.getInstance();


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
        DataSnapshot dataSnapshot = null;








        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, cCharacter, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                MenuItem item = navigation_view.getCheckedItem();
                if (item != null) switchLayout(item.getItemId());
            }

        };
        toggle.setDrawerIndicatorEnabled(true);
        cCharacter.addDrawerListener(toggle);
        toggle.syncState();

        navigation_view.setNavigationItemSelectedListener(item -> {
            navigation_view.setCheckedItem(item);
            cCharacter.closeDrawer(GravityCompat.START);
            return false;
        });


        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name = findViewById(R.id.insertNametxt);
                String names = name.getText().toString().trim();
                EditText lvl = findViewById(R.id.insertLevel);
                String lvls = lvl.getText().toString().trim();
                EditText str = findViewById(R.id.strTxt);
                String strs = str.getText().toString().trim();
                EditText dex = findViewById(R.id.dexTxt);
                String dexs = dex.getText().toString().trim();
                EditText consti = findViewById(R.id.constTxt);
                String conss = consti.getText().toString().trim();
                EditText intel = findViewById(R.id.intTxt);
                String ints = intel.getText().toString().trim();
                EditText wisd = findViewById(R.id.sabTxt);
                String wisds = wisd.getText().toString().trim();
                EditText charism = findViewById(R.id.charTxt);
                String chars = charism.getText().toString().trim();
                EditText exp = findViewById(R.id.insertExpPoints);
                String exper = exp.getText().toString().trim();

                EditText comp = findViewById(R.id.comptxt);
                EditText bond = findViewById(R.id.bondtxt);
                EditText equip = findViewById(R.id.equipmentxt);
                EditText feat = findViewById(R.id.featuretxt);
                EditText flaw = findViewById(R.id.flawstxt);
                EditText ideal = findViewById(R.id.idealtxt);
                EditText pers = findViewById(R.id.perstxt);



                if (names.isEmpty() || lvls == "" || exper == "" || strs == "" || dexs == "" || conss == "" || ints == "" || wisds == "" || chars == "")
                    Toast.makeText(CreateCharacter.this, "Campos obligatorios sin rellenar", Toast.LENGTH_SHORT).show();
                else {

                    ObjectResult race = (ObjectResult) raceSpinner.getSelectedItem();
                    ObjectResult clas = (ObjectResult) classSpinner.getSelectedItem();
                    ObjectResult bac = (ObjectResult) backgSpinner.getSelectedItem();
                    ObjectResult ali = (ObjectResult) aligmSpinner.getSelectedItem();

                    Log.d("Aqui",race.getName()+bac.getName()+ali.getName()+clas.getName());
                    PersonajeEntity insertarp = new PersonajeEntity(names, race.getName(), clas.getName(), Integer.valueOf(lvls), Integer.valueOf(strs), Integer.valueOf(dexs), Integer.valueOf(conss), Integer.valueOf(ints), Integer.valueOf(wisds), Integer.valueOf(chars));




                    insertarp.setCompetences(comp.getText().toString());
                    insertarp.setBond(bond.getText().toString());
                    insertarp.setEquipment(equip.getText().toString());
                    insertarp.setIdeal(ideal.getText().toString());
                    insertarp.setFeature(feat.getText().toString());
                    insertarp.setFlaws(flaw.getText().toString());
                    insertarp.setPersonality(pers.getText().toString());
                    insertarp.setBackg(bac.getName());
                    insertarp.setAligm(ali.getName());



                    if(image_url!=null){
                        insertarp.setImage(image_url.toString());
                        subirPhoto(image_url,insertarp.getName());
                    }
                    Query query = dbreff.orderByChild("mail").equalTo(act.getEmail());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String clave = snapshot.getKey();
                                    DatabaseReference nodoEncontradoRef = dbreff.child(clave);

                                    nodoEncontradoRef.child("personaje").child(names).setValue(insertarp);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Ocurrió un error de lectura de la base de datos
                        }
                    });



                    Toast.makeText(getApplicationContext(), "Se ha enviado los datos", Toast.LENGTH_LONG).show();

                }
            }
        });
        backgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedback = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aligmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedaligm = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedclass = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedRace = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void OnBackPressed() {
        if (cCharacter.isDrawerOpen(GravityCompat.START)) cCharacter.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }






    public void switchLayout(int id_item) {
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

    public void getAPIData(ArrayAdapter<ObjectResult> adapterC,ArrayAdapter<ObjectResult> adapterR,ArrayAdapter<ObjectResult> adapterA,ArrayAdapter<ObjectResult> adapterB) {
        OkHttpClient httpClient = getUnsafeOkHttpClient().newBuilder().followRedirects(false).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.dnd5eapi.co/")
                .addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).client(httpClient)
                .build();


        PersonajeAPI r = retrofit.create(PersonajeAPI.class);

        Call<RequestAnswer> classescall = r.getClasses();



                classescall.enqueue(new Callback<RequestAnswer>() {
                    @Override
                    public void onResponse(Call<RequestAnswer> call, Response<RequestAnswer> response) {
                        if (!response.isSuccessful()) {
                            Log.d("No salio bien", ""+response.code());
                            return;
                        }
                        ObjectResult ini = new ObjectResult("Seleccion","Selecciona Clase","");
                        totalClases.add(ini);
                        for(ObjectResult po : response.body().getResults()) {
                            totalClases.add(po);
                        }

                        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        classSpinner.setAdapter(adapterC);
                    }

                    @Override
                    public void onFailure(Call<RequestAnswer> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                        Log.e("Failure", t.getMessage());
                    }
                });



                Call<RequestAnswer> aligmcall = r.getAligm();


                aligmcall.enqueue(new Callback<RequestAnswer>() {
                    @Override
                    public void onResponse(Call<RequestAnswer> call, Response<RequestAnswer> response) {
                        if (!response.isSuccessful()) {
                            Log.d("No salio bien", ""+response.code());
                            return;
                        }
                        ObjectResult ini = new ObjectResult("Seleccion","Selecciona Alineamiento","");
                        totalAligm.add(ini);
                        for(ObjectResult po : response.body().getResults()) {
                            totalAligm.add(po);
                        }

                        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        aligmSpinner.setAdapter(adapterA);
                    }

                    @Override
                    public void onFailure(Call<RequestAnswer> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                        Log.e("Failure", t.getMessage());
                    }
                });


    Call<RequestAnswer> backgroundscall = r.getBackgrounds();



                backgroundscall.enqueue(new Callback<RequestAnswer>() {
                    @Override
                    public void onResponse(Call<RequestAnswer> call, Response<RequestAnswer> response) {
                        if (!response.isSuccessful()) {
                            Log.d("No salio bien", ""+response.code());
                            return;
                        }
                        ObjectResult ini = new ObjectResult("Seleccion","Selecciona Trasfondo","");
                        totalBackg.add(ini);
                        for(ObjectResult po : response.body().getResults()) {
                            totalBackg.add(po);
                        }
                        String inserciones[] ={"Anthropologist","Archaeologist","Adopted",
                                "Black Fist Double Agent","Caravan Specialist","Charlatan","City Watch","Criminal","Far Traveler",
                                "Folk Hero","Guild Artisan","Hermit","Noble","Urchin"};

                        for(int i=1;i<14;i++) {
                            totalBackg.add(new ObjectResult("Seleccion", inserciones[i], ""));
                        }
                        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        backgSpinner.setAdapter(adapterB);

                    }

                    @Override
                    public void onFailure(Call<RequestAnswer> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                        Log.e("Failure", t.getMessage());
                    }
                });

            Call<RequestAnswer> racescall = r.getRaces();

                racescall.enqueue(new Callback<RequestAnswer>() {
                    @Override
                    public void onResponse(Call<RequestAnswer> call, Response<RequestAnswer> response) {
                        if (!response.isSuccessful()) {
                            Log.d("No salio bien", ""+response.code());
                            return;
                        }
                        ObjectResult ini = new ObjectResult("Seleccion","Selecciona Raza","");
                        totalRaces.add(ini);
                        for(ObjectResult po : response.body().getResults()) {
                            totalRaces.add(po);
                        }

                        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        raceSpinner.setAdapter(adapterR);

                    }

                    @Override
                    public void onFailure(Call<RequestAnswer> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                        Log.e("Failure", t.getMessage());
                    }
                });
            }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void uploadPhoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,COD_SEL_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        Log.d("image_url", "requestCode - RESULT_OK: "+requestCode+" "+RESULT_OK);
        if(resultCode == RESULT_OK &&requestCode==COD_SEL_IMAGE){
                image_url=data.getData();
                Picasso.with(this).load(image_url).into(image);
            }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void subirPhoto(Uri image_url,String name){
        aut = FirebaseAuth.getInstance();
        String rute_storage = storage_path +"_"+photo+"_"+aut.getUid()+"_"+name;
        StorageReference storeRef = stref.child(rute_storage);
        storeRef.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateCharacter.this, "Foto insertada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }




    }

