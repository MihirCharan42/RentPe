package com.example.rentpe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentpe.fragments.AllHomesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    private Button btn,next;
    private TextView tv;
    String UIDL,UIDT,PhoneL,PhoneT,email,rent,nameL,nameT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn=findViewById(R.id.pay);
        tv=findViewById(R.id.paytv);
        next=findViewById(R.id.next);
        Date d=new Date();
        tv.setText(new StringBuilder().append("Pay rent for the\n").append(d.getMonth()).append("th month of ").append(d.getYear()+1900).append("?").toString());
        next.setVisibility(View.GONE);

        Bundle Incomingmessages= getIntent().getExtras();
        if(Incomingmessages!=null){
            PhoneL=Incomingmessages.getString("PhoneT");
            PhoneT=Incomingmessages.getString("PhoneL");
            UIDL=Incomingmessages.getString("UIDL");
            UIDT=Incomingmessages.getString("UIDT");
            rent=Incomingmessages.getString("Rent");
            email=Incomingmessages.getString("Email");
            nameL=Incomingmessages.getString("NameL");
            nameT=Incomingmessages.getString("NameT");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout= new Checkout();
                int amt=Integer.parseInt(rent)*100;
                checkout.setKeyID("rzp_test_yFLjZMNjnGOWw2");
                checkout.setImage(R.drawable.ic_launcher_background);
                try {
                    JSONObject obj=new JSONObject();
                    obj.put("name", "RentPe");
                    obj.put("description","something");
                    obj.put("theme.color", "#0093DD");
                    obj.put("currency", "INR");
                    obj.put("amount",amt);
                    obj.put("prefill.contact",PhoneT);
                    obj.put("prefill.email",email);
                    checkout.open(Payment.this, obj);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Payment.this,HomeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
            Date d=new Date();
            HashMap<String, String> hashMap = new HashMap<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");
            hashMap.put("TransactionID",s);
            hashMap.put("Time",d.toString());
            hashMap.put("UserIDofTenant",UIDT);
            hashMap.put("UserIDofLandlord",UIDL);
            hashMap.put("NameT",nameT);
            hashMap.put("NameL",nameL);
            databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Payment.this, "Successfully Uploaded the Transaction", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Payment.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        Toast.makeText(Payment.this,"Successful Transaction "+s,Toast.LENGTH_SHORT).show();
        tv.setText("You have paid the rent for\n"+d.getMonth()+"th of the month "+d.getYear()+1900);
        btn.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Payment.this,"Unsuccessful Transaction",Toast.LENGTH_SHORT).show();
    }
}