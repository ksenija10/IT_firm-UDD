package com.itfirm.udd.controller;

import com.itfirm.udd.dto.GeoLocationRequest;
import com.itfirm.udd.dto.ReportResponse;
import com.itfirm.udd.dto.SearchFormRequest;
import com.itfirm.udd.dto.SearchPageResponse;
import com.itfirm.udd.exceptions.GeocodeException;
import com.itfirm.udd.service.ReportService;
import com.itfirm.udd.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/time")
    public ResponseEntity<List<ReportResponse>> getReportByTime() throws IOException {
        List<ReportResponse> reportResponse = reportService.getByTime();

        return new ResponseEntity<>(reportResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/city")
    public ResponseEntity<List<ReportResponse>> getReportByCity() throws IOException {
        List<ReportResponse> reportResponse = reportService.getByCity();

        return new ResponseEntity<>(reportResponse, HttpStatus.OK);
    }

}
