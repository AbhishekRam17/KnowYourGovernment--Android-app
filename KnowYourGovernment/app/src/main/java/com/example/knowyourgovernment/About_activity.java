package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class About_activity extends AppCompatActivity {
    private TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        link = findViewById(R.id.textView3);

//        Linkify.TransformFilter myTransformFilter = new Linkify.TransformFilter() {
//            @Override
//            public String transformUrl(Matcher match, String url) {
//                return url.substring(1); //remove the $ sign
//            }
//        };
        link.setMovementMethod(LinkMovementMethod.getInstance());
//        //  TextView myCustomLink3 = new TextView(this);
//        Pattern pattern3 = Pattern.compile("\\$[a-zA-Z]+");
//        link.setText("GOOGLE CIVIC ONFORMATION API");
//        Linkify.addLinks(link,pattern3, "https://developers.google.com/civic-information/", null, myTransformFilter);
////mainLayout.addView(link);
//
  //      Linkify.addLinks(link, "https://developers.google.com/civic-information/");



    }


}