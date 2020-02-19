package com.example.iconparking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText name, email, password,mobile;
    Button register;
    FirebaseAuth auth;
    DatabaseReference reference;
    TextView login;

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        mobile=findViewById(R.id.mobile);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtMobile=mobile.getText().toString();
                if (TextUtils.isEmpty(txtUsername)) {
                    name.setError("Name Required");
                }
                if (TextUtils.isEmpty(txtEmail)) {
                    name.setError("Email Required");
                }
                if (TextUtils.isEmpty(txtPassword)) {
                    name.setError("password Required");
                } else if (txtPassword.length() < 4) {
                    name.setError("Length of password is sort");
                } else {
                    ///register(txtUsername,txtEmail,txtPassword);
                    final ProgressDialog pd = new ProgressDialog(SignUp.this);
                    pd.setMessage("Authenticating...");
                    pd.show();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference reference = database.getReference("user");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pd.dismiss();
                            if (dataSnapshot.child(mobile.getText().toString()).exists()) {
                                //Toast.makeText(SignUp.this, "User register successfully", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp.this, "User already register", Toast.LENGTH_SHORT).show();

                                Intent in=new Intent(SignUp.this,SignIn.class);
                                startActivity(in);
                                finish();
                            } else
                                {
                                pd.dismiss();
                                User user = new User(email.getText().toString(),name.getText().toString(), password.getText().toString());
                                reference.child(mobile.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "User register successfully", Toast.LENGTH_SHORT).show();
                                    Intent in=new Intent(SignUp.this,SignIn.class);
                                    startActivity(in);
                                    finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}