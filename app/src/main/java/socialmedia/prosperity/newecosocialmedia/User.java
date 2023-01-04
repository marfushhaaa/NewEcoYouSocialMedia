package socialmedia.prosperity.newecosocialmedia;

public class User {

    public String name, nickname, email, bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(){

    }

    public User(String name, String nickname, String email, String bio){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.bio = bio;
    }
}
