package com.student.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.student.entities.Student;
import com.student.service.GeneratePDFService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class CSVHelper {
    public static String TYPE = "text/csv";

    @Autowired
    GeneratePDFService generatePDFService;

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Student> addStudentDetails(InputStream is) throws DocumentException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Student> students = new ArrayList<Student>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            List<String> roll = new ArrayList<>();

            for (CSVRecord csvRecord : csvRecords) {
                Integer sub1 = 0, sub2 = 0, sub3 = 0, sub4 = 0, sub5 = 0;
                sub1 = Integer.parseInt(csvRecord.get("subject1"));
                sub2 = Integer.parseInt(csvRecord.get("subject2"));
                sub3 = Integer.parseInt(csvRecord.get("subject3"));
                sub4 = Integer.parseInt(csvRecord.get("subject4"));
                sub5 = Integer.parseInt(csvRecord.get("subject5"));
                Integer total = sub1 + sub2 + sub3 + sub4 + sub5;
                double percentage = total / 5;
                String grade = "";
                if (percentage >= 90)
                    grade = "A";
                else if (percentage >= 50 && percentage < 90)
                    grade = "B";
                else
                    grade = "C";

                Student student = new Student(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Name"),
                        csvRecord.get("Roll Number"),
                        Integer.parseInt(csvRecord.get("subject1")),
                        Integer.parseInt(csvRecord.get("subject2")),
                        Integer.parseInt(csvRecord.get("subject3")),
                        Integer.parseInt(csvRecord.get("subject4")),
                        Integer.parseInt(csvRecord.get("subject5")),
                        total,
                        grade
                );


                if(!roll.contains(student.getRollNumber())) {
                    students.add(student);
                    String pdfName = csvRecord.get("Name");
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("generatedPdf/" + pdfName + ".pdf"));
                    document.open();

                    PdfPTable table = new PdfPTable(10);
                    GeneratePDFService.addTableHeader(table);
                    GeneratePDFService.addRows(table, student);

                    document.add(table);
                    document.close();
                } else {
                    log.debug("Student details already added");
                }

                roll.add(student.getRollNumber());
            }

            return students;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream generatePdf(List<Student> students) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Student student : students) {
                List<String> data = Arrays.asList(
                        String.valueOf(student.getId()),
                        student.getName(),
                        student.getRollNumber(),
                        String.valueOf(student.getSubject1()),
                        String.valueOf(student.getSubject2()),
                        String.valueOf(student.getSubject3()),
                        String.valueOf(student.getSubject4()),
                        String.valueOf(student.getSubject5()),
                        String.valueOf(student.getTotal()),
                        student.getGrade()

                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
