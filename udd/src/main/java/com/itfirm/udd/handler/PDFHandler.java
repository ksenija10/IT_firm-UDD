package com.itfirm.udd.handler;

import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PDFHandler {

    public ApplicantIndexUnit getIndexUnit(File file) {
        ApplicantIndexUnit applicantIndexUnit = new ApplicantIndexUnit();
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            String text = getText(parser);
            applicantIndexUnit.setContent(text);
        } catch (IOException e) {
            System.out.println("An error occurred when converting to PDF document");
        };

        return applicantIndexUnit;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            PDDocument doc = parser.getPDDocument();
            String text = textStripper.getText(doc);
            doc.close();
            return text;
        } catch (IOException e) {
            System.out.println("An error occurred when converting to PDF document");
        }
        return null;
    }

}
