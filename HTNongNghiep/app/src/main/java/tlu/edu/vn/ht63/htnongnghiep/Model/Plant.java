package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;
import java.util.Date;

public class Plant implements Serializable {
    private String id;
    private String idplant;
    private String userid;
    private String nameuser;
    private Date datesell;
    private String address;
    private String description;
    private String name;
    private Float price;
    private Float rating;
    private String image;

    public Plant() {
    }

    public Plant(String id, String idplant, String userid, String nameuser, Date datesell, String address, String description, String name, Float price, Float rating, String image) {
        this.id = id;
        this.idplant = idplant;
        this.userid = userid;
        this.nameuser = nameuser;
        this.datesell = datesell;
        this.address = address;
        this.description = description;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdplant() {
        return idplant;
    }

    public void setIdplant(String idplant) {
        this.idplant = idplant;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public Date getDatesell() {
        return datesell;
    }

    public void setDatesell(Date datesell) {
        this.datesell = datesell;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}