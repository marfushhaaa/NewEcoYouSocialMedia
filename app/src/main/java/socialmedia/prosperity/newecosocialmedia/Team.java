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

    public String getChallenges() {
        return challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    String challenges;

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
    public int challengeCount;

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
    public Team(String name, String bio, String shortBio, String membernumber, String dateCreated, String hashtag,
                String teamPlaces, String challenges, int challengeCount) {
        this.name = name;
        this.bio = bio;
        this.shortBio = shortBio;
        this.membernumber = membernumber;
        this.teamPlaces = teamPlaces;
        this.dateCreated = dateCreated;
        this.hashtag = hashtag;
        this.challenges = challenges;
        this.challengeCount = challengeCount;
    }

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
