package com.example.rentpe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentpe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleHouseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String prevAddress, prevDescription, prevRent, prevNameLandlord, prevNameTenant, prevUserIDL, prevUserIDT;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleHouseFragment() {
        // Required empty public constructor
    }
    public SingleHouseFragment(String Address, String Description,String Rent, String NameLandlord, String NameTenant, String UIDL, String UIDT) {
        this.prevAddress=Address;
        this.prevDescription=Description;
        this.prevRent= Rent;
        this.prevNameLandlord=NameLandlord;
        this.prevNameTenant=NameTenant;
        this.prevUserIDL=UIDL;
        this.prevUserIDT=UIDT;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleHouseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleHouseFragment newInstance(String param1, String param2) {
        SingleHouseFragment fragment = new SingleHouseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_single_house,container,false);
        EditText rent,address,description,namelandord,nametenant;
        Button update,delete;
        rent=v.findViewById(R.id.rent);
        address=v.findViewById(R.id.address);
        description=v.findViewById(R.id.description);
        namelandord=v.findViewById(R.id.nameLandlord);
        nametenant=v.findViewById(R.id.nameTenant);
        update=v.findViewById(R.id.UpdateButton);
        delete=v.findViewById(R.id.DeleteButton);

        rent.setText(prevRent);
        address.setText(prevAddress);
        description.setText(prevDescription);
        namelandord.setText(prevNameLandlord);
        nametenant.setText(prevNameTenant);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newRent=rent.getText().toString();
                String newAddress=address.getText().toString();
                String newDescription=description.getText().toString();
                String newNameLandlord=namelandord.getText().toString();
                String newNameTenant=nametenant.getText().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HouseDetails");
                Map hashMap = new HashMap<>();
                hashMap.put("Address", newAddress);
                hashMap.put("Description", newDescription);
                hashMap.put("NameOfTheLandlord", newNameLandlord);
                hashMap.put("NameOfTheTenant", newNameTenant);
                hashMap.put("Rent", newRent);
                String newUIDL=prevUserIDL.substring(20);
                String newUIDT=prevUserIDT.substring(20);
                String UpdateBranch=newUIDL+" + "+newUIDT;
                databaseReference.child(UpdateBranch).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            rent.setText(newRent);
                            address.setText(newAddress);
                            description.setText(newDescription);
                            namelandord.setText(newNameLandlord);
                            nametenant.setText(newNameTenant);
                            Toast.makeText(getContext(), "Successfull Updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HouseDetails");
                String newUIDL=prevUserIDL.substring(20);
                String newUIDT=prevUserIDT.substring(20);
                String DeleteBranch=newUIDL+" + "+newUIDT;
                databaseReference.child(DeleteBranch).removeValue();
                Toast.makeText(getContext(), "Successfull Deleted", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        return v;
    }
    public void onBackPressed() {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new AllHomesFragment()).addToBackStack(null).commit();
    }
}