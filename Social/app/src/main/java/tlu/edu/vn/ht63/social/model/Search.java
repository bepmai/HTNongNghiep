package tlu.edu.vn.ht63.social.model;

public class Search {
    private String name, profileImage;

    public Search(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
