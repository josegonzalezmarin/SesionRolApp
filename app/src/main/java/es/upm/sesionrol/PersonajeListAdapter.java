package es.upm.sesionrol;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    private List<PersonajeEntity> listaPer;
    private int resourceLayout;


    public PersonajeListAdapter(@NonNull Context context,int resource, List<PersonajeEntity> listaPer) {
        super(context, resource, listaPer);
        this.context = context;
        this.resourceLayout = resource;
        this.listaPer = listaPer;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;


        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceLayout,null);
        }

        PersonajeEntity personaje = (PersonajeEntity) listaPer.get(position);

            ImageView imagen = view.findViewById(R.id.imagenperf);
            imagen.setImageResource(personaje.getImage());

            TextView tvName = view.findViewById(R.id.nameView);
            tvName.setText(personaje.getName());

            TextView tvClass = view.findViewById(R.id.classView);
            tvClass.setText(personaje.getDndclass());

            TextView tvRace = view.findViewById(R.id.raceView);
            tvRace.setText(personaje.getRace());

            TextView tvExp = view.findViewById(R.id.expView);
            tvExp.setText(personaje.getExp()+"");

            TextView tvLvl = view.findViewById(R.id.lvlView);
            tvLvl.setText(personaje.getLvl()+"");






        return view;
    }

}