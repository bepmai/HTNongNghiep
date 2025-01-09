package tlu.edu.vn.ht63.htnongnghiep.Model;

public class FriendSuggest {
    private String id, bgImage, profileImage,role, fullName, mutualFriends;
    public FriendSuggest() {
    }
    public FriendSuggest(String id, String bgImage, String profileImage, String role, String fullName, String mutualFriends) {
        this.id = id;
        this.bgImage = bgImage;
        this.profileImage = profileImage;
        this.role = role;
        this.fullName = fullName;
        this.mutualFriends = mutualFriends;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String name) {
        this.fullName = name;
    }

    public String getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(String mutualFriends) {
        this.mutualFriends = mutualFriends;
    }
}
