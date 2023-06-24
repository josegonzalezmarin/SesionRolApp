package es.upm.sesionrol;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

    Spinner raceSpinner;
    Spinner classSpinner;
    Spinner aligmSpinner;
    Spinner backgSpinner;
    private List<ObjectResult> totalClases;
    private List<ObjectResult> totalRaces;
    private List<ObjectResult> totalAligm;
    private List<ObjectResult> totalBackg;

    private RequestAnswer respuesta;

    private List<String> insertadasClasses = new ArrayList<>();

    private MenuItem crearcampana;
    private ListView listView;
    private PersonajeListAdapter personajeAdapter;
    private List<PersonajeEntity> listaPersonajes;
    private DataSnapshot ainsertar;

    ImageView image;
    StorageReference stref;
    String storage_path = "profilepic/*";

    DatabaseReference dbreff;
    private FirebaseFirestore db;
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

        stref = FirebaseStorage.getInstance().getReference();

        db = FirebaseFirestore.getInstance();


        dbreff = FirebaseDatabase.getInstance().getReference("User");

        FirebaseAuth aut = FirebaseAuth.getInstance();
        FirebaseUser act = aut.getCurrentUser();


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

                    PersonajeEntity insertarp = new PersonajeEntity(names, raceSpinner.getSelectedItem().toString().trim(), classSpinner.getSelectedItem().toString().trim(), Integer.valueOf(lvls), Integer.valueOf(strs), Integer.valueOf(dexs), Integer.valueOf(conss), Integer.valueOf(ints), Integer.valueOf(wisds), Integer.valueOf(chars));


                    insertarp.setCompetences(comp.getText().toString());
                    insertarp.setBond(bond.getText().toString());
                    insertarp.setEquipment(equip.getText().toString());
                    insertarp.setIdeal(ideal.getText().toString());
                    insertarp.setFeature(feat.getText().toString());
                    insertarp.setFlaws(flaw.getText().toString());
                    insertarp.setPersonality(pers.getText().toString());
                    insertarp.setBackg(backgSpinner.getSelectedItem().toString());
                    insertarp.setAligm(aligmSpinner.getSelectedItem().toString());


                    Map<String, Object> personajeData = new HashMap<>();
                    personajeData.put("name", names);
                    personajeData.put("lvl", Integer.valueOf(lvls));
                    personajeData.put("dndclass", classSpinner.getSelectedItem().toString());
                    personajeData.put("race", raceSpinner.getSelectedItem().toString());
                    personajeData.put("exp", exper);
                    personajeData.put("aligm", aligmSpinner.getSelectedItem().toString());
                    personajeData.put("backg", backgSpinner.getSelectedItem().toString());
                    personajeData.put("str", Integer.valueOf(strs));
                    personajeData.put("dex", Integer.valueOf(dexs));
                    personajeData.put("constit", Integer.valueOf(conss));
                    personajeData.put("intel", Integer.valueOf(ints));
                    personajeData.put("wisd", Integer.valueOf(wisds));
                    personajeData.put("charism", Integer.valueOf(chars));
                    personajeData.put("competences", comp.getText().toString());
                    personajeData.put("equipment", equip.getText().toString());
                    personajeData.put("ideal", ideal.getText().toString());
                    personajeData.put("bond", bond.getText().toString());
                    personajeData.put("feature", feat.getText().toString());
                    personajeData.put("personality", pers.getText().toString());
                    personajeData.put("flaws", flaw.getText().toString());


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
                //TODO sacar info de la api
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO sacar info de la api
            }
        });
        aligmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedaligm = parent.getItemAtPosition(pos).toString();
                //TODO sacar info de la api
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO sacar info de la api
            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedclass = parent.getItemAtPosition(pos).toString();
                //TODO sacar info de la api
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO sacar info de la api
            }
        });
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedRace = parent.getItemAtPosition(pos).toString();
                //TODO sacar info de la api
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO sacar info de la api
            }
        });
    }


    public void OnBackPressed() {
        if (cCharacter.isDrawerOpen(GravityCompat.START)) cCharacter.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }


    public void onImageButtonClick(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            cambio = new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
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


    }

