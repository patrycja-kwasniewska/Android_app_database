package com.example.aplikacja2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter extends ArrayAdapter<Phone> {

    private RecyclerViewInterface recyclerViewInterface;

    private List<Phone> phoneList;
    private AdapterView.OnItemClickListener mListener;

    public PhoneListAdapter(Context context, List<Phone> phoneList) {
        super(context, 0, phoneList);
        this.phoneList = phoneList;


    }

    @NonNull
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new PhoneViewHolder(view);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        TextView producerTextView = convertView.findViewById(R.id.text_view_producer);
        TextView modelTextView = convertView.findViewById(R.id.text_view_model);
        //TextView versionTextView = convertView.findViewById(R.id.text_view_version);

        Phone phone = phoneList.get(position);

        producerTextView.setText(phone.producer);
        modelTextView.setText(phone.model);
        //versionTextView.setText(phone.version);

        return convertView;
    }

    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        final Phone phone = phoneList.get(position);
        holder.mTextViewName.setText(phone.getManufacturer());
        holder.mTextViewNumber.setText(phone.getModel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onItemClick(phone);
            }
        });
    }

    public int getItemCount() {
        return phoneList.size();
    }



    public class PhoneViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewName;
        public TextView mTextViewNumber;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.text_view_producer);
            mTextViewNumber = itemView.findViewById(R.id.text_view_model);
        }
    }



}
