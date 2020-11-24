package com.example.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity   implements View.OnClickListener, View.OnLongClickListener{
    private RecyclerView recyclerView;
    private OfficialAdapter OfficialAdapter;
    private ArrayList<Official> OfficialList = new ArrayList<>();
    private static int MY_LOCATION_REQUEST_CODE_ID = 111;
    private LocationManager locationManager;
    private Criteria criteria;
    public TextView locationg;
    private static final String TAG = "ggwp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById( R.id.recycler );
        OfficialAdapter = new OfficialAdapter( OfficialList, this );
        recyclerView.setAdapter( OfficialAdapter );
locationg = findViewById(R.id.locationtextView);

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        // use gps for location
      //  criteria.setPowerRequirement(Criteria.POWER_LOW);
       // criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        // use network for location
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
if(isConnected()) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                MY_LOCATION_REQUEST_CODE_ID);
    } else {
        setLocation();
    }
}
else if (!isConnected()){
    locationg.setText("No Data For Location");
    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("No Network Connection");
    builder.setMessage("Content Cannot Be Added Without A Network Connection");
    androidx.appcompat.app.AlertDialog dialog = builder.create();
    dialog.show();
    return;
}
        Log.d(TAG, "onCreate: " + isConnected() );
//       for (int i = 0; i < 30; i++) {
//           OfficialList.add(new Official("jkjk","h","h","g","j","g","h","h","u","u","i","u"));
//       }
    }

    public void officiallist(List<Official> officials) {

        OfficialList.addAll(officials);
        OfficialAdapter.notifyDataSetChanged();

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
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull
            String[] permissions, @NonNull
                    int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
                return;
            }
        }
        ((TextView) findViewById(R.id.locationtextView)).setText("No Data For Location");

    }

    @SuppressLint("MissingPermission")
    private void setLocation() {
        boolean connected = isConnected();
        String bestProvider = locationManager.getBestProvider(criteria, true);
     //   ((TextView) findViewById(R.id.bestText)).setText(bestProvider);

        Location currentLocation = locationManager.getLastKnownLocation(bestProvider);

        if (currentLocation != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresses;

            try {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 5);
//                ((TextView) findViewById(R.id.locText)).setText(
//                        String.format(Locale.getDefault(),
//                                "%.4f, %.4f", currentLocation.getLatitude(), currentLocation.getLongitude()));
                Log.d(TAG, "onCreate: " + currentLocation.getLatitude() + currentLocation.getLongitude() + addresses );
                displayAddresses( addresses);
            }
            catch (IOException j){
                j.printStackTrace();
            }

        } else {
            ((TextView) findViewById(R.id.locationtextView)).setText("No Data For Location");
        }


    }

    public void recheckLocation(View v) {
        Toast.makeText(this, "Rechecking Location", Toast.LENGTH_SHORT).show();
        setLocation();
    }

//    public void doLatLon(View v) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            List<Address> addresses;
//
//            String loc = ((EditText) findViewById(R.id.editText)).getText().toString();
//            if (loc.trim().isEmpty()) {
//                Toast.makeText(this, "Enter Lat & Lon coordinates first!", Toast.LENGTH_LONG).show();
//                return;
//            }
//            String[] latLon = loc.split(",");
//            double lat = Double.parseDouble(latLon[0]);
//            double lon = Double.parseDouble(latLon[1]);
//
//            addresses = geocoder.getFromLocation(lat, lon, 10);
//
//            displayAddresses(addresses);
//            Log.d(TAG, "onCreate: " + addresses.get(0));
//        } catch (IOException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }

//    public void doLocationName(View v) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            List<Address> addresses;
//
//            String loc = ((EditText) findViewById(R.id.editText)).getText().toString();
//            addresses = geocoder.getFromLocationName(loc, 10);
//            displayAddresses(addresses);
//        } catch (IOException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }

    private void displayAddresses(List<Address> addresses) {
        String city = "", zip = "",state="";
        StringBuilder sb = new StringBuilder();
        if (addresses.size() == 0) {

            return;
        }

        for (Address ad : addresses) {


            if (zip.equals("")) {
                if (ad.getPostalCode() == null){
                    zip = "";
                }
                else{
                    zip = ad.getPostalCode();
                }

            }
//            String a = String.format("%s %s %s %s %s %s",
//                    (ad.getSubThoroughfare() == null ? "" : ad.getSubThoroughfare()),
//                    (ad.getThoroughfare() == null ? "" : ad.getThoroughfare()),
//                    (ad.getLocality() == null ? "" : ad.getLocality()),
//                    (ad.getAdminArea() == null ? "" : ad.getAdminArea()),
//                    (ad.getPostalCode() == null ? "" : ad.getPostalCode()),
//                    (ad.getCountryName() == null ? "" : ad.getCountryName()));
//
//            if (!a.trim().isEmpty())
//                sb.append("* ").append(a.trim());
//
//            sb.append("\n");

            if (city.equals("")) {
                if (ad.getLocality() == null){
                    city = "";
                }
                else{
                    city = ad.getLocality();
                }
            }

            if (state.equals("")) {
                if (ad.getAdminArea() == null){
                    state = "";
                }
                else{
                    state = ad.getAdminArea();
                }
            }
        }

        String ad = zip;
        if (ad.equals("")) {
            ad = city;
        }

        if (ad.equals("")) {
            ad = state;
        }
        Log.d(TAG, "displayAddresses: " + city + ", " + state + " " + zip);

        OfficialDownloader countryDownloader = new OfficialDownloader(this, ad);
        new Thread(countryDownloader).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                makeCountryDialog();
                return true;

            case R.id.aboutgg:

                Intent intent = new Intent(this, About_activity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void makeCountryDialog() {

        if (!isConnected()) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Content Cannot Be Added Without A Network Connection");
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        final MainActivity click = this;

        builder.setView(et);

//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
////                choice = et.getText().toString().trim();
////
////                final ArrayList<String> results = SymbolNameDownloader.findMatches(choice);
////
////                if (results.size() == 0) {
////                    doNoAnswer(choice);
////                } else if (results.size() == 1) {
////                    doSelection(results.get(0));
////                } else {
////                    String[] array = results.toArray(new String[0]);
//
//                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Make a selection");
////                    builder.setItems(array, new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int which) {
////                            String symbol = results.get(which);
////                            doSelection(symbol);
////                        }
////                    });
//
//            }}
//                    builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//                    androidx.appcompat.app.AlertDialog dialog2 = builder.create();
//                    dialog2.show();
//                }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User cancelled the dialog
//            }
//        });
        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
OfficialList.clear();
                String location = et.getText().toString().trim();

                OfficialDownloader countryDownloader = new OfficialDownloader(click, location);
                new Thread(countryDownloader).start();
                OfficialAdapter.notifyDataSetChanged();
            }
        } );
        builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        } );


       // builder.setMessage("Enter a City, State or a Zip Code");
        builder.setTitle("Enter a City, State or a Zip Code");

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    @Override
    public void onClick(View v) {
        int   pos = recyclerView.getChildLayoutPosition(v);
        Official Office = OfficialList.get(pos);

        Intent intent = new Intent(this, OfficalAbout.class);
        intent.putExtra("f2", Office);
        intent.putExtra("l2", (locationg.getText().toString()));
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}