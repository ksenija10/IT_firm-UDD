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
import java.util.logging.Logger;

import javax.validation.Valid;

@RestController
@RequestMapping("api/log")
public class LogController {

    @Autowired
    private LocationService locationService;

    private final static Logger logger = Logger.getLogger("LogController.class");

    @PostMapping("/form-access")
    public ResponseEntity<Void> logFormAccess(@Valid @RequestBody LocationRequest locationRequest){

        CityResponse cityResponse = locationService.getCityFromCoordinates(locationRequest);

        logger.info("Job application form accessed from city=" + cityResponse.getAddress().getCity());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
