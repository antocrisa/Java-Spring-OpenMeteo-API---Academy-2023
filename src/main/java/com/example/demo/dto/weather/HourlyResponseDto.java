package com.example.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HourlyResponseDto {

    @JsonProperty("time")
    private List<LocalDateTime> localDateTime;
    @JsonProperty("temperature_2m")
    private List<Double> temperature;
    @JsonProperty("weather_code")
    private List<String> weatherCode;




    public void convertWeatherCodesToDescriptions() {
        List<String> temp = List.copyOf(weatherCode);
        this.weatherCode.clear();

        for (String code : temp) {
            String condition = getWeatherConditionAsString(Integer.valueOf(code));
            this.weatherCode.add(condition);
        }
    }


    public String getWeatherConditionAsString(Integer weatherCode) {
        switch (weatherCode) {
            case 0:
                return "Clear sky";
            case 1:
                return "Mainly clear";
            case 2:
                return "Partly cloudy";
            case 3:
                return "Overcast";
            case 45:
                return "Fog";
            case 48:
                return "Depositing rime fog";
            case 51:
                return "Drizzle: Light";
            case 53:
                return "Drizzle: Moderate";
            case 55:
                return "Drizzle: Dense intensity";
            case 56:
                return "Freezing Drizzle: Light";
            case 57:
                return "Freezing Drizzle: Dense intensity";
            case 61:
                return "Rain: Slight";
            case 63:
                return "Rain: Moderate";
            case 65:
                return "Rain: Heavy intensity";
            case 66:
                return "Freezing Rain: Light";
            case 67:
                return "Freezing Rain: Heavy intensity";
            case 71:
                return "Snow fall: Slight";
            case 73:
                return "Snow fall: Moderate";
            case 75:
                return "Snow fall: Heavy intensity";
            case 77:
                return "Snow grains";
            case 80:
                return "Rain showers: Slight";
            case 81:
                return "Rain showers: Moderate";
            case 82:
                return "Rain showers: Violent";
            case 85:
                return "Snow showers: Slight";
            case 86:
                return "Snow showers: Heavy";
            case 95:
                return "Thunderstorm: Slight or moderate";
            case 96:
                return "Thunderstorm with hail: Slight";
            case 99:
                return "Thunderstorm with hail: Heavy";
            default:
                return "Unknown";
        }
    }


}