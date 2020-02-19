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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity
{
    EditText mobile,password;
    Button login;
    TextView register;
    FirebaseAuth auth;
    SharedPreferences sp=null;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        password=findViewById(R.id.password);
        mobile=findViewById(R.id.mobile);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();
        sp = getSharedPreferences("user", MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(SignIn.this,SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtMobile=mobile.getText().toString();
                String txtPassword=password.getText().toString();

                if(TextUtils.isEmpty(txtMobile))
                {
                    mobile.setError("Mobile no Required");
                }
                if(TextUtils.isEmpty(txtPassword))
                {
                    password.setError("Password Required");
                }
                else
                {
                    final ProgressDialog pd = new ProgressDialog(SignIn.this);
                    pd.setMessage("Authenticating...");
                    pd.show();

                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference("user");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pd.dismiss();
                            if (dataSnapshot.child(mobile.getText().toString()).exists()) {
                                User user = dataSnapshot.child(mobile.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(password.getText().toString()))
                                {
                                    Toast.makeText(SignIn.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("mobileNo",dataSnapshot.getKey());
                                    editor.putString("name",user.getName());
                                    editor.putString("emailId",user.getEmail());
                                    editor.putString("password",user.getPassword());
                                    editor.commit();
                                    Intent in=new Intent(SignIn.this,Home.class);
                                    startActivity(in);
                                    finish();
                                    Toast.makeText(SignIn.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                }
                                else
                                    Toast.makeText(SignIn.this, "Failed", Toast.LENGTH_SHORT).show();

                            }
                            else Toast.makeText(SignIn.this, "User Not Exist", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    auth.signInWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful())
//                            {
//                               // Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                //startActivity(intent);
//                                //finish();
//                                Toast.makeText(SignIn.this, "Successfully login", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                //Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
//
//                                Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }
        });
    }
}
