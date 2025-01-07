package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Plant {
    private String id;
    private String idplant;
    private String userid;
    private String nameuser;
    private String datesell;
    private String address;
    private String description;
    private String name;
    private String price;
    private Float rating;
    private int imageResId;

    public Plant(String name, String price, Float rating, int imageResId,String description ,String id, String idplant, String iduser, String nameuser, String datesell, String adress) {
        this.name = name;
        this.id = id;
        this.idplant = idplant;
        this.userid = iduser;
        this.nameuser = nameuser;
        this.datesell = datesell;
        this.address = adress;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public void setName(String name) { this.name = name; }

    public void setId(String id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setIdplant(String idplant) { this.idplant = idplant; }

    public void setUserid(String userid) { this.userid = userid; }
    public void setNameuser(String nameuser) { this.nameuser = nameuser; }
    public void setDatesell(String datesell) { this.datesell = datesell; }
    public void setAddress(String address) { this.address = address; }
    public void setPrice(String price) { this.price = price; }
    public void setRating(Float rating) { this.rating = rating; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }


    public String getId() { return id; }
    public String getIdplant() { return idplant; }

    public String getUserid() { return userid; }
    public String getNameuser() { return nameuser; }
    public String getDatesell() { return datesell; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public Float getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}