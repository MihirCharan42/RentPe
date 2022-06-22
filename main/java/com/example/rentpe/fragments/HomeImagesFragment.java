package com.example.rentpe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rentpe.R;
import com.example.rentpe.model.HouseAdapter;
import com.example.rentpe.model.ImageAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeImagesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String imageLink0, imageLink1,imageLink2, imageLink3, imageLink4, imageLink5, imageLink6, imageLink7, imageLink8,imageLink9;
    private RecyclerView recyclerView;
    ImageAdapter adapter;
    TextView tv;

    public HomeImagesFragment() {
        // Required empty public constructor
    }
    public HomeImagesFragment(String imageLink0, String imageLink1, String imageLink2, String imageLink3, String imageLink4, String imageLink5, String imageLink6, String imageLink7, String imageLink8, String imageLink9) {
        this.imageLink0=imageLink0;
        this.imageLink1=imageLink1;
        this.imageLink2=imageLink2;
        this.imageLink3=imageLink3;
        this.imageLink4=imageLink4;
        this.imageLink5=imageLink5;
        this.imageLink6=imageLink6;
        this.imageLink7=imageLink7;
        this.imageLink8=imageLink8;
        this.imageLink9=imageLink9;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeImagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeImagesFragment newInstance(String param1, String param2) {
        HomeImagesFragment fragment = new HomeImagesFragment();
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
        View holder= inflater.inflate(R.layout.fragment_home_images, container, false);
        inflater.inflate(R.layout.fragment_home_images, container, false);
        recyclerView = holder.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> array=new ArrayList<>();
        adapter = new ImageAdapter(getActivity(),array);
        recyclerView.setAdapter(adapter);
        if(!imageLink0.isEmpty())
            array.add(imageLink0);
        if(!imageLink1.isEmpty())
            array.add(imageLink1);
        if(!imageLink2.isEmpty())
            array.add(imageLink2);
        if(!imageLink3.isEmpty())
            array.add(imageLink3);
        if(!imageLink4.isEmpty())
            array.add(imageLink4);
        if(!imageLink5.isEmpty())
            array.add(imageLink5);
        if(!imageLink6.isEmpty())
            array.add(imageLink6);
        if(!imageLink7.isEmpty())
            array.add(imageLink7);
        if(!imageLink8.isEmpty())
            array.add(imageLink8);
        if(!imageLink9.isEmpty())
            array.add(imageLink9);
        adapter.notifyDataSetChanged();
        //array.clear();
        return holder;
    }
}