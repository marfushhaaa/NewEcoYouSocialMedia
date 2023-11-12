package socialmedia.prosperity.newecosocialmedia.Objects;

public class Challenge {
    public Challenge(){

    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeBio() {
        return challengeBio;
    }

    public void setChallengeBio(String challengeBio) {
        this.challengeBio = challengeBio;
    }

    public String getChallengeHashtagDistricts() {
        return challengeHashtagDistricts;
    }

    public void setChallengeHashtagDistricts(String challengeHashtagDistricts) {
        this.challengeHashtagDistricts = challengeHashtagDistricts;
    }

    public String getChallengeHashtagTimeForDoing() {
        return challengeHashtagTimeForDoing;
    }

    public void setChallengeHashtagTimeForDoing(String challengeHashtagTimeForDoing) {
        this.challengeHashtagTimeForDoing = challengeHashtagTimeForDoing;
    }

    public String getChallengeHashtagTeamOwner() {
        return challengeHashtagTeamOwner;
    }

    public void setChallengeHashtagTeamOwner(String challengeHashtagTeamOwner) {
        this.challengeHashtagTeamOwner = challengeHashtagTeamOwner;
    }

    public String getChallengePhotoLink() {
        return challengePhotoLink;
    }

    public void setChallengePhotoLink(String challengePhotoLink) {
        this.challengePhotoLink = challengePhotoLink;
    }

    public String getChallengeDateOfCreation() {
        return challengeDateOfCreation;
    }

    public void setChallengeDateOfCreation(String challengeDateOfCreation) {
        this.challengeDateOfCreation = challengeDateOfCreation;
    }

    public String getChallengeTeamCreated() {
        return challengeTeamCreated;
    }

    public void setChallengeTeamCreated(String challengeTeamCreated) {
        this.challengeTeamCreated = challengeTeamCreated;
    }

    String challengeName, challengeBio, challengeHashtagDistricts, challengeHashtagTimeForDoing,
            challengeHashtagTeamOwner, challengePhotoLink, challengeDateOfCreation, challengeTeamCreated;
    public Challenge(String challengeName, String challengeBio, String challengeHashtagDistricts, String challengeHashtagTimeForDoing,
                     String challengeHashtagTeamOwner, String challengePhotoLink,String challengeDateOfCreation,String challengeTeamCreated){
        this.challengeName = challengeName;
        this.challengeBio = challengeBio;
        this.challengeHashtagDistricts = challengeHashtagDistricts;
        this.challengeHashtagTimeForDoing = challengeHashtagTimeForDoing;
        this.challengeHashtagTeamOwner = challengeHashtagTeamOwner;
        this.challengePhotoLink = challengePhotoLink;
        this.challengeDateOfCreation = challengeDateOfCreation;
        this.challengeTeamCreated = challengeTeamCreated;

    }

}
