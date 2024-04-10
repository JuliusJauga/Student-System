package com.example.lab4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a group of students.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class Group {
    String name;

    /**
     * Constructs a new Group object with the given index.
     * @param index The index of the group.
     */
    public Group(int index) {
        name = "Group " + (index + 1);
    }

    /**
     * Sets a new name for the group based on the given index.
     * @param index The index of the group.
     */
    public void newName(int index) {
        name = "Group " + (index + 1);
    }

    ObservableList<Student> students = FXCollections.observableArrayList();

    /**
     * Returns the list of students in the group.
     * @return The list of students.
     */
    public ObservableList<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students in the group.
     * @param students The list of students.
     */
    public void setStudents(ObservableList<Student> students) {
        for (Student student : students) {
            if (student.isInGroup()) {
                student.getGroup().remove(student);
            }
            student.setInGroup(true);
            student.setGroup(this);
        }
        this.students = students;
    }

    /**
     * Adds a student to the group.
     * @param student The student to add.
     */
    public void add(Student student) {
        if (!students.contains(student)) {
            if (student.isInGroup()) {
                student.getGroup().remove(student);
            }
            students.add(student);
            student.setGroup(this);
            student.setInGroup(true);
        }
    }

    /**
     * Adds a student to the group at the specified index.
     * @param index The index at which to add the student.
     * @param student The student to add.
     */
    public void add(int index, Student student) {
        if (!students.contains(student)) {
            if (student.isInGroup()) {
                student.getGroup().remove(student);
            }
            students.add(index, student);
            student.setGroup(this);
            student.setInGroup(true);
        }
    }

    /**
     * Removes the student at the specified index from the group.
     * @param index The index of the student to remove.
     */
    public void remove(int index) {
        if (index < students.size()) {
            students.get(index).setInGroup(false);
            students.remove(index);
        }
    }

    /**
     * Removes the specified student from the group.
     * @param student The student to remove.
     */
    public void remove(Student student) {
        if (students.contains(student)) {
            students.remove(student);
            student.setInGroup(false);
        }
    }

    /**
     * Returns the number of students in the group.
     * @return The number of students.
     */
    public int size() {
        return students.size();
    }

    /**
     * Returns the name of the group.
     * @return The name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the group.
     * @param name The new name for the group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Clears the group by removing all students.
     */
    public void clear() {
        for (Student student : students) {
            student.setInGroup(false);
            student.setGroup(null);
        }
        students.clear();
    }
}
