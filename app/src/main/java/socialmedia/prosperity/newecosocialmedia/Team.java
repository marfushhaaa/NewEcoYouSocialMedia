package socialmedia.prosperity.newecosocialmedia;

import java.util.ArrayList;
import java.util.List;

public class Team {
    String name;
    String bio;
    String shortBio;
    List<String> userList = new ArrayList<String>();
    String hashtag;
    String membernumber;
    String dateCreated;

    public String getTeamPlaces() {
        return teamPlaces;
    }

    public void setTeamPlaces(String teamPlaces) {
        this.teamPlaces = teamPlaces;
    }

    String teamPlaces;
    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int users;

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

//    int totalMemberNumber;
//
//    public int getTotalMemberNumber() {
//        return totalMemberNumber;
//    }
//
//    public void setTotalMemberNumber(int totalMemberNumber) {
//        this.totalMemberNumber = totalMemberNumber;
//    }

    Team(){

    }
    public Team(String name, String bio, String shortBio, String membernumber, String dateCreated, String hashtag, String teamPlaces) {
        this.name = name;
        this.bio = bio;
        this.shortBio = shortBio;
        this.membernumber = membernumber;
        this.teamPlaces = teamPlaces;
//        this.totalMembers = totalMembers;
        this.dateCreated = dateCreated;
        this.hashtag = hashtag;
    }
//
//    public String getTotalMembers() {
//        return totalMembers;
//    }
//
//    public void setTotalMembers(String totalMembers) {
//        this.totalMembers = totalMembers;
//    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public String getMembernumber() {
        return membernumber;
    }

    public void setMembernumber(String membernumber) {
        this.membernumber = membernumber;
    }
}
