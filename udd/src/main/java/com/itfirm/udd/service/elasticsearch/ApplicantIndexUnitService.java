package com.itfirm.udd.service.elasticsearch;

import com.itfirm.udd.exceptions.GeocodeException;
import com.itfirm.udd.handler.PDFHandler;
import com.itfirm.udd.model.Applicant;
import com.itfirm.udd.model.Location;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import com.itfirm.udd.repository.elasticsearch.ApplicantIndexUnitRepository;
import com.itfirm.udd.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ApplicantIndexUnitService {

    @Autowired
    private PDFHandler pdfHandler;

    @Autowired
    private ApplicantIndexUnitRepository applicantIndexUnitRepository;

    @Async
    public ApplicantIndexUnit save(Applicant applicant, Location location) {
        String cvFilePath = applicant.getCv().getLocation();
        ApplicantIndexUnit applicantIndexUnit = pdfHandler.getIndexUnit(new File(cvFilePath));
        String[] cvFilePathParts = cvFilePath.split("\\/");
        applicantIndexUnit.setId(cvFilePathParts[cvFilePathParts.length - 1]);
        applicantIndexUnit.setName(applicant.getName());
        applicantIndexUnit.setSurname(applicant.getSurname());
        applicantIndexUnit.setEmail(applicant.getEmail());
        applicantIndexUnit.setEducationLevel(applicant.getEducation().getLevel());
        applicantIndexUnit.setEducationName(applicant.getEducation().getName());
        applicantIndexUnit.setAddress(applicant.getAddress());
        applicantIndexUnit.setCvId(applicant.getCv().getId());

        applicantIndexUnit.setLocation(new GeoPoint(location.getLat(), location.getLon()));

        return applicantIndexUnitRepository.save(applicantIndexUnit);
    }

}
