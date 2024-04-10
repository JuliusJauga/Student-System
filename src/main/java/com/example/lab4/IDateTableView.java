package com.example.lab4;

import javafx.scene.control.TableView;

import java.time.LocalDate;

/**
 * The IDateTableView interface represents a view for displaying dates in a table format.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public interface IDateTableView {
    /**
     * Creates a table view for displaying dates.
     *
     * @param tableView the TableView object to be created
     */
    void createTableView(TableView<LocalDate> tableView);
}
