package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Plant {
    private String name;
    private String price;
    private Float rating;
    private int imageResId;

    public Plant(String name, String price, Float rating, int imageResId) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public Float getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}