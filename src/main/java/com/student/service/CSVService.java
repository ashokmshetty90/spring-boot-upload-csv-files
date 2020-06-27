package com.student.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.student.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.student.helper.CSVHelper;
import com.student.repository.StudentRepository;


@Service
public class CSVService {
  @Autowired
  StudentRepository repository;

  public void uploadCSVFileToDB(MultipartFile file) throws DocumentException {
    try {
      List<Student> students = CSVHelper.addStudentDetails(file.getInputStream());
      repository.saveAll(students);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() throws DocumentException, IOException, URISyntaxException {
    List<Student> students = repository.findAll();

    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("pdftable.pdf"));
    document.open();

    PdfPTable table = new PdfPTable(10);
    addTableHeader(table);
    addRows(table,students);

    document.add(table);
    document.close();

    ByteArrayInputStream in = CSVHelper.generatePdf(students);
    return in;
  }

  private void addTableHeader(PdfPTable table) {
    Stream.of("ID", "Name", "Roll Number","Sub-1","Sub-2","Sub-3","Sub-4","Sub-5","Total","Grade")
            .forEach(columnTitle -> {
              PdfPCell header = new PdfPCell();
              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
              header.setBorderWidth(2);
              header.setPhrase(new Phrase(columnTitle));
              table.addCell(header);
            });
  }

  private void addRows(PdfPTable table, List<Student> students) {
    for (Student student : students) {
      table.addCell(String.valueOf(student.getId()));
      table.addCell(String.valueOf(student.getName()));
      table.addCell(String.valueOf(student.getRollNumber()));
      table.addCell(String.valueOf(student.getSubject1()));
      table.addCell(String.valueOf(student.getSubject2()));
      table.addCell(String.valueOf(student.getSubject3()));
      table.addCell(String.valueOf(student.getSubject4()));
      table.addCell(String.valueOf(student.getSubject5()));
      table.addCell(String.valueOf(student.getTotal()));
      table.addCell(String.valueOf(student.getGrade()));

    }
  }


  public List<Student> getAllStudents() {
    return repository.findAll();
  }
}
