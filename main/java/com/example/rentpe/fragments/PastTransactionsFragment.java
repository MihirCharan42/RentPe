package com.example.rentpe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rentpe.R;
import com.example.rentpe.model.House;
import com.example.rentpe.model.HouseAdapter;
import com.example.rentpe.model.Transaction;
import com.example.rentpe.model.TransactionAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastTransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastTransactionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String UIDofCurrent;
    private TextView emptyTV,backTV;
    private RecyclerView rv;
    private TransactionAdapter myAdapter;
    ArrayList<Transaction> TransactionList;

    private DatabaseReference db;
    public PastTransactionsFragment() {
        // Required empty public constructor
    }
    public PastTransactionsFragment(String UID) {
        this.UIDofCurrent=UID;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastTransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastTransactionsFragment newInstance(String param1, String param2) {
        PastTransactionsFragment fragment = new PastTransactionsFragment();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_past_transactions, container, false);
        emptyTV=v.findViewById(R.id.emptyTV);
        backTV=v.findViewById(R.id.gobacktohome);
        rv=v.findViewById(R.id.recyclerviewTransaction);
        emptyTV.setVisibility(View.GONE);

        db= FirebaseDatabase.getInstance().getReference("Transactions");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        TransactionList= new ArrayList<>();
        myAdapter = new TransactionAdapter(getActivity(),TransactionList);
        rv.setAdapter(myAdapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Transaction transaction= data.getValue(Transaction.class);
                    String UIDL,UIDT;
                    UIDL=transaction.getUserIDofLandlord();
                    UIDT=transaction.getUserIDofTenant();
                    if(UIDT.equals(UIDofCurrent))
                        TransactionList.add(transaction);
                    else if(UIDL.equals(UIDofCurrent))
                        TransactionList.add(transaction);
                }
                if(TransactionList.isEmpty()) {
                    emptyTV.setVisibility(View.VISIBLE);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}