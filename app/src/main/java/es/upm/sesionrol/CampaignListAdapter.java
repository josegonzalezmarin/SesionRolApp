package es.upm.sesionrol;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
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


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.upm.sesionrol.PersonajeEntity;
import es.upm.sesionrol.R;

public class CampaignListAdapter extends ArrayAdapter<CampaignEntity> {



    private Context context;
    private List<CampaignEntity> listacampanas;
    private int resourceLayout;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseUser user;
    private FirebaseAuth userA;


    public CampaignListAdapter(@NonNull Context context,int resource, List<CampaignEntity> listacampanas) {
        super(context, resource, listacampanas);
        this.context = context;
        this.resourceLayout = resource;
        this.listacampanas = listacampanas;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceLayout,null);
        }

        CampaignEntity cam = listacampanas.get(position);

        TextView tvName = view.findViewById(R.id.nameView);
        tvName.setText(cam.getName());

        TextView tvJug = view.findViewById(R.id.textnombJugadores);
        String insertarJ = cam.getJugadores().replace("\n"," ");
        tvJug.setText(insertarJ);

        TextView tvmaster = view.findViewById((R.id.master));
        String insertarmaster= cam.getMaster();
        tvmaster.setText(insertarmaster);

        userA = FirebaseAuth.getInstance();
        user = userA.getCurrentUser();
        String imagen ="*_photo_"+user.getUid()+"_"+cam.getName();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("camppic/"+imagen);

        ImageView imagenV = view.findViewById(R.id.imagencamp);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // La descarga de la URL de la imagen se completó con éxito
                // Carga la imagen en el ImageView utilizando una biblioteca de manejo de imágenes
                if(imagenV!=null)
                    Picasso.with(context).load(uri).into(imagenV);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Missing image","No tiene imagen asociada");
            }
        });



        return view;
    }

}