package com.itfirm.udd.mapper;

import com.itfirm.udd.dto.JobApplicationResponse;
import com.itfirm.udd.model.Applicant;

public class ApplicantMapper {

    public JobApplicationResponse entityToDto(Applicant applicant) {
        return new JobApplicationResponse(applicant.getId(), applicant.getName(), applicant.getSurname(),
                applicant.getEmail(), applicant.getAddress(), applicant.getEducation().getName(), applicant.getCv().getLocation());
    }
}
