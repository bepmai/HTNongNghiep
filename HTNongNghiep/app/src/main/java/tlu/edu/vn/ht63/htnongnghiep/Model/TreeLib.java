package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;
import java.util.List;

public class TreeLib implements Serializable {
    public TreeLib(){

    }
    public TreeLib(String id, String name, String unique, String mean, String heightMean, String temperature, String distribution, String area, String enviromentLive, String suns, String waters, String discription, String lifecycle, String feature, String trunk,List<String> images) {
        this.id = id;
        this.name = name;
        this.unique = unique;
        this.mean = mean;
        this.heightMean = heightMean;
        this.temperature = temperature;
        this.distribution = distribution;
        this.area = area;
        this.enviromentLive = enviromentLive;
        this.suns = suns;
        this.waters = waters;
        this.discription = discription;
        this.lifecycle = lifecycle;
        this.feature = feature;
        this.trunk = trunk;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getHeightMean() {
        return heightMean;
    }

    public void setHeightMean(String heightMean) {
        this.heightMean = heightMean;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEnviromentLive() {
        return enviromentLive;
    }

    public void setEnviromentLive(String enviromentLive) {
        this.enviromentLive = enviromentLive;
    }

    public String getSuns() {
        return suns;
    }

    public void setSuns(String suns) {
        this.suns = suns;
    }

    public String getWaters() {
        return waters;
    }

    public void setWaters(String waters) {
        this.waters = waters;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getTrunk() {
        return trunk;
    }

    public void setTrunk(String trunk) {
        this.trunk = trunk;
    }

    private String id;
    private String name;
    private String unique;
    private String mean;
    private String heightMean;
    private String temperature;
    private String distribution;
    private String area;
    private String enviromentLive;
    private String suns;
    private String waters;
    private String discription;
    private String lifecycle; // chu kỳ
    private String feature; // ăn quả , hoặc không , ăn lá , ăn rễ , không ăn được ???
    private String trunk;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    private List<String> images;
}
