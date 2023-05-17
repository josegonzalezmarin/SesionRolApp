package es.upm.sesionrol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCharacter  extends AppCompatActivity {
    private DrawerLayout cCharacter;
    Spinner raceSpinner;
    Spinner classSpinner;
    Spinner aligmSpinner;
    Spinner backgSpinner;
    private MenuItem crearcampana;
    private ListView listView;
    private PersonajeListAdapter personajeAdapter;
    private List<PersonajeEntity> listaPersonajes;

    private FirebaseFirestore db;
    private CollectionReference personajesRef;
    private Button savebt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = FirebaseFirestore.getInstance();


        setContentView(R.layout.create_character);



        cCharacter = findViewById(R.id.createcharacter);
        raceSpinner = findViewById(R.id.race_spinner);
        classSpinner = findViewById(R.id.class_spinner);
        aligmSpinner = findViewById(R.id.aligment_spinner);
        backgSpinner = findViewById(R.id.background_spinner);
        savebt = findViewById(R.id.saveC);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,cCharacter,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerClosed(View drawerView){
                MenuItem item = navigation_view.getCheckedItem();
                if(item!=null) switchLayout(item.getItemId());
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
                String names = name.getText().toString();
                EditText lvl = findViewById(R.id.insertLevel);
                String lvls = lvl.getText().toString();
                EditText str = findViewById(R.id.strTxt);
                String strs = str.getText().toString();
                EditText dex = findViewById(R.id.dexTxt);
                String dexs = dex.getText().toString();
                EditText consti = findViewById(R.id.constTxt);
                String conss = consti.getText().toString();
                EditText intel = findViewById(R.id.intTxt);
                String ints = intel.getText().toString();
                EditText wisd = findViewById(R.id.sabTxt);
                String wisds = wisd.getText().toString();
                EditText charism = findViewById(R.id.charTxt);
                String chars = charism.getText().toString();
                EditText exp = findViewById(R.id.insertExpPoints);
                String exper = exp.getText().toString();

                Log.d("DigameUSteDonded",lvls + exper+strs + dexs +conss+ints+names);
                if(names.isEmpty()||lvls==""||exper==""||strs==""||dexs==""||conss==""||ints==""||wisds==""||chars=="")
                    Toast.makeText(CreateCharacter.this, "Campos obligatorios sin rellenar", Toast.LENGTH_SHORT).show();
                else {

                    EditText comp = findViewById(R.id.comptxt);
                    EditText bond = findViewById(R.id.bondtxt);
                    EditText equip = findViewById(R.id.equipmentxt);
                    EditText feat = findViewById(R.id.featuretxt);
                    EditText flaw = findViewById(R.id.flawstxt);
                    EditText ideal = findViewById(R.id.idealtxt);
                    EditText pers = findViewById(R.id.perstxt);



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


                    db = FirebaseFirestore.getInstance();

                    db.collection("personaje")
                            .add(personajeData)
                            .addOnSuccessListener(documentReference -> {
                                // Se guardaron los datos exitosamente
                                String documentId = documentReference.getId();
                                Log.d("TAG", "Documento guardado con ID: " + documentId);
                            })
                            .addOnFailureListener(e -> {
                                // Ocurrió un error al guardar los datos
                                Log.e("TAG", "Error al guardar el documento", e);
                            });
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
    public void OnBackPressed(){
        if(cCharacter.isDrawerOpen(GravityCompat.START)) cCharacter.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }




    public void switchLayout(int id_item){
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
            cambio= new Intent(findViewById(R.id.editar_personaje).getContext(), CreateCampaign.class);
            startActivity(cambio);
        }
        else{
            cambio= new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
}
