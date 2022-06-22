package com.example.rentpe.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rentpe.HouseDetails;
import com.example.rentpe.MainActivity;
import com.example.rentpe.R;
import com.example.rentpe.model.House;
import com.example.rentpe.model.HouseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllHomesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllHomesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button addhome;
    private TextView plus,drawer,empty;
    ProgressDialog pd;
    private RecyclerView recyclerView;
    private DatabaseReference db;
    FirebaseAuth mAuth;
    HouseAdapter myAdapter;
    ArrayList<House> HouseList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllHomesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllHomesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllHomesFragment newInstance(String param1, String param2) {
        AllHomesFragment fragment = new AllHomesFragment();
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
        View holder=inflater.inflate(R.layout.fragment_all_homes, container, false);
        inflater.inflate(R.layout.fragment_all_homes, container, false);
        mAuth=FirebaseAuth.getInstance();
        empty=holder.findViewById(R.id.emptyTV);
        addhome=holder.findViewById(R.id.addhouse2);
        empty.setVisibility(View.GONE);
        addhome.setVisibility(View.GONE);
        String UIDofCurrent=mAuth.getCurrentUser().getUid();
        recyclerView = holder.findViewById(R.id.recyclerview);
        db= FirebaseDatabase.getInstance().getReference("HouseDetails");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HouseList= new ArrayList<>();
        myAdapter = new HouseAdapter(getActivity(),HouseList);
        recyclerView.setAdapter(myAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    House house= data.getValue(House.class);
                    String UIDL,UIDT;
                    UIDL=house.getUserIDofLandlord();
                    UIDT=house.getUserIDofTenant();
                    if(UIDT.equals(UIDofCurrent))
                        HouseList.add(house);
                    else if(UIDL.equals(UIDofCurrent))
                        HouseList.add(house);
                }
                if(HouseList.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                    empty.setText("There is nothing in the database that belongs to you");
                    addhome.setVisibility(View.VISIBLE);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        plus=holder.findViewById(R.id.plus);
        drawer=holder.findViewById(R.id.drawer);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), HouseDetails.class);
                startActivity(i);
            }
        });
        addhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),HouseDetails.class);
                startActivity(i);
            }
        });
        return holder;
    }
}