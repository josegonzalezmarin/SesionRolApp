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
    private List<PersonajeEntity> listaPersonajes;
    private int resourceLayout;


    public PersonajeListAdapter(@NonNull Context context,int resource, List<PersonajeEntity> listaPersonajes) {
        super(context, resource, listaPersonajes);
        this.context = context;
        this.resourceLayout = resource;
        this.listaPersonajes = listaPersonajes;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;


        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceLayout,null);
        }


        PersonajeEntity personaje = listaPersonajes.get(position);

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
    @Override
    public PersonajeEntity getItem(int position) {
        return getItemList().get(position);
    }

    private List<PersonajeEntity> getItemList() {
        // Supongamos que tienes una lista de objetos Item llamada "itemList"
        // Puedes modificar este método según cómo tengas implementada tu lista de elementos
        return listaPersonajes;
    }
}