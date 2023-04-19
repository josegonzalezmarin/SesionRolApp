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

public class MainActivity extends AppCompatActivity {


    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    private FirebaseAuth mAuth;
    private String email;
    private String password;

    private String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText emailEdt = findViewById(R.id.editTextTextEmailAddress);
        EditText passEdt = findViewById(R.id.editTextTextPassword);
        Button submit = findViewById(R.id.submit_button);
        Button registerB = findViewById(R.id.registerButton);


        mAuth = FirebaseAuth.getInstance();

        registerB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Registro.class);
                startActivity(myIntent);
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailEdt.getText().toString()) && TextUtils.isEmpty(passEdt.getText().toString())){
                    Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(emailEdt.getText().toString(),passEdt.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG,"signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent my2Intent = new Intent(view.getContext(), MainHub.class);
                                startActivity(my2Intent);
                            }else{
                                Log.w(TAG,"signInWithEmail:failure",task.getException());
                                Toast.makeText(MainActivity.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

}

