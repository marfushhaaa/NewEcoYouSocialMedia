package socialmedia.prosperity.newecosocialmedia;

public class Post {
    public Post() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    String name;
    String text;
    String photo_link;
    String dateCreation;

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    String userCreated;
    public Post(String name, String text, String photo_link, String dateCreation, String userCreated) {
        this.name = name;
        this.text = text;
        this.photo_link = photo_link;
        this.dateCreation = dateCreation;
        this.userCreated = userCreated;
    }

}
