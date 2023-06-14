package es.upm.sesionrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private FirebaseAuth mAuthR;
    User use;
    private String TAG ="Vamos a ver que me pierdo";
    DatabaseReference dbreff;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbreff = FirebaseDatabase.getInstance().getReference().child("User");
        EditText emailEdtR = findViewById(R.id.registerEmail);
        EditText passEdtR = findViewById(R.id.registerPass);
        Button registerButton = findViewById(R.id.register_button);
        use = new User();

        mAuthR = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mAuthR.createUserWithEmailAndPassword(emailEdtR.getText().toString(), passEdtR.getText().toString()).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            use.setMail(emailEdtR.getText().toString().trim());
                            dbreff.push().setValue(use);
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuthR.getCurrentUser();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registro.this, "Fallo al crear", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
            }

        });
    }
}
