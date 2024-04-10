package com.example.lab4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for modifying a group of students.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class GroupModifyController implements Initializable {
    Controller parent;
    @FXML
    private TableView<Student> free_students;

    @FXML
    private TableView<Student> group_students;

    ObservableList<Student> freeStudents;
    Group group;

    /**
     * Constructor for GroupModifyController.
     *
     * @param parent       The parent controller.
     * @param group        The group of students to modify.
     * @param freeStudents The list of free students.
     */
    GroupModifyController(Controller parent, Group group, ObservableList<Student> freeStudents) {
        this.group = group;
        this.freeStudents = freeStudents;
        this.parent = parent;
        for (Student student : group.getStudents()) {
            System.out.println(student.toString());
        }
    }

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IStudentTableView newView = new StudentNameAndIdImpl();
        newView.createTableView(free_students);
        free_students.setItems(freeStudents);
        newView.createTableView(group_students);
        group_students.setItems(group.getStudents());

        free_students.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                Student selectedPerson = free_students.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    freeStudents.remove(selectedPerson);
                    group.add(selectedPerson);
                    free_students.refresh();
                    group_students.refresh();
                }
            }
        });
        group_students.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                Student selectedPerson = group_students.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    freeStudents.add(selectedPerson);
                    group.remove(selectedPerson);
                    free_students.refresh();
                    group_students.refresh();
                }
            }
        });
    }

    /**
     * Event handler for the set button click.
     *
     * @param event The action event.
     */
    @FXML
    void set_button_click(ActionEvent event) {
        parent.setData();
    }
}
