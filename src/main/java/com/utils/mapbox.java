package com.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class mapbox {

    @Value("${mapbox.access.token}")
    private String mapboxAccessToken;

    private RestTemplate restTemplate = new RestTemplate();

    public mapbox() {
        this.restTemplate = restTemplate;
    }

    public List<Double> getCoordinates(String location) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://api.mapbox.com/geocoding/v5/mapbox.places/{location}.json")
                .queryParam("access_token", "pk.eyJ1IjoiYWtzaGlzaDIwMDIiLCJhIjoiY20ydmV6NDFnMGEzcDJqczRraHdtcnQ1MyJ9.rhOsJDioA5UpIF_uXEF_Gg")
                .queryParam("limit", 1)
                .encode()
                .toUriString();

        URI uri = UriComponentsBuilder.fromUriString(urlTemplate)
                .buildAndExpand(location)
                .toUri();

        MapboxResponse response = restTemplate.getForObject(uri, MapboxResponse.class);

        if (response != null && !response.getFeatures().isEmpty()) {
            MapboxResponse.Feature feature = response.getFeatures().get(0);
            return feature.getGeometry().getCoordinates();
        } else {
            throw new RuntimeException("Coordinates not found");
        }
    }

    // MapboxResponse classes

    public static class MapboxResponse {
        private List<Feature> features;

        public List<Feature> getFeatures() {
            return features;
        }

        public void setFeatures(List<Feature> features) {
            this.features = features;
        }

        public static class Feature {
            private Geometry geometry;

            public Geometry getGeometry() {
                return geometry;
            }

            public void setGeometry(Geometry geometry) {
                this.geometry = geometry;
            }

            public static class Geometry {
                private List<Double> coordinates;

                public List<Double> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<Double> coordinates) {
                    this.coordinates = coordinates;
                }
            }
        }
    }
}
