package com.example.lab4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents PDF data for a specific date.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class PDF_Data {
    private LocalDate date;
    private int integerValue;
    private List<Student> studentsOfDate = new ArrayList<>();

    /**
     * Constructs a PDF_Data object with the given date.
     *
     * @param date the date associated with the PDF data
     */
    public PDF_Data(LocalDate date) {
        this.date = date;
        this.integerValue = 0;
    }

    /**
     * Returns the date associated with the PDF data as a string.
     *
     * @return the date as a string
     */
    public String getDateAsString() {
        return date.toString();
    }

    /**
     * Returns the integer value associated with the PDF data.
     *
     * @return the integer value
     */
    public int getIntegerValue() {
        return integerValue;
    }

    /**
     * Adds a student to the PDF data if the student attended the date and is not already added.
     * Updates the integer value accordingly.
     *
     * @param student the student to add
     */
    public void Add(Student student) {
        if (student.getAttendedDates().contains(date) && !studentsOfDate.contains(student)) {
            studentsOfDate.add(student);
            integerValue++;
        }
    }

    /**
     * Returns the date associated with the PDF data.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date associated with the PDF data.
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the integer value associated with the PDF data.
     *
     * @param integerValue the integer value to set
     */
    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    /**
     * Returns the list of students associated with the PDF data.
     *
     * @return the list of students
     */
    public List<Student> getStudentsOfDate() {
        return studentsOfDate;
    }

    /**
     * Sets the list of students associated with the PDF data.
     *
     * @param studentsOfDate the list of students to set
     */
    public void setStudentsOfDate(List<Student> studentsOfDate) {
        this.studentsOfDate = studentsOfDate;
    }
}
