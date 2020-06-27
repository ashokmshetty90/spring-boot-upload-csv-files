package com.student.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.student.entities.Student;

import java.util.List;
import java.util.stream.Stream;

public class GeneratePDFService {

    public static void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Roll Number","Sub-1","Sub-2","Sub-3","Sub-4","Sub-5","Total","Grade")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public static void addRows(PdfPTable table, Student student) {
        //for (Student student : students) {
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

        //}
    }

}
