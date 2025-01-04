package tlu.edu.vn.ht63.htnongnghiep.Model;

public class PlantShop {
    private String id;
    private String name;
    private String price;
    private int imageResId;

    // Constructor
    public PlantShop(String id, String name, String price, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}