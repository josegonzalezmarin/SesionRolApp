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

public class Registro extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private FirebaseAuth mAuthR;
    private String TAG;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        EditText emailEdtR = findViewById(R.id.registerEmail);
        EditText passEdtR = findViewById(R.id.registerPass);
        Button registerButton = findViewById(R.id.register_button);

        mAuthR =  FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mAuthR.createUserWithEmailAndPassword(emailEdtR.getText().toString(),passEdtR.getText().toString()).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            Log.d(TAG,"createUserWithEmail:success");
                            FirebaseUser user = mAuthR.getCurrentUser();
                        } else {
                            Log.w(TAG,"createUserWithEmail:failure",task.getException());
                            Toast.makeText(Registro.this,"Autentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
            }

        });
    }
}
