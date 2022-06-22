package com.example.rentpe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText name;
    private Button submit;

    private DatabaseReference mRootRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        name=findViewById(R.id.nameRegister);
        email=findViewById(R.id.emailRegister);
        password=findViewById(R.id.passwordRegister);
        phone=findViewById(R.id.phoneRegister);
        submit=findViewById(R.id.submit);

        mAuth=FirebaseAuth.getInstance();

        mRootRef=FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtname=name.getText().toString();
                String txtemail=email.getText().toString();
                String txtpassword=password.getText().toString();
                String txtphone=phone.getText().toString();
                if(TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtname) || TextUtils.isEmpty(txtpassword) || TextUtils.isEmpty(txtphone)){
                    Toast.makeText(SignInActivity.this,"Fill everything",Toast.LENGTH_SHORT).show();
                }
                else if(txtpassword.length()<6){
                    Toast.makeText(SignInActivity.this,"Password should be > 6",Toast.LENGTH_SHORT).show();
                }
                else{
                    regsiterUser(txtname,txtemail,txtpassword, txtphone);
                }
            }

            private void regsiterUser(String txtname, String txtemail, String txtpassword, String txtphone) {
                mAuth.createUserWithEmailAndPassword(txtemail,txtpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String,Object> mpp=new HashMap<>();
                        mpp.put("name",txtname);
                        mpp.put("email",txtemail);
                        mpp.put("password",txtpassword);
                        mpp.put("phone",txtphone);
                        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(mpp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(SignInActivity.this,HomeActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);;
                                    finish();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}