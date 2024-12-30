package tlu.edu.vn.ht63.htnongnghiep.Model;

public class FriendSuggest {
    private String bgImage, profileImage,role, name, mutualFriends;

    public FriendSuggest(String bgImage, String profileImage, String role, String name, String mutualFriends) {
        this.bgImage = bgImage;
        this.profileImage = profileImage;
        this.role = role;
        this.name = name;
        this.mutualFriends = mutualFriends;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(String mutualFriends) {
        this.mutualFriends = mutualFriends;
    }
}
