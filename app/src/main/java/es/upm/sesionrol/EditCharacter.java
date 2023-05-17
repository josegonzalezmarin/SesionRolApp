package es.upm.sesionrol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ValueEventListener;

public class EditCharacter  extends AppCompatActivity {
    private DrawerLayout cCharacter;
    Spinner raceSpinner;
    Spinner classSpinner;
    Spinner aligmSpinner;
    Spinner backgSpinner;
    private MenuItem crearcampana;
    private DatabaseReference db;
    private Button savebt;



    private DatabaseReference dbref;
    private ValueEventListener velistener;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.edit_character);
        cCharacter = findViewById(R.id.createcharacter);
        raceSpinner = findViewById(R.id.race_spinner);
        classSpinner = findViewById(R.id.class_spinner);
        aligmSpinner = findViewById(R.id.aligment_spinner);
        backgSpinner = findViewById(R.id.background_spinner);
        savebt = findViewById(R.id.saveC);

        dbref = FirebaseDatabase.getInstance().getReference().child("personaje");


        velistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                // Se invoca cuando los datos cambian en el nodo "usuarios"

                for (DataSnapshot s : ds.getChildren()) {
                    // Obtén los datos de cada hijo
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


                    // Haz lo que necesites con los datos

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Se invoca cuando se produce un error en la lectura de los datos
                Log.e("TAG", "Error en la lectura: " + databaseError.getMessage());
            }


        };
        // Agrega el listener a la referencia de la base de datos
        dbref.addValueEventListener(velistener);



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

                Log.d("DigameUSteDonded",lvls + strs + dexs +conss+ints+names);
                if(names.isEmpty()||lvls==""||strs==""||dexs==""||conss==""||ints==""||wisds==""||chars=="")
                    Toast.makeText(EditCharacter.this, "Campos obligatorios sin rellenar", Toast.LENGTH_SHORT).show();
                else {
                    PersonajeEntity p = new PersonajeEntity(
                            names,raceSpinner.getSelectedItem().toString(),
                            classSpinner.getSelectedItem().toString(),
                            Integer.valueOf(lvls),
                            Integer.valueOf(strs),
                            Integer.valueOf(dexs),
                            Integer.valueOf(conss),
                            Integer.valueOf(ints),
                            Integer.valueOf(wisds),
                            Integer.valueOf(chars));

                    EditText exp = findViewById(R.id.strTxt);
                    EditText comp = findViewById(R.id.dexTxt);
                    EditText bond = findViewById(R.id.constTxt);
                    EditText equip = findViewById(R.id.intTxt);
                    EditText feat = findViewById(R.id.sabTxt);
                    EditText flaw = findViewById(R.id.charTxt);
                    EditText ideal = findViewById(R.id.insertNametxt);
                    EditText pers = findViewById(R.id.insertLevel);
                    p.setAligm(aligmSpinner.getSelectedItem().toString());
                    p.setBackg(backgSpinner.getSelectedItem().toString());
                    p.setExp(Integer.valueOf(exp.getText().toString()));
                    p.setCompetences(comp.getText().toString());
                    p.setBond(bond.getText().toString());
                    p.setEquipment(equip.getText().toString());
                    p.setFeature(feat.getText().toString());
                    p.setFlaws(flaw.getText().toString());
                    p.setIdeal(ideal.getText().toString());
                    p.setPersonality(pers.getText().toString());
                    db = FirebaseDatabase.getInstance().getReference();

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("personaje");
                    String key = "id"+names+raceSpinner.getSelectedItem().toString()+classSpinner.getSelectedItem().toString();

                    db.child("personaje").child(key).setValue(p);
                    Log.d("DigameUSteDonded","Efectivamente paso por aqui");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbref != null && velistener != null) {
            dbref.removeEventListener(velistener);
        }
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
