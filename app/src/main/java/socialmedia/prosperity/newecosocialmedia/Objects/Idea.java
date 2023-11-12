package socialmedia.prosperity.newecosocialmedia.Objects;

public class Idea {
    public Idea(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }

    String name, description, photoLink, owner;
    String [] hashtags;


    public Idea(String name, String description, String photoLink, String owner){
        this.name = name;
        this.description = description;
        this.photoLink = photoLink;
        this.owner = owner;
    }
}
