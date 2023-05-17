package es.upm.sesionrol;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.upm.sesionrol.PersonajeEntity;
import es.upm.sesionrol.R;

public class PersonajeListAdapter extends ArrayAdapter<PersonajeEntity> {



    private Context context;
    private List<PersonajeEntity> listaPersonajes;
    private LayoutInflater inflater;
    private DatabaseReference databaseReference;
    private ChildEventListener pChildEventListener;

    // variable for Text view.
    private TextView retrieveTV;
    ListView lvListadoFirebase;
    List<PersonajeEntity> lRow;


    public PersonajeListAdapter(Context context, List<PersonajeEntity> listaPersonajes) {
        super(context, 0, listaPersonajes);
        this.context = context;
        this.listaPersonajes = listaPersonajes;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.character_summary, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.nameView1);
        TextView tvClass = convertView.findViewById(R.id.classView);
        TextView tvRace = convertView.findViewById(R.id.raceView);
        TextView tvExp = convertView.findViewById(R.id.expView);
        TextView tvLvl = convertView.findViewById(R.id.lvlView);

        PersonajeEntity personaje = listaPersonajes.get(position);
        tvName.setText(personaje.getName());
        tvClass.setText(personaje.getDndclass());
        tvRace.setText(personaje.getRace());
        tvExp.setText(personaje.getExp()+"");
        tvLvl.setText(personaje.getLvl()+"");

        return convertView;
    }

    @Override
    public int getCount() {
        return listaPersonajes.size();
    }

    /*
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.character_summary, parent, false);
        }

        PersonajeEntity personaje = getItem(position);

        TextView nombreTextView = itemView.findViewById(R.id.nameView);
        TextView nivelTextView = itemView.findViewById(R.id.lvlView);
        TextView claseTextView = itemView.findViewById(R.id.classView);

        nombreTextView.setText(personaje.getName());
        nivelTextView.setText(String.valueOf(personaje.getLvl()));
        claseTextView.setText(personaje.getDndclass());

        return itemView;
    }*/
}