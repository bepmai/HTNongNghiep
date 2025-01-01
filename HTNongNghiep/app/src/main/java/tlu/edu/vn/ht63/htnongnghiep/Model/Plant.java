package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Plant {
    private String name;
    private String price;
    private String discount;
    private String rating;
    private int imageResId;

    public Plant(String name, String price, String discount, String rating, int imageResId) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDiscount() { return discount; }
    public String getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}