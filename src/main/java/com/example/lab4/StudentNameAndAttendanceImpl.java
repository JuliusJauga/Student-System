package com.example.lab4;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * Implementation of the IStudentTableView interface that creates a TableView for displaying student names and attendance.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class StudentNameAndAttendanceImpl implements IStudentTableView {

    /**
     * Creates a TableView with columns for student names and attendance.
     *
     * @param startingView The TableView to be populated with student names and attendance.
     */
    @Override
    public void createTableView(TableView<Student> startingView) {
        startingView.getColumns().clear();

        // Create column for student names
        TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Student name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Create column for attendance
        TableColumn<Student, String> attendanceColumn = new TableColumn<Student, String>("Attendance");
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceString"));

        // Customize cell factory for attendance column
        attendanceColumn.setCellFactory(tc -> {
            TableCell<Student, String> cell = new TableCell<Student, String>() {
                private ScrollPane scrollPane = new ScrollPane();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        Text text = new Text(item);
                        scrollPane.setContent(text);
                        setGraphic(scrollPane);
                    }
                }
            };
            return cell;
        });

        // Add columns to the startingView TableView
        startingView.getColumns().add(nameColumn);
        startingView.getColumns().add(attendanceColumn);

        // Set column resize policy
        startingView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
