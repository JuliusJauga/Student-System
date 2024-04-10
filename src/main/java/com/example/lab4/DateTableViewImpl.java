package com.example.lab4;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Implementation of the {@link IDateTableView} interface that creates a table view for dates.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class DateTableViewImpl implements IDateTableView {
    /**
     * Creates a table view for dates.
     *
     * @param tableView the table view to create
     */
    @Override
    public void createTableView(TableView tableView) {
        tableView.getColumns().clear();
        TableColumn<LocalDate, LocalDate> dateColumn = new TableColumn<>("Attended dates");
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LocalDate, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<LocalDate, LocalDate> param) {
                return Bindings.createObjectBinding(() -> param.getValue());
            }
        });
        tableView.getColumns().add(dateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
