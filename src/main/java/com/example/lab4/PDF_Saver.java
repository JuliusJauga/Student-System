
package com.example.lab4;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Generates a PDF document based on the provided list of PDF_Data objects and saves it to the specified file path.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class PDF_Saver {
    /**
     * Method for generating a PDF.
     * @param list     The list of PDF_Data objects containing student information.
     * @param filePath The file path where the generated PDF document will be saved.
     * @throws IOException       If an I/O error occurs while writing the PDF document.
     * @throws DocumentException If an error occurs during the creation of the PDF document.
     */
    public static void generatePDF(ObservableList<PDF_Data> list, String filePath) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        for (PDF_Data pdfData : list) {
            document.add(new Paragraph("Student list of - " + pdfData.getDateAsString()));
            PdfPTable table = new PdfPTable(4);
            if (pdfData.getStudentsOfDate().isEmpty()) {
                document.add(new Paragraph("No students attended date\n\n"));
                continue;
            }
            document.add(new Paragraph("\n"));
            PdfPCell headerCell1 = new PdfPCell(new Paragraph("Name"));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell1);
            PdfPCell headerCell2 = new PdfPCell(new Paragraph("Address"));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell2);
            PdfPCell headerCell3 = new PdfPCell(new Paragraph("ID"));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell3);
            PdfPCell headerCell4 = new PdfPCell(new Paragraph("Student ID"));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell4);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            for (int i = 0; i < pdfData.getStudentsOfDate().size(); i++) {
                PdfPCell cell1 = new PdfPCell(new Paragraph(pdfData.getStudentsOfDate().get(i).getName()));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Paragraph(pdfData.getStudentsOfDate().get(i).getAddress()));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);
                PdfPCell cell3 = new PdfPCell(new Paragraph(pdfData.getStudentsOfDate().get(i).getId()));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell3);
                PdfPCell cell4 = new PdfPCell(new Paragraph(pdfData.getStudentsOfDate().get(i).getStudentId()));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell4);
            }
            document.add(table);
        }
        document.close();
    }
}
