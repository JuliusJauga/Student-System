package com.example.lab4;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The FileHandler class is responsible for handling file operations such as reading and writing data to CSV and Excel files.
 * It provides methods to write data from a list of groups and students to a CSV file, and to read data from a CSV file and populate the list of groups and students.
 * It also provides methods to write data from a list of groups and students to an Excel file, and to read data from an Excel file and populate the list of groups and students.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class FileHandler {
    List<Group> groups = new ArrayList<Group>();
    ObservableList<Student> allStudents = FXCollections.observableArrayList();
    ObservableList<Student> freeStudents = FXCollections.observableArrayList();
    /**
     * Constructs a new FileHandler object.
     *
     * @param groups        the list of groups
     * @param allStudents   the observable list of all students
     * @param freeStudents  the observable list of free students
     */
    FileHandler(List<Group> groups, ObservableList<Student> allStudents, ObservableList<Student> freeStudents) {
        this.groups = groups;
        this.allStudents = allStudents;
        this.freeStudents = freeStudents;
    }
    /**
     * Writes the student data to a CSV file.
     *
     * @throws IOException if an I/O error occurs while writing the file.
     */
    void WriteCSV() throws IOException {
        String csvFilePath = "students.csv";
        String[] header = {"Name", "Address", "ID", "Student ID", "Group", "Attended Dates"};
        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
        writer.writeNext(header);

        // Writing data for each student
        int i = 1;
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                List<LocalDate> attendedDates = student.getAttendedDates();
                String attendedDatesString = attendedDates.toString();
                String[] rowData = {
                        student.getName(),
                        student.getAddress(),
                        student.getId(),
                        student.getStudentId(),
                        "" + i,
                        student.getAttendedDates().toString()
                };
                writer.writeNext(rowData);
            }
            i++;
        }
        i = 0;
        for (Student student : freeStudents) {
            List<LocalDate> attendedDates = student.getAttendedDates();
            String attendedDatesString = attendedDates.toString();
            String[] rowData = {
                    student.getName(),
                    student.getAddress(),
                    student.getId(),
                    student.getStudentId(),
                    "" + i,
                    student.getAttendedDates().toString()
            };
            writer.writeNext(rowData);
        }
        writer.close();
    }
    /**
     * Reads data from a CSV file and populates the student records.
     * 
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public void readCSV() throws IOException {
        String csvFilePath = "students.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            allStudents.clear();
            groups.clear();
            freeStudents.clear();
            String[] header = reader.readNext();

            String[] rowData;
            while ((rowData = reader.readNext()) != null) {
                String name = rowData[0];
                String address = rowData[1];
                String id = rowData[2];
                String studentId = rowData[3];
                int groupIndex = Integer.parseInt(rowData[4]);
                String attendedDatesString = rowData[5];
                List<LocalDate> attendedDates = parseAttendedDates(attendedDatesString);

                if (groupIndex > 0) {
                    // Student belongs to a group
                    while (groups.size() <= groupIndex) {
                        groups.add(new Group(groups.size()));
                    }
                    Student student = new Student(name, id, studentId, address);
                    student.setAttendedDates(attendedDates);
                    groups.get(groupIndex).add(student);
                    allStudents.add(student);
                } else {
                    // Free student
                    Student student = new Student(name, id, studentId, address);
                    student.setAttendedDates(attendedDates);
                    freeStudents.add(student);
                    allStudents.add(student);
                }
            }
            if (!groups.isEmpty()) groups.remove(0);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Parses a string of attended dates and returns a list of LocalDate objects.
     *
     * @param attendedDatesString the string containing the attended dates
     * @return a list of LocalDate objects representing the attended dates
     */
    private List<LocalDate> parseAttendedDates(String attendedDatesString) {
        attendedDatesString = attendedDatesString.substring(1, attendedDatesString.length() - 1);
        String[] dateStrings = attendedDatesString.split(",");
        List<LocalDate> attendedDates = new ArrayList<>();
        for (String dateString : dateStrings) {
            if (!dateString.trim().isEmpty()) {
                LocalDate date = LocalDate.parse(dateString.trim());
                attendedDates.add(date);
            }
        }
        return attendedDates;
    }
    /**
     * Writes student data to an Excel file.
     *
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void writeExcel() throws IOException {
        String excelFilePath = "students.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");
        // Writing header row
        Row headerRow = sheet.createRow(0);
        String[] header = {"Name", "Address", "ID", "Student ID", "Group", "Attended Dates"};
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }
        int rowNum = 1;
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            for (Student student : group.getStudents()) {
                Row row = sheet.createRow(rowNum++);
                writeStudentData(student, i+1, row);
            }
        }
        for (Student student : freeStudents) {
            Row row = sheet.createRow(rowNum++);
            writeStudentData(student, -1, row);
        }
        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        outputStream.close();
    }
    /**
     * Writes the student data to the specified row in the Excel file.
     *
     * @param student     The student object containing the data to be written.
     * @param groupIndex  The index of the group the student belongs to.
     * @param row         The row in the Excel file where the data will be written.
     */
    private static void writeStudentData(Student student, int groupIndex, Row row) {
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(student.getName());

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(student.getAddress());

        Cell cell2 = row.createCell(2);
        cell2.setCellValue(student.getId());

        Cell cell3 = row.createCell(3);
        cell3.setCellValue(student.getStudentId());

        Cell cell4 = row.createCell(4);
        if (groupIndex >= 0) {
            cell4.setCellValue(groupIndex);
        } else {
            cell4.setCellValue(0);
        }

        Cell cell5 = row.createCell(5);
        cell5.setCellValue(student.getAttendedDates().toString());
    }
    /**
     * Reads data from an Excel file and populates the student records.
     *
     * @throws IOException if an I/O error occurs while reading the Excel file.
     */
    public void readExcel() throws IOException {
        String excelFilePath = "students.xlsx";

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            allStudents.clear();
            groups.clear();
            freeStudents.clear();
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                String name = cellIterator.next().getStringCellValue();
                String address = cellIterator.next().getStringCellValue();
                String id = cellIterator.next().getStringCellValue();
                String studentId = cellIterator.next().getStringCellValue();
                int groupIndex = (int) cellIterator.next().getNumericCellValue(); // Assuming group index is numeric
                String attendedDatesString = cellIterator.next().getStringCellValue();
                List<LocalDate> attendedDates = parseAttendedDates(attendedDatesString);

                if (groupIndex > 0) {
                    // Student belongs to a group
                    while (groups.size() <= groupIndex) {
                        groups.add(new Group(groups.size()));
                    }
                    Student student = new Student(name, id, studentId, address);
                    student.setAttendedDates(attendedDates);
                    groups.get(groupIndex).add(student);
                    allStudents.add(student);
                } else {
                    // Free student
                    Student student = new Student(name, id, studentId, address);
                    student.setAttendedDates(attendedDates);
                    allStudents.add(student);
                    freeStudents.add(student);
                }
            }
            if (!groups.isEmpty()) groups.remove(0);
        }
    }
}
