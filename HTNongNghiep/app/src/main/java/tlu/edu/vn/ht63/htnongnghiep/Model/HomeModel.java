package tlu.edu.vn.ht63.htnongnghiep.Model;

public class HomeModel {
    private String userName, userImage,userRole,timestamp, postImage, postTitle, postDescription, postTime;
    private int likeCount, commentCount, shareCount;
    public HomeModel(){

    }
    public HomeModel(String userName, String userImage,String userRole,String timestamp, String postImage, String postTitle, String postDescription, String postTime, int likeCount, int commentCount, int shareCount) {
        this.userName = userName;
        this.userImage = userImage;
        this.postImage = postImage;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postTime = postTime;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
