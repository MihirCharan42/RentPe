package com.example.rentpe.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentpe.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Transaction> list;

    public TransactionAdapter(Context mContext, ArrayList<Transaction> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.transaction_row, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction=list.get(position);
        holder.transaction.setText("Paid to " + transaction.getNameL() + " from " + transaction.getNameT() + " at " + transaction.getTime()
        + " with ID: " + transaction.getTransactionID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction=itemView.findViewById(R.id.transaction);
        }
    }
}
