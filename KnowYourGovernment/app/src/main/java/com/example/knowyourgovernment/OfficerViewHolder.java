package com.example.knowyourgovernment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OfficerViewHolder extends RecyclerView.ViewHolder {

    TextView role;
    TextView party;
    TextView name;


    OfficerViewHolder  (View view) {
        super(view);
        role = view.findViewById(R.id.roleid);
        party = view.findViewById(R.id.partyid);
        name = view.findViewById(R.id.nameid);


    }


    }
