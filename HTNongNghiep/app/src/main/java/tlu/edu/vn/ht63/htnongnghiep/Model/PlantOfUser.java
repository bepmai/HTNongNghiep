package tlu.edu.vn.ht63.htnongnghiep.Model;

public class PlantOfUser {
    private String image;
    private String nameplant;
    private int ageplant;
    private float height;
    private int weeklyWatering;
    private int weeklySunExposure;
    private String health;
    private String temperature;
    private String environment;
    private String type;
    private String note;

    public PlantOfUser(){}
    public PlantOfUser(String image, String nameplant, int ageplant, float height, int weeklyWatering, int weeklySunExposure, String health, String temperature, String environment, String type, String note) {
        this.image = image;
        this.nameplant = nameplant;
        this.ageplant = ageplant;
        this.height = height;
        this.weeklyWatering = weeklyWatering;
        this.weeklySunExposure = weeklySunExposure;
        this.health = health;
        this.temperature = temperature;
        this.environment = environment;
        this.type = type;
        this.note = note;
    }
    public String getImage(){
        return image;
    }
    public String getNameplant() {
        return nameplant;
    }

    public int getAgeplant() {
        return ageplant;
    }

    public float getHeight() {
        return height;
    }

    public int getWeeklyWatering() {
        return weeklyWatering;
    }

    public int getWeeklySunExposure() {
        return weeklySunExposure;
    }

    public String getHealth() {
        return health;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public void setImage(String image){
        this.image = image;
    }
    public void setNameplant(String nameplant) {
        this.nameplant = nameplant;
    }

    public void setAgeplant(int ageplant) {
        this.ageplant = ageplant;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeeklyWatering(int weeklyWatering) {
        this.weeklyWatering = weeklyWatering;
    }

    public void setWeeklySunExposure(int weeklySunExposure) {
        this.weeklySunExposure = weeklySunExposure;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNote(String note) {
        this.note = note;
    }
}


