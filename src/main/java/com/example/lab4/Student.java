package com.example.lab4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

/**
 * A class for implementing student methods. Including attended dates and other fields.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class Student extends Person {
    private String studentId;
    private boolean inGroup = false;
    private Group group = null;
    private String attendanceString;
    private ObservableList<LocalDate> attendedDates = FXCollections.observableArrayList();
    private ObservableList<LocalDate> filteredDates = FXCollections.observableArrayList();

    /**
     * Constructs a Student object with the specified name, ID, student ID, and address.
     *
     * @param name       the name of the student
     * @param id         the ID of the student
     * @param studentId  the student ID of the student
     * @param address    the address of the student
     */
    public Student(String name, String id, String studentId, String address) {
        super(name, id, address);
        this.studentId = studentId;
    }

    /**
     * Constructs a Student object with the specified name, ID, student ID, address, and attended dates.
     *
     * @param name           the name of the student
     * @param id             the ID of the student
     * @param studentId      the student ID of the student
     * @param address        the address of the student
     * @param attendedDates  the list of attended dates of the student
     */
    public Student(String name, String id, String studentId, String address, List<LocalDate> attendedDates) {
        super(name, id, address);
        this.studentId = studentId;
        this.attendedDates.setAll(attendedDates);
    }

    /**
     * Returns the student ID of the student.
     *
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID of the student.
     *
     * @param studentId the student ID to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns a string representation of the Student object.
     *
     * @return a string representation of the Student object
     */
    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName() + '\'' +
                ", id='" + getId() + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }

    /**
     * Returns the name of the student.
     *
     * @return the name of the student
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ID of the student.
     *
     * @return the ID of the student
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the student.
     *
     * @param id the ID to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the address of the student.
     *
     * @return the address of the student
     */
    @Override
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the student.
     *
     * @param address the address to set
     */
    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the list of attended dates of the student.
     *
     * @return the list of attended dates
     */
    public ObservableList<LocalDate> getAttendedDates() {
        return attendedDates;
    }

    /**
     * Sets the list of attended dates of the student.
     *
     * @param attendedDates the list of attended dates to set
     */
    public void setAttendedDates(List<LocalDate> attendedDates) {
        this.attendedDates.setAll(attendedDates);
    }

    /**
     * Adds an attendance date to the list of attended dates of the student.
     *
     * @param date the attendance date to add
     */
    public void addAttendance(LocalDate date) {
        if (date != null && !attendedDates.contains(date)) {
            attendedDates.add(date);
        }
    }

    /**
     * Returns whether the student is in a group.
     *
     * @return true if the student is in a group, false otherwise
     */
    public boolean isInGroup() {
        return inGroup;
    }

    /**
     * Sets whether the student is in a group.
     *
     * @param inGroup true if the student is in a group, false otherwise
     */
    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    /**
     * Returns the group the student is in.
     *
     * @return the group the student is in
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets the group the student is in.
     *
     * @param group the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Returns the attendance string representation of the student.
     *
     * @return the attendance string representation
     */
    public String getAttendanceString() {
        if (filteredDates.isEmpty()) {
            return attendedDates.toString();
        }
        return filteredDates.toString();
    }

    /**
     * Sets the attendance string representation of the student.
     *
     * @param attendanceString the attendance string representation to set
     */
    public void setAttendanceString(String attendanceString) {
        this.attendanceString = attendanceString;
    }

    /**
     * Sets the attended dates of the student.
     *
     * @param attendedDates the attended dates to set
     */
    public void setAttendedDates(ObservableList<LocalDate> attendedDates) {
        this.attendedDates = attendedDates;
    }

    /**
     * Filters the attended dates based on the specified start and end dates.
     *
     * @param date_start the start date of the filter range
     * @param date_end   the end date of the filter range
     */
    public void makeFilteredDates(LocalDate date_start, LocalDate date_end) {
        filteredDates.clear();
        if (date_start == null) {
            for (LocalDate date : attendedDates) {
                if (date.isBefore(date_end) || date.isEqual(date_end)) {
                    filteredDates.add(date);
                }
            }
            return;
        } else if (date_end == null) {
            for (LocalDate date : attendedDates) {
                if (date.isAfter(date_start) || date.isEqual(date_start)) {
                    filteredDates.add(date);
                }
            }
            return;
        }
        for (LocalDate date : attendedDates) {
            if (date_end.isAfter(date) && date_start.isBefore(date) || date_start.isEqual(date) || date_end.isEqual(date)) {
                filteredDates.add(date);
            }
        }
    }

    /**
     * Returns the filtered dates of the student.
     *
     * @return the filtered dates
     */
    public ObservableList<LocalDate> getFilteredDates() {
        return filteredDates;
    }

    /**
     * Sets the filtered dates of the student.
     *
     * @param filteredDates the filtered dates to set
     */
    public void setFilteredDates(ObservableList<LocalDate> filteredDates) {
        this.filteredDates = filteredDates;
    }
}
