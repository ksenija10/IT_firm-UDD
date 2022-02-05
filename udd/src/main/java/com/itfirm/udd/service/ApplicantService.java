package com.itfirm.udd.service;

import com.itfirm.udd.dto.JobApplicationRequest;
import com.itfirm.udd.dto.JobApplicationResponse;
import com.itfirm.udd.mapper.ApplicantMapper;
import com.itfirm.udd.model.Applicant;
import com.itfirm.udd.model.CV;
import com.itfirm.udd.model.Education;
import com.itfirm.udd.model.Letter;
import com.itfirm.udd.repository.ApplicantRepository;
import com.itfirm.udd.repository.CvRepository;
import com.itfirm.udd.repository.EducationRepository;
import com.itfirm.udd.service.elasticsearch.ApplicantIndexUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class ApplicantService {

    @Value("${cv.dir}")
    private String CV_DIR;

    @Value("${letter.dir}")
    private String LETTER_DIR;

    private static final ApplicantMapper applicantMapper = new ApplicantMapper();

    @Autowired
    private ApplicantIndexUnitService applicantIndexUnitService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private CvRepository cvRepository;

    public JobApplicationResponse save(JobApplicationRequest jobApplicationRequest) throws IOException {
        String cvFilePath = saveFile(jobApplicationRequest.getCv(), CV_DIR);
        CV cv = new CV(cvFilePath);
        String letterFilePath = saveFile(jobApplicationRequest.getLetter(), LETTER_DIR);
        Letter letter = new Letter(letterFilePath);

        Education education = educationRepository.getById(jobApplicationRequest.getEducationId());

        Applicant applicant = new Applicant(jobApplicationRequest.getName(), jobApplicationRequest.getSurname(),
                jobApplicationRequest.getEmail(), jobApplicationRequest.getAddress(), education, cv, letter);

        applicantIndexUnitService.save(applicant);

        return applicantMapper.entityToDto(applicantRepository.save(applicant));
    }

    public byte[] getCv(Long id) throws IOException {
        CV cv = cvRepository.getById(id);
        Path path = Paths.get(cv.getLocation());
        return Files.readAllBytes(path);
    }

    private String saveFile(MultipartFile file, String DATA_DIR) throws IOException {
        String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String fileName = createFileName(file.getOriginalFilename());
            Path path = Paths.get( DATA_DIR + File.separator + fileName);
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    private String createFileName(String originalFileName) {
        String cleanFileName = originalFileName.replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String[] fileNameParts = cleanFileName.split("\\.");
        String fileName = String.join("_", Arrays.copyOf(fileNameParts, fileNameParts.length-1));
        fileName = fileName + "_" + LocalDateTime.now() + ".pdf";
        return fileName;
    }
}
