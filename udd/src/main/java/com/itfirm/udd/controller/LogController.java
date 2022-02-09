package com.itfirm.udd.controller;

import com.itfirm.udd.dto.CityResponse;
import com.itfirm.udd.dto.LocationRequest;
import com.itfirm.udd.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/log")
public class LogController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/form-access")
    public ResponseEntity<Void> logFormAccess(@RequestBody LocationRequest locationRequest){

        CityResponse cityResponse = locationService.getCityFromCoordinates(locationRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
