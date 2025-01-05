package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Chat {
    private String profileImage, userName, message, timestamp,userId;

    public Chat(String profileImage, String userName, String message, String timestamp,String userId) {
        this.profileImage = profileImage;
        this.userName = userName;
        this.message = message;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
