package socialmedia.prosperity.newecosocialmedia;
public class Team {
    String name, bio, shortBio, membernumber, dateCreated;
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
    public Team(String name, String bio, String shortBio, String membernumber, String dateCreated) {
        this.name = name;
        this.bio = bio;
        this.shortBio = shortBio;
        this.membernumber = membernumber;
//        this.totalMembers = totalMembers;
        this.dateCreated = dateCreated;
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
