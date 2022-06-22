package com.example.rentpe.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentpe.HouseDetails;
import com.example.rentpe.HouseImages;
import com.example.rentpe.Payment;
import com.example.rentpe.fragments.AllHomesFragment;
import com.example.rentpe.fragments.HomeImagesFragment;
import com.example.rentpe.R;
import com.example.rentpe.fragments.PastTransactionsFragment;
import com.example.rentpe.fragments.SingleHouseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<House> list;
    String Landlord,Tenant;
    FirebaseAuth mAuth;

    public HouseAdapter(Context mContext, ArrayList<House> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.house_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        House house=list.get(position);
        String address=house.getAddress();
        if(address.length()>20) {
            String newAddress=address.substring(0,20)+"..";
            holder.addressofhouse.setText(newAddress);
        }
        else
            holder.addressofhouse.setText(address);
        String imageURL=house.getImgLink0();
        Picasso.get().load(imageURL).into(holder.image);
        holder.HouseDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new SingleHouseFragment(house.getAddress(),
                                house.getDescription(),
                                house.getRent(),
                                house.getNameOfTheLandlord(),
                                house.getNameOfTheTenant(),
                                house.getUserIDofLandlord(),
                                house.getUserIDofTenant()))
                        .addToBackStack(null).commit();
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new HomeImagesFragment(house.getImgLink0(),
                                house.getImgLink1(),
                                house.getImgLink2(),
                                house.getImgLink3(),
                                house.getImgLink4(),
                                house.getImgLink5(),
                                house.getImgLink6(),
                                house.getImgLink7(),
                                house.getImgLink8(),
                                house.getImgLink9())).addToBackStack(null).commit();
            }
        });
        holder.payrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), Payment.class);
                i.putExtra("PhoneL",house.getPhoneLandlord());
                i.putExtra("PhoneT",house.getPhoneTenant());
                i.putExtra("Email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                i.putExtra("UIDL",house.getUserIDofLandlord());
                i.putExtra("UIDT",house.getUserIDofTenant());
                i.putExtra("Rent",house.getRent());
                i.putExtra("NameL",house.getNameOfTheLandlord());
                i.putExtra("NameT",house.getNameOfTheTenant());
                mContext.startActivity(i);
            }
        });
        String CurrentUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new PastTransactionsFragment(CurrentUID)).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView addressofhouse;
        Button  review,payrent, HouseDetailsButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.houseImage1);
            addressofhouse=itemView.findViewById(R.id.addressItem);
            review=itemView.findViewById(R.id.reviewButton);
            payrent=itemView.findViewById(R.id.RentButton);
            HouseDetailsButton=itemView.findViewById(R.id.HomeDetailsButton);
        }
    }
}
