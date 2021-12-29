package com.itfirm.udd.service.elasticsearch;

import com.itfirm.udd.handler.PDFHandler;
import com.itfirm.udd.model.Applicant;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import com.itfirm.udd.repository.elasticsearch.ApplicantIndexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ApplicantIndexUnitService {

    @Autowired
    private PDFHandler pdfHandler;

    @Autowired
    private ApplicantIndexUnitRepository applicantIndexUnitRepository;

    public ApplicantIndexUnit save(Applicant applicant) {
        String cvFilePath = applicant.getCv().getLocation();
        ApplicantIndexUnit applicantIndexUnit = pdfHandler.getIndexUnit(new File(cvFilePath));
        String[] cvFilePathParts = cvFilePath.split("\\/");
        applicantIndexUnit.setId(cvFilePathParts[cvFilePathParts.length - 1]);
        applicantIndexUnit.setName(applicant.getName());
        applicantIndexUnit.setSurname(applicant.getSurname());
        applicantIndexUnit.setEducationLevel(applicant.getEducation().getLevel());

        return applicantIndexUnitRepository.save(applicantIndexUnit);
    }

}
