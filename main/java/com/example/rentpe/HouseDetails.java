package com.example.rentpe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HouseDetails extends AppCompatActivity {
        EditText description,address,nameTenant,nameLandlord,LUID,TUID,rent,phoneLandlord,phoneTenant;
        Button submit;
        TextView back;
        ProgressDialog pd;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_house_details);
            description=findViewById(R.id.description);
            address=findViewById(R.id.address);
            nameTenant=findViewById(R.id.nameTenant);
            nameLandlord=findViewById(R.id.nameLandlord);
            LUID=findViewById(R.id.landlordUID);
            back=findViewById(R.id.gobacktohome);
            TUID=findViewById(R.id.tenantUID);
            phoneLandlord=findViewById(R.id.PhoneLandlord);
            phoneTenant=findViewById(R.id.PhoneTenant);
            rent=findViewById(R.id.rent);
            submit=findViewById(R.id.submitButton2);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HouseDetails.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tenantName=nameTenant.getText().toString();
                    String desc=description.getText().toString();
                    String addressDetails=address.getText().toString();
                    String landlordName=nameLandlord.getText().toString();
                    String rentDetails=rent.getText().toString();
                    String UIDofL=LUID.getText().toString();
                    String UIDofT=TUID.getText().toString();
                    String txtphoneLandlord=phoneLandlord.getText().toString();
                    String txtphoneTenant=phoneTenant.getText().toString();
                    Intent i=new Intent(HouseDetails.this, HouseImages.class);
                    i.putExtra("NameTenant",tenantName);
                    i.putExtra("NameLandlord",landlordName);
                    i.putExtra("Address",addressDetails);
                    i.putExtra("Description",desc);
                    i.putExtra("Rent",rentDetails);
                    i.putExtra("UIDofL",UIDofL);
                    i.putExtra("UIDofT",UIDofT);
                    i.putExtra("PhoneLandlord",txtphoneLandlord);
                    i.putExtra("PhoneTenant",txtphoneTenant);
                    startActivity(i);
                    finish();
                }
            });
        }
    }