package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.util.Date;

public class ReviewPlant {
    private String id;
    private String idPlantShop;
    private String iduser;
    private String nameuser;
    private String nameplant;
    private Date date;
    private Float statrating;
    private String review;

    public ReviewPlant(){}

    public ReviewPlant(String id, String idPlantShop, String iduser, String nameuser, String nameplant, Date date, Float statrating, String review) {
        this.id = id;
        this.idPlantShop = idPlantShop;
        this.iduser = iduser;
        this.nameuser = nameuser;
        this.nameplant = nameplant;
        this.date = date;
        this.statrating = statrating;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPlantShop() {
        return idPlantShop;
    }

    public void setIdPlantShop(String idPlantShop) {
        this.idPlantShop = idPlantShop;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getNameplant() {
        return nameplant;
    }

    public void setNameplant(String nameplant) {
        this.nameplant = nameplant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getStatrating() {
        return statrating;
    }

    public void setStatrating(Float statrating) {
        this.statrating = statrating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
