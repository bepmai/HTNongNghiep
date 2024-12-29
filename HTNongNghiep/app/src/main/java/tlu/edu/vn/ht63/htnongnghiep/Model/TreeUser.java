package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;

public class TreeUser implements Serializable {
    public TreeUser(String id, String idUser,String name, int age, String height, int timeWater, int timeSunbathing, String healthStatus, String temperature, String area, String type, String note) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.age = age;
        this.height = height;
        this.timeWater = timeWater;
        this.timeSunbathing = timeSunbathing;
        this.healthStatus = healthStatus;
        this.temperature = temperature;
        this.area = area;
        this.type = type;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getTimeWater() {
        return timeWater;
    }

    public void setTimeWater(int timeWater) {
        this.timeWater = timeWater;
    }

    public int getTimeSunbathing() {
        return timeSunbathing;
    }

    public void setTimeSunbathing(int timeSunbathing) {
        this.timeSunbathing = timeSunbathing;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String id;
    private String idUser;
    private int age;
    private String height;
    private int timeWater;
    private int timeSunbathing;
    private String healthStatus;
    private String temperature;
    private String area;
    private String type;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public String getIdTree() {
        return idTree;
    }

    public void setIdTree(String idTree) {
        this.idTree = idTree;
    }

    private String idTree;
}
