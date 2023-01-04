package socialmedia.prosperity.newecosocialmedia;
public class Team {
    String name, bio, hash, membernumber, dateCreated;
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
    public Team(String name, String bio, String hash, String membernumber, String dateCreated) {
        this.name = name;
        this.bio = bio;
        this.hash = hash;
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
