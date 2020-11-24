package com.example.knowyourgovernment;

import java.io.Serializable;
import java.util.List;

public class Official implements Serializable {
    private String role, name, party, phone, url, email, photo, fb, twitter, youtube;
    private List<String> address;
    public Official (String role, String name, String party, List<String> address, String phone,
                    String url, String email, String photo, String fb, String twitter, String youtube){
        this.name =  name;
        this.role =  role;
        this.party = party;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.email = email;
        this.photo = photo;

        this.fb= fb;
        this.twitter = twitter;
        this.youtube = youtube;
    }

    public String getName() { return name; }
    public String getRole (){ return role; }
    public String getParty() { return party; }
    public String getPhoto() { return photo; }

    public String getUrl() { return url; }
    public String getEmail() { return email; }
    public String getYoutube() { return youtube; }
    public String getFb() { return fb; }

    public String getTwitter() { return twitter; }

    public List<String> getAddresses() {
        return address;
    }
    public void setName(String myName) { this.name = myName; }

    public String getPhone() { return phone; }
}
