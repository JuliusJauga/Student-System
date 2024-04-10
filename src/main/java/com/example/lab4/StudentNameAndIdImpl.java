package com.example.lab4;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Implementation of the IStudentTableView interface for creating a table view
 * with student names and IDs.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class StudentNameAndIdImpl implements IStudentTableView {
    /**
     * Creates a table view with student names and IDs.
     *
     * @param startingView The starting TableView to create the view on.
     */
    @Override
    public void createTableView(TableView<Student> startingView) {
        startingView.getColumns().clear();
        TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Student name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> idColumn = new TableColumn<Student, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        startingView.getColumns().add(nameColumn);
        startingView.getColumns().add(idColumn);
        startingView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
