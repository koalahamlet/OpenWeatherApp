package venmo.michaelhuff.sf5dayforecast.Models;

public class WeatherOverview {


    Temprature temp; // just object
    Float pressure;// 1026.24,
    Integer humidity;
    WeatherConditions[] weather; // array then object
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Temprature getTemp() {
        return temp;
    }

    public void setTemp(Temprature temp) {
        this.temp = temp;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public WeatherConditions getWeather() {
        return weather[0];
    }

    public void setWeather(WeatherConditions[] weather) {
        this.weather = weather;
    }
}
