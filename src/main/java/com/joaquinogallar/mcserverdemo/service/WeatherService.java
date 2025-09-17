package com.joaquinogallar.mcserverdemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        System.out.println(weatherService.getCityCoords("Barcelona"));
    }

    public String getCityCoords(String cityName) {
        try {
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + cityName.toLowerCase() + "&language=en&format=json";
            ObjectMapper objectMapper = new ObjectMapper();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode objMapped = objectMapper.readTree(response.body());
                JsonNode results = objMapped.get("results");

                return results.get(0).get("latitude").toString()
                        + ","
                        + results.get(0).get("longitude").toString();
            } else {
                throw new RuntimeException("API call failed with status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
