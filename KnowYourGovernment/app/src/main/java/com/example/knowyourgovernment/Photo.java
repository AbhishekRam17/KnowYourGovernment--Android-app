package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Photo extends AppCompatActivity {
    Official O;
    private TextView location;
    private TextView Role;
    private TextView Name;
    private ImageView official;
    private ImageView partysymbol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        location = findViewById(R.id.location);
        Role = findViewById(R.id.role);
        Name = findViewById(R.id.name);
        official = findViewById(R.id.photo);
        partysymbol = findViewById(R.id.symbol);

        Intent data = getIntent();
        if (data.hasExtra("loco")) {

            location.setText(data.getStringExtra("loco"));
        }

        O = (Official) data.getSerializableExtra("photo");
        setdata();

        if ( isConnected() != true) {
            official.setImageResource(R.drawable.brokenimage);
        }
        else {

            loadRemoteImage(O.getPhoto());
        }

      //  loadRemoteImage(O.getPhoto());
    }
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void setdata(){
        Role.setText(O.getRole());
        Name.setText(O.getName());


        if(O.getParty().contains( "Republican")){
            partysymbol.setImageResource( R.drawable.rep_logo );
            getWindow().getDecorView().setBackgroundColor( Color.RED);
        }
        else if(O.getParty().contains( "Democratic")){
            partysymbol.setImageResource( R.drawable.dem_logo);
            getWindow().getDecorView().setBackgroundColor( Color.BLUE);
        }

        else {
            partysymbol.setVisibility(View.GONE);
            getWindow().getDecorView().setBackgroundColor( Color.BLACK);
        }



    }
    public void logoclick(View v) {



        String Partywebsite = O.getParty();
        if ( Partywebsite.contains("Democratic" )){
            Uri uri =  Uri.parse("https://democrats.org");
            Intent demointent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(demointent);
        }
        else if (Partywebsite.contains( "Republican" )){

            Uri uri =  Uri.parse("https://www.gop.com");
            Intent repubintent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(repubintent);
        }

    }
    private void loadRemoteImage(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'

        final long start = System.currentTimeMillis(); // Used for timing

        Picasso.get().load(imageURL)
                .error(R.drawable.missing)
                .placeholder(R.drawable.placeholder)
                .into(official); // Use this if you don't want a callback

    }

}