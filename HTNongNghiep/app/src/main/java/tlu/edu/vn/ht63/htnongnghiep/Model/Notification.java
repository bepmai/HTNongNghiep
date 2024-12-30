package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;

public class Notification implements Serializable {
    //type=1 bài viết, type=2 farm, type=3 friend;
    public Notification(String id,String idUser, String uri, String titleContent, String contentMess, String time,int type) {
        this.id = id;
        this.uri = uri;
        this.titleContent = titleContent;
        this.contentMess = contentMess;
        this.time = time;
        this.type = type;
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getContentMess() {
        return contentMess;
    }

    public void setContentMess(String contentMess) {
        this.contentMess = contentMess;
    }

    private String id;
    private String uri;
    private String titleContent;
    private String contentMess;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    private String idUser;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
}
