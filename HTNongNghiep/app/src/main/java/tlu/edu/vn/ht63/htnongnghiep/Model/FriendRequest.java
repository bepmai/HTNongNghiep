package tlu.edu.vn.ht63.htnongnghiep.Model;

public class FriendRequest {
    private String senderId;
    private String senderName;
    private String status;

    public FriendRequest(){

    }
    public FriendRequest(String senderId, String senderName, String status) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.status = status;
    }

    // Getter và Setter (nếu cần thiết)
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}