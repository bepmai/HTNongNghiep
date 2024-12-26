package tlu.edu.vn.ht63.htnongnghiep;

public class WeatherRVModal {
    private String time;
    private String temperature;
    private String icon;
    private String windSpeed;
    private String humidity;

    public WeatherRVModal(String time, String temperature, String icon, String windSpeed, String humidity) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity(){
        return humidity;
    }

    public void setHumidity(String humidity){
        this.humidity = humidity;
    }
}
