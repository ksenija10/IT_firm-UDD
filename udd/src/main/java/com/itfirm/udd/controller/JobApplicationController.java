package com.itfirm.udd.controller;

import com.itfirm.udd.dto.JobApplicationRequest;
import com.itfirm.udd.dto.JobApplicationResponse;
import com.itfirm.udd.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/job-application")
public class JobApplicationController {

    @Autowired
    private ApplicantService applicantService;

    @PostMapping()
    public ResponseEntity<JobApplicationResponse> applyForJob(@ModelAttribute @Valid JobApplicationRequest jobApplicationRequest) throws IOException {

        return new ResponseEntity<>(applicantService.save(jobApplicationRequest), HttpStatus.OK);
    }

    @GetMapping("/cv/{cvId}")
    public ResponseEntity<byte[]> getCv(@PathVariable Long cvId) throws IOException {
        byte[] cvBytes = applicantService.getCv(cvId);
        return new ResponseEntity<>(cvBytes, HttpStatus.OK);
    }
}
