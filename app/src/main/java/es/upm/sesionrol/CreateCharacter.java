package es.upm.sesionrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.navigation.NavigationView;

public class CreateCharacter  extends AppCompatActivity {
    private DrawerLayout cCharacter;
    Spinner raceSpinner;
    Spinner classSpinner;
    Spinner aligmSpinner;
    Spinner backgSpinner;
    private MenuItem crearcampana;
    private DatabaseReference db;
    private Button savebt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                EditText lvl = findViewById(R.id.insertLevel);
                EditText str = findViewById(R.id.strTxt);
                EditText dex = findViewById(R.id.dexTxt);
                EditText consti = findViewById(R.id.constTxt);
                EditText intel = findViewById(R.id.intTxt);
                EditText wisd = findViewById(R.id.sabTxt);
                EditText charism = findViewById(R.id.charTxt);
                PersonajeEntity p = new PersonajeEntity(
                        name.getText().toString(),
                        raceSpinner.getSelectedItem().toString(),
                        classSpinner.getSelectedItem().toString(),
                        Integer.valueOf(lvl.getText().toString()),
                        Integer.valueOf(str.getText().toString()),
                        Integer.valueOf(dex.getText().toString()),
                        Integer.valueOf(consti.getText().toString()),
                        Integer.valueOf(intel.getText().toString()),
                        Integer.valueOf(wisd.getText().toString()),
                        Integer.valueOf(charism.getText().toString()));
                if(p==null)
                    Toast.makeText(CreateCharacter.this, "Campos obligatorios sin rellenar", Toast.LENGTH_SHORT).show();
                else {
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

                    db.child("personaje").child(p.getPid()+"").setValue(p);
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
        else if(id_item==R.id.cerrar_sesion){
            cambio= new Intent(findViewById(R.id.crear_campana).getContext(), MainActivity.class);
            startActivity(cambio);
        }
        else if(id_item==R.id.editar_personaje){
            cambio= new Intent(findViewById(R.id.editar_personaje).getContext(), CreateCampaign.class);
            startActivity(cambio);
        }
        else{
            cambio= new Intent(findViewById(R.id.ajustes).getContext(), MainActivity.class);
            startActivity(cambio);
        }


    }
}
