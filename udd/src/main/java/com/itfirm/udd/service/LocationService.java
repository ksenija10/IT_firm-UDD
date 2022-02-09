package com.itfirm.udd.service;

import com.itfirm.udd.dto.CityResponse;
import com.itfirm.udd.dto.LocationRequest;
import com.itfirm.udd.exceptions.GeocodeException;
import com.itfirm.udd.model.Location;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LocationService {

    private static final String API_KEY = "pk.02029742c20635fb7bb9799a485d6ffc&";
    private static final String API_URL = "https://us1.locationiq.com/v1/";
    private static final String SEARCH_URL = "search.php";
    private static final String REVERSE_URL = "reverse.php";

    @Autowired
    private RestTemplate restTemplate;

    public Location getLocationFromAddress(String address) throws GeocodeException {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        try {
            ResponseEntity<Location[]> locationResponse = restTemplate.getForEntity(
                    API_URL + SEARCH_URL + "?key=" + API_KEY + "&q=" + encodedAddress + "&format=json", Location[].class);
            return Objects.requireNonNull(locationResponse.getBody())[0];
        } catch (HttpClientErrorException e) {
            throw new GeocodeException("Unable to geocode");
        }
    }

    public CityResponse getCityFromCoordinates(LocationRequest locationRequest) {
        ResponseEntity<CityResponse> locationResponse = restTemplate.getForEntity(
                API_URL + REVERSE_URL + "?key=" + API_KEY + "&lat=" + locationRequest.lat
                        + "&lon=" + locationRequest.lon + "&format=json", CityResponse.class);

        return Objects.requireNonNull(locationResponse.getBody());
    }
}
