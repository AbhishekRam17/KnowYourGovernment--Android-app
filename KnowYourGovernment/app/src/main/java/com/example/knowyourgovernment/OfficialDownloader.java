package com.example.knowyourgovernment;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OfficialDownloader implements Runnable {

    private static final String TAG = "vainko";
    private static final String REGION_URL = "https://www.googleapis.com/civicinfo/v2/representatives?key=";
    String fb = "";
    String twitter = "";
    String youtube = "";
    String address = "";
    String line1 = "";
    String line2 = "";
    String line3 = "";
    String ocity = "";
    String ostate = "";
    String ozip = "";
    private static final String APIKey = "AIzaSyDY8-j8EhLNtR0nucF-vBTU1LQ6jW0p_WQ";
    private MainActivity mainActivity;
    private String searchTarget;

    public OfficialDownloader(MainActivity mainActivity, String searchTarget) {
        this.mainActivity = mainActivity;
        this.searchTarget = searchTarget;
    }
    @Override
    public void run() {
//        String query = String.format( "/%s/quote?token=%s", searchTarget, APIKey );
//        Log.d(TAG, "run: " + searchTarget);

        String dataURL =REGION_URL + APIKey + "&address=" +searchTarget ;
       // Uri.Builder uriBuilder = Uri.parse( REGION_URL.concat(dataURL ) ).buildUpon();
        Uri.Builder buildURL = Uri.parse(dataURL ).buildUpon();
        // Uri.Builder uriBuilder = Uri.parse(REGION_URL + searchTarget).buildUpon();
        //uriBuilder.appendQueryParameter("fullText", "true");
        String urlToUse = buildURL.build().toString();

        Log.d(TAG, "run: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "run1: HTTP ResponseCode NOT OK: " + conn.getResponseCode());
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "run2: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
            return;
        }

        process(sb.toString());
        Log.d(TAG, "run: ");

    }

    private void process(String s) {
        Log.d(TAG, "processgg: start " );

        final ArrayList<Official> officiallist = new ArrayList<>();
        List<String> addresslist = new ArrayList<String>();
        try {
            //  JSONArray jArray = new JSONArray(s);
            //     JSONObject jCountry = (JSONObject) jArray.get(0);
            JSONObject jCountry = new JSONObject( s );
            JSONObject normalizedInput = jCountry.getJSONObject("normalizedInput");
            final String city = normalizedInput.getString("city").trim();
            final String state = normalizedInput.getString("state").trim();
            final String zip = normalizedInput.getString("zip").trim();
          //   mainActivity.locationg.setText(city + ", " + state + " " + zip);



            JSONArray offices = jCountry.getJSONArray("offices");
            JSONArray officials = jCountry.getJSONArray("officials");

            for(int i = 0; i < offices.length(); i++){
                JSONObject object = offices.getJSONObject(i);
                JSONArray oindices = object.getJSONArray("officialIndices");
                String office = object.getString("name").trim();

                for(int x = 0; x < oindices.length(); x++){
                    int tempIndex = oindices.getInt(x);
                    JSONObject governmentobject = officials.getJSONObject(tempIndex);




                    String name = governmentobject.getString("name");





                    if(governmentobject.has("address")) {
                        JSONArray addressArray = governmentobject.getJSONArray("address");

                        for(int p = 0; p < addressArray.length(); p++) {


                            JSONObject addressj = addressArray.getJSONObject(p);

                            if (addressj.has("line1")) {
                                line1 = addressj.getString("line1").trim();
                            }

                            if (addressj.has("line2")) {
                                line2 = addressj.getString("line2").trim();
                            }
//            String pop = jCountry.getString("population");
//            int population = 0;
//            if (!pop.trim().isEmpty())
//                population = Integer.parseInt(pop);
//
//            String a = jCountry.getString("area");
//            int area = 0;
//            if (!a.trim().isEmpty() && !a.trim().equals("null"))
//                area = (int) Double.parseDouble(a);
//
//            String citizen = jCountry.getString("demonym");
//
//            StringBuilder codes = new StringBuilder();
//            JSONArray jCodes = jCountry.getJSONArray("callingCodes");
//            for (int j = 0; j < jCodes.length(); j++) {
//                codes.append(jCodes.get(j)).append(" ");
//            }
//
//            StringBuilder borders = new StringBuilder();
//            JSONArray jBorders = jCountry.getJSONArray("borders");
//            for (int j = 0; j < jBorders.length(); j++) {
//                borders.append(jBorders.get(j)).append(" ");
//            }


                            if (addressj.has("line3")) {
                                line3 = addressj.getString("line3").trim();
                            }
                            if (addressj.has("city")) {
                                ocity = addressj.getString("city").trim();
                            }
                            if (addressj.has("state")) {
                                ostate = addressj.getString("state").trim();
                            }
                            if (addressj.has("zip")) {
                                ozip = addressj.getString("zip").trim();
                            }

//            final Stock country = new Stock(name, alpha3Code,
//                    price,  priceChange, percentChange);
//            Log.d(TAG, "run: " + country);
//            mainActivity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mainActivity.addCountry(country);
//                }
//            });
                            if (!line1.equals("")) {
                                address = address + line1 + "\n";
                            }
                            if (!line2.equals("")) {
                                address = address + line2 + "\n";
                            }
                            if (!line3.equals("")) {
                                address = address + line3 + "\n";
                            }

                            if(p == addressArray.length()-1) {
                                address = address + ocity + ", " + ostate + " " + ozip;
                            }else{
                                address = address.trim();
                            }

                            addresslist.add(address);
                        }
                    }



                    String party = "";
                    if(governmentobject.has("party")) {
                        party = governmentobject.getString("party");
                        if (party.equals("")) {
                            party = "No data";
                        }
                    }


                    String phone = "";
                    if(governmentobject.has("phones")) {
                        JSONArray phoneArray = governmentobject.getJSONArray("phones");

                        if (phoneArray != null && phoneArray.length() > 0) {
                            phone = phoneArray.get( 0 ).toString();
                        }
                    }


                    String websiteUrl = "";
                    if(governmentobject.has("urls")) {
                        JSONArray weburlArray = governmentobject.getJSONArray("urls");

                        if (weburlArray != null && weburlArray.length() > 0) {
                            websiteUrl = weburlArray.get( 0 ).toString();
                        }
                    }


                    String email = "";
                    if(governmentobject.has("emails")) {
                        JSONArray emailArray = governmentobject.getJSONArray("emails");

                        if (emailArray != null && emailArray.length() > 0) {
                            email = emailArray.get( 0 ).toString();
                        }
                    }


                    String photoURL = "";
                    if(governmentobject.has("photoUrl")) {
                        photoURL = governmentobject.getString("photoUrl").trim();
                    }




// socials
                    if(governmentobject.has("channels")) {
                        JSONArray channels = governmentobject.getJSONArray("channels");
                          int gg = channels.length();
                        for (int j = 0; j < gg; j++) {

                            String socials = channels.getJSONObject(j).getString("type");


                            if (socials.equals( "Facebook" )){
                                fb = channels.getJSONObject(j).getString("id");
                            }
                            if (socials.equals( "Twitter" )){
                                twitter = channels.getJSONObject(j).getString("id");
                            }
                            if (socials.equals( "YouTube" )) {
                                youtube = channels.getJSONObject(j).getString("id");
                            }
                        }
                    }
                  final  Official country = new Official(office, name, party, addresslist, phone, websiteUrl, email, photoURL, fb, twitter, youtube);
                    officiallist.add(country);

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.locationg.setText(city + ", " + state + " " + zip);
                            mainActivity.officiallist(officiallist);
                        }
                    });


                }

            }













        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
