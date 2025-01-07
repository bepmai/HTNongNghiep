package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Friend {
    private String name, image,time,position,friendCount, friendId,status ,firebasePath;
    public Friend(){

    }
    public Friend(String name, String image, String time, String position, String friendCount,String friendId,String status, String firebasePath) {
        this.name = name;
        this.image = image;
        this.time = time;
        //this.position = position;
        //this.friendCount = friendCount;
        this.friendId = friendId;
        this.status = status;
        this.firebasePath = firebasePath;
    }

    public String getFirebasePath() {
        return firebasePath;
    }

    public void setFirebasePath(String firebasePath) {
        this.firebasePath = firebasePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFriendId() {
        return friendId;
    }
    public void setFriendId(String friendId){
        this.friendId = friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
