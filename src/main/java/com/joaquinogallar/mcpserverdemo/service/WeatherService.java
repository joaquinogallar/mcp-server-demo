package com.joaquinogallar.mcpserverdemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private final HttpClient client;

    public WeatherService(HttpClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService(HttpClient.newHttpClient());
        System.out.println(weatherService.getCityWeather("Buenos Aires"));
    }

    @Tool(
            name = "get_city_coordinates",
            description = "Get the latitude and longitude coordinates for a given city name"
    )
    public String[] getCityCoords(String cityName) {
        try {
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + cityName.toLowerCase().replace(" ", "%20") + "&language=en&format=json";
            ObjectMapper objectMapper = new ObjectMapper();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode objMapped = objectMapper.readTree(response.body());
                JsonNode results = objMapped.get("results");

                return new String[]{
                        results.get(0).get("latitude").toString(),
                        results.get(0).get("longitude").toString()
                };
            } else {
                throw new RuntimeException("API call failed with status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Tool(
            name = "get_city_weather",
            description = "Get current weather conditions and hourly forecast for a city including temperature, humidity, and wind speed"
    )
    public String getCityWeather(String cityName) {
        try {
            String[] cityCoords = getCityCoords(cityName);
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + cityCoords[0] + "&longitude=" + cityCoords[1] + "&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("API call failed with status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
