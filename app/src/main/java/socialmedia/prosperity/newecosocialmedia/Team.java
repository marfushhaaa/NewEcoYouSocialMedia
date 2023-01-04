package socialmedia.prosperity.newecosocialmedia;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Team {
    String name, bio, hash, membernumber;

    Team(){

    }
    public Team(String name, String bio, String hash, String membernumber) {
        this.name = name;
        this.bio = bio;
        this.hash = hash;
        this.membernumber = membernumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMembernumber() {
        return membernumber;
    }

    public void setMembernumber(String membernumber) {
        this.membernumber = membernumber;
    }
}
