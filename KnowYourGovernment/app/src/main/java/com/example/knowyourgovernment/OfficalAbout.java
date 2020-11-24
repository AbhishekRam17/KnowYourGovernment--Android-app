package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class OfficalAbout extends AppCompatActivity {
    private TextView location;
    private TextView Role;
    private TextView Name;
    private TextView Party;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView website;
    private TextView Addressdata;
    private TextView Phonedata;
    private TextView Emaildata;
    private TextView Websitedata;
    private ImageView official;
    private ImageView partysymbol;
    private ImageView facebook;
    private ImageView twitter;
    private ImageView youtube;

    Official O;
    private static final String TAG = "gtr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offical_about);

        location = findViewById(R.id.textView6);
        Role = findViewById(R.id.role);
        Name = findViewById(R.id.name);
        Party = findViewById(R.id.party);
        address = findViewById(R.id.textView10);
        phone = findViewById(R.id.textView12);
        email = findViewById(R.id.textView13);
        website = findViewById(R.id.textView17);
        Addressdata = findViewById(R.id.textView14);
        Phonedata = findViewById(R.id.textView15);
        Emaildata = findViewById(R.id.textView16);
        Websitedata = findViewById(R.id.textView18);
        official = findViewById(R.id.imageView2);
        partysymbol = findViewById(R.id.imageView3);
        facebook = findViewById(R.id.imageView4);
        twitter = findViewById(R.id.imageView5);
        youtube = findViewById(R.id.imageView6);

        setdata();

officerdata();

        if (O.getPhoto().isEmpty() || isConnected() != true) {
            official.setImageResource(R.drawable.brokenimage);
        }
        else {

            loadRemoteImage(O.getPhoto());
        }

        Role.setText(O.getRole());
        Name.setText(O.getName());
        Party.setText("(" + O.getParty() + ")");

backgroundandlogo();


        Linkify.addLinks( Addressdata, Linkify.ALL );
        Linkify.addLinks( Phonedata,Linkify.ALL);
        Linkify.addLinks( Emaildata,Linkify.ALL);
        Linkify.addLinks( Websitedata,Linkify.ALL);

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
    public void linkup( View v){
        if (O.getPhoto().isEmpty()) {
          return;
        }
        else {
            Intent intent = new Intent( OfficalAbout.this, Photo.class );

            intent.putExtra("photo", O);
           intent.putExtra("loco", (location.getText().toString()));
           startActivity(intent);
        }
    }

    private void backgroundandlogo() {
        Log.d(TAG, "backgroundandlogo: "+ O.getParty());
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







    private void officerdata(){

        if (O.getAddresses().isEmpty()) {
            address.setVisibility(View.GONE);
            Addressdata.setVisibility(View.GONE);
        }
        else {

     //     Addressdata.setText(Arrays.toString(O.getAddresses().toArray()));
            Addressdata.setText(O.getAddresses().get(0));

        }

        if (O.getPhone().isEmpty() ){
            phone.setVisibility(View.GONE);
            Phonedata.setVisibility(View.GONE);
        }
        else {
            Phonedata.setText( O.getPhone());

        }

        if (O.getEmail().isEmpty()) {
            Emaildata.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
        }
        else{
            Emaildata.setText( O.getEmail() );

        }

        if (O.getUrl().isEmpty()) {
            Websitedata.setVisibility(View.GONE);
            website.setVisibility(View.GONE);
        }
        else{
            Websitedata.setText( O.getUrl() );

        }
    }
    public void setdata() {
        Intent data = getIntent();
        if (data.hasExtra("l2")) {
       //     location.setText((String) data.getSerializableExtra("l2"));
            location.setText(data.getStringExtra("l2"));
        }

        O = (Official) data.getSerializableExtra("f2");
    }

    private void loadRemoteImage(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'

        final long start = System.currentTimeMillis(); // Used for timing

        Picasso.get().load(imageURL)
                .error(R.drawable.missing)
                .placeholder(R.drawable.placeholder)
                .into(official); // Use this if you don't want a callback

    }


    public void twitterClicked(View v) {
        Intent intent = null;
        String name = O.getTwitter();
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + O.getFb();
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" + O.getFb();
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void youTubeClicked(View v) {

        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + O.getYoutube()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + O.getYoutube())));

        }


    }
}