package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Message1{
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "";
    String message;
    String sentBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Message1(String sentBy, String message) {
        this.sentBy = sentBy;
        this.message = message;
    }
}
