package com.itfirm.udd.service;

import com.itfirm.udd.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LocationService {

    private static final String API_KEY = "pk.02029742c20635fb7bb9799a485d6ffc&";
    private static final String API_URL = "https://us1.locationiq.com/v1/search.php";

    @Autowired
    private RestTemplate restTemplate;

    public Location getLocationFromAddress(String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        ResponseEntity<Location[]> locationResponse = restTemplate.getForEntity(
                API_URL + "?key=" + API_KEY + "&q=" + encodedAddress + "&format=json", Location[].class);

        return Objects.requireNonNull(locationResponse.getBody())[0];
    }
}
