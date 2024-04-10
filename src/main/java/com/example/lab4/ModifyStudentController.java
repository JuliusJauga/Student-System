package com.example.lab4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for modifying a student's information.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class ModifyStudentController implements Initializable {
    Controller parent;
    @FXML
    private Label attendance_label;
    @FXML
    private Button set_button;
    @FXML
    DatePicker date_picker;
    @FXML
    private TableView<LocalDate> attendance_dates_table;
    private boolean modifiable;
    ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
    @FXML
    private TextField stud_address;

    @FXML
    private TextField id;

    @FXML
    private TextField stud_id;

    @FXML
    private TextField stud_name;
    @FXML
    private ObservableList<Student> studentList;
    private Student CurrentStudent;

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDateTableView newDateView = new DateTableViewImpl();
        newDateView.createTableView(attendance_dates_table);
        attendance_dates_table.setItems(selectedDates);
        attendance_dates_table.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                LocalDate selectedDate = attendance_dates_table.getSelectionModel().getSelectedItem();
                if (selectedDate != null) {
                    if (modifiable) selectedDates.remove(selectedDate);
                }
            }
        });
        date_picker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (selectedDates.contains(date)) {
                    setStyle("-fx-background-color: #add8e6; -fx-text-fill: black;"); // Set background color to blue for selected dates
                }
            }
        });
        date_picker.setOnAction(event -> {
            LocalDate date = date_picker.getValue();
            if (date != null) {
                if (selectedDates.contains(date)) {
                    selectedDates.remove(date);
                    date_picker.setValue(null);
                } else {
                    insert(date);
                }
                attendance_dates_table.refresh();
            }
        });
        if (!modifiable) {
            set_button.setDisable(true);
            set_button.setVisible(false);
            attendance_label.setVisible(false);
            date_picker.setVisible(false);
            date_picker.setDisable(true);
            stud_name.setDisable(true);
            stud_address.setDisable(true);
            stud_id.setDisable(true);
            id.setDisable(true);
        }
    }

    /**
     * Constructs a new ModifyStudentController.
     *
     * @param parent     The parent controller.
     * @param modifiable Indicates whether the student's information is modifiable.
     */
    public ModifyStudentController(Controller parent, boolean modifiable) {
        this.modifiable = modifiable;
        this.parent = parent;
    }

    /**
     * Sets the data for the controller.
     *
     * @param student      The student object to display.
     * @param studentList  The list of students.
     */
    void setData(Student student, ObservableList<Student> studentList) {
        stud_id.setText(student.getStudentId());
        stud_name.setText(student.getName());
        id.setText(student.getId());
        stud_address.setText(student.getAddress());
        CurrentStudent = new Student(student.getName(), student.getId(), student.getStudentId(), student.getAddress());
        CurrentStudent.setAttendedDates(student.getAttendedDates());
        selectedDates.clear();
        selectedDates.setAll(student.getAttendedDates());
        this.studentList = studentList;
    }

    /**
     * Handles the click event of the set button.
     *
     * @param event The action event.
     */
    @FXML
    void set_button_click(ActionEvent event) {
        for (LocalDate date : selectedDates) {
            System.out.println(date);
        }
        if (Objects.equals(stud_name.getText(), "") || (Objects.equals(stud_name.getText(), "") || Objects.equals(stud_name.getText(), "") || Objects.equals(stud_name.getText(), ""))) {
            return;
        }
        for (Student student : studentList) {
            if (!Objects.equals(CurrentStudent.getId(), student.getId()) && !Objects.equals(CurrentStudent.id, student.getId())) {
                if (Objects.equals(id.getText(), student.getId()) || Objects.equals(stud_id.getText(), student.getStudentId())) {
                    return;
                }
            }
        }
        Student modified = new Student("","","","");
        for (int i = 0; i < studentList.size(); i++) {
            if (Objects.equals(CurrentStudent.getId(), studentList.get(i).getId())) {
                studentList.get(i).setName(stud_name.getText());
                studentList.get(i).setAddress(stud_address.getText());
                studentList.get(i).setStudentId(stud_id.getText());
                studentList.get(i).setId(id.getText());
                studentList.get(i).setAttendedDates(selectedDates);
                modified = new Student(stud_name.getText(), id.getText(), stud_id.getText(), stud_address.getText());
            }
        }
        parent.setData();
        CurrentStudent = modified;
    }

    /**
     * Inserts a date into the selectedDates list in ascending order.
     *
     * @param toInsert The date to insert.
     */
    void insert(LocalDate toInsert) {
        int index = 0;
        for (LocalDate date : selectedDates) {
            if (date.isBefore(toInsert)) {
                index++;
            }
        }
        selectedDates.add(index, toInsert);
    }
}
