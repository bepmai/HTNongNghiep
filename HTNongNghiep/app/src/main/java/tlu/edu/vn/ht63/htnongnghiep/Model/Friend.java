package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Friend {
    private String name, image,time,position,friendCount;
    public Friend(String name, String image, String time, String position, String friendCount) {
        this.name = name;
        this.image = image;
        this.time = time;
        this.position = position;
        this.friendCount = friendCount;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(String friendCount) {
        this.friendCount = friendCount;
    }
}
