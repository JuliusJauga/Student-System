package com.example.lab4;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Implementation of the IStudentTableView interface that creates a TableView for displaying student information.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class StudentFullTableImpl implements IStudentTableView {

    /**
     * Creates a TableView with columns for student name, ID, student ID, and address.
     *
     * @param startingView The TableView to be populated with student information.
     */
    @Override
    public void createTableView(TableView<Student> startingView) {
        startingView.getColumns().clear();
        
        // Create columns and set cell value factories
        TableColumn<Student, String> nameColumn = new TableColumn<>("Student name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Student, String> studentIdColumn = new TableColumn<>("Student ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        
        TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        // Add columns to the TableView
        startingView.getColumns().add(nameColumn);
        startingView.getColumns().add(idColumn);
        startingView.getColumns().add(studentIdColumn);
        startingView.getColumns().add(addressColumn);
        
        // Set column resize policy
        startingView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
