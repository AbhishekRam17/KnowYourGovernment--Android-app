package com.example.knowyourgovernment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OfficialAdapter extends RecyclerView.Adapter<OfficerViewHolder> {
    private ArrayList<Official> officialList;
    private MainActivity mainActivity;


    OfficialAdapter(ArrayList<Official> list, MainActivity mA) {
        officialList = list;
        this.mainActivity = mA;
    }
    @NonNull
    @Override
    public OfficerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official_list_entry, parent, false);

        View.setOnClickListener( mainActivity );
        View.setOnLongClickListener(mainActivity);
        return new OfficerViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficerViewHolder holder, int position) {
        Official selected = officialList.get(position);
        holder.role.setText(selected.getRole());
        holder.name.setText( selected.getName() );
        holder.party.setText( String.format( "(%s)", selected.getParty()) );
    }

    @Override
    public int getItemCount() {

            return officialList.size();
    }
}
