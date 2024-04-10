package com.example.lab4;

import javafx.scene.control.TableView;

/**
 * This interface represents a view for displaying student data in a table format.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public interface IStudentTableView {
    /**
     * Creates a table view for displaying student data.
     *
     * @param startingView the starting view of the table.
     */
    void createTableView(TableView<Student> startingView);
}
