package com.example.lab4;

import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * The Controller class implements the Initializable interface and serves as the controller for the application.
 * It handles the logic and event handling for the user interface components.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class Controller implements Initializable {

    int selectedGroup = -2;
    private ObservableList<Student> filterItems = FXCollections.observableArrayList();
    @FXML
    private DatePicker filter_date_picker;
    private LocalDate filter_date_start;
    private LocalDate filter_date_end;
    @FXML
    private TableView<LocalDate> date_view;
    @FXML
    private Label filter_end;

    @FXML
    private Label filter_start;
    private List<LocalDate> selectedFilterDates = new ArrayList<>();
    @FXML
    private TableView<PDF_Data> PDF_view;
    private ObservableList<PDF_Data> PDF_List = FXCollections.observableArrayList();
    @FXML
    private DatePicker PDF_date_picker;

    @FXML
    private Label PDF_interval_end;

    @FXML
    private Label PDF_interval_start;
    private LocalDate PDF_start;
    private LocalDate PDF_end;
    private List<LocalDate> selectedDates = new ArrayList<>();
    @FXML
    private TableView<Student> free_students_table;
    List<Group> groupList = new ArrayList<>();

    @FXML
    private TreeTableView<Student> groups_table;
    @FXML
    private TreeTableView<Student> groups_table1;

    @FXML
    private TextField groups_desired;

    @FXML
    private TextField stud_address;

    @FXML
    private TextField id;

    @FXML
    private TextField stud_id;

    @FXML
    private TextField stud_name;
    private int groupToDelete = 0;
    private boolean allowDeletion = false;
    ObservableList<Student> studentList = FXCollections.observableArrayList();
    ObservableList<Student> freeStudents = FXCollections.observableArrayList();
    @FXML
    private TableView<Student> list_view;
    @FXML
    private TableView<Student> list_view1;
    private Student selectedStudent;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the JavaFX framework after the FXML file has been loaded.
     * Creates the table views and sets the data for the table views. Using interfaces. Also sets the date pickers. Also sets the text fields to take only numbers.
     * Handles on action events.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PDF_view.getColumns().clear();
        TableColumn<PDF_Data, String> dateColumn = new TableColumn<>("Date");
        TableColumn<PDF_Data, Integer> integerColumn = new TableColumn<>("Attended Students");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAsString"));
        integerColumn.setCellValueFactory(new PropertyValueFactory<>("integerValue"));
        PDF_view.getColumns().addAll(dateColumn, integerColumn);
        PDF_view.setItems(PDF_List);
        PDF_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        IGroupTableView fullGroupView = new GroupTableViewFullImpl();
        IGroupTableView groupOnlyView = new GroupOnlyTableViewImpl();
        fullGroupView.createGroupView(groups_table);
        groupOnlyView.createGroupView(groups_table1);

        IStudentTableView fullTableView = new StudentFullTableImpl();
        fullTableView.createTableView(list_view);
        IStudentTableView FreeTableView = new StudentNameAndIdImpl();
        FreeTableView.createTableView(free_students_table);
        IStudentTableView AttendanceTableView = new StudentNameAndAttendanceImpl();
        AttendanceTableView.createTableView(list_view1);

        IDateTableView newDateView = new DateTableViewImpl();
        newDateView.createTableView(date_view);

        setNumeric(stud_id);
        setNumeric(id);
        setNumeric(groups_desired);

        list_view.setItems(studentList);
        free_students_table.setItems(freeStudents);
        list_view1.setItems(filterItems);
        list_view.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                Student selectedPerson = list_view.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    try {
                        openAnotherWindow(event, selectedPerson, true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() >= 2) {
                Student selectedPerson = list_view.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    if (selectedPerson.isInGroup()) {
                        selectedPerson.getGroup().remove(selectedPerson);
                    }
                    else {
                        freeStudents.remove(selectedPerson);
                    }
                    studentList.remove(selectedPerson);
                    setData();
                }
            }
        });
        list_view1.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                Student selectedPerson = list_view1.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    selectedStudent = selectedPerson;
                    selectedGroup = -3;
                    date_view.setVisible(true);
                    UpdateFilterList();
                    setData();
                }
            }
        });
        groups_table.setOnMouseClicked(event -> {
            TreeItem<Student> selectedItem = groups_table.getSelectionModel().getSelectedItem();
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                if (selectedItem != null) {
                    Student selectedPerson = selectedItem.getValue();
                    if (selectedPerson != null) {
                        groupToDelete = 0;
                        for (TreeItem<Student> group : groups_table.getRoot().getChildren()) {
                            if (group.getValue().equals(selectedPerson)) {
                                allowDeletion = true;
                                break;
                            }
                            groupToDelete++;
                        }
                    }
                }
            }
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                allowDeletion = false;
                if (selectedItem != null) {
                    Student selectedPerson = selectedItem.getValue();
                    if (selectedPerson != null) {
                        try {
                            int i = 0;
                            for (TreeItem<Student> group : groups_table.getRoot().getChildren()) {
                                if (group.getValue().equals(selectedPerson)) {
                                    openGroupWindow(event, groupList.get(i));
                                    return;
                                }
                                i++;
                            }
                            i = 0;
                            for (TreeItem<Student> group : groups_table.getRoot().getChildren()) {
                                for (TreeItem<Student> student : group.getChildren()) {
                                    if (student.getValue().equals(selectedPerson)) {
                                        openAnotherWindow(event, selectedPerson, false);
                                    }
                                    i++;
                                }
                                i = 0;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        PDF_date_picker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (selectedDates.contains(date)) {
                    setStyle("-fx-background-color: #add8e6; -fx-text-fill: black;");
                }
            }
        });
        filter_date_picker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (selectedFilterDates.contains(date)) {
                    setStyle("-fx-background-color: #add8e6; -fx-text-fill: black;");
                }
            }
        });
        filter_date_picker.setOnAction(event -> {
            LocalDate date = filter_date_picker.getValue();
            if (date != null) {
                if (filter_date_start != null && filter_date_end != null) {
                    if (date.isEqual(filter_date_start)) {
                        filter_date_start = null;
                        filter_start.setText("");
                        selectedFilterDates.remove(date);
                    }
                    else if (date.isEqual(filter_date_end)) {
                        filter_date_end = null;
                        filter_end.setText("");
                        selectedFilterDates.remove(date);
                    }
                    else if (date.isBefore(filter_date_start)) {
                        selectedFilterDates.add(date);
                        selectedFilterDates.remove(filter_date_start);
                        filter_date_start = date;
                        filter_start.setText(filter_date_start.toString());
                    }
                    else if (date.isAfter(filter_date_end)) {
                        selectedFilterDates.add(date);
                        selectedFilterDates.remove(filter_date_end);
                        filter_date_end = date;
                        filter_end.setText(filter_date_end.toString());
                    }
                    else if (date.isAfter(filter_date_start) && date.isBefore(filter_date_end)) {
                        selectedFilterDates.add(date);
                        selectedFilterDates.remove(filter_date_end);
                        filter_date_end = date;
                        filter_end.setText(filter_date_end.toString());
                    }
                }
                else if (filter_date_start == null && filter_date_end != null) {
                    if (date.isEqual(filter_date_end)) {
                        filter_date_end = null;
                        filter_end.setText("");
                        selectedFilterDates.remove(date);
                    }
                    else if (date.isBefore(filter_date_end)) {
                        selectedFilterDates.add(date);
                        filter_date_start = date;
                        filter_start.setText(filter_date_start.toString());
                    }
                    else if (date.isAfter(filter_date_end)) {
                        selectedFilterDates.add(date);
                        selectedFilterDates.remove(filter_date_end);
                        filter_date_end = date;
                        filter_end.setText(filter_date_end.toString());
                    }
                }
                else if (filter_date_start != null && filter_date_end == null) {
                    if (date.isEqual(filter_date_start)) {
                        filter_date_start = null;
                        filter_start.setText("");
                        selectedFilterDates.remove(date);
                    }
                    else if (date.isBefore(filter_date_start)) {
                        selectedFilterDates.remove(filter_date_start);
                        selectedFilterDates.add(date);
                        filter_date_start = date;
                        filter_start.setText(filter_date_start.toString());
                    }
                    else if (date.isAfter(filter_date_start)) {
                        selectedFilterDates.add(date);
                        filter_date_end = date;
                        filter_end.setText(filter_date_end.toString());
                    }
                }
                else if (filter_date_start == null && filter_date_end == null) {
                    filter_date_start = date;
                    selectedFilterDates.add(date);
                    filter_start.setText(date.toString());
                }
            }
            UpdateFilterList();
        });
        PDF_date_picker.setOnAction(event -> {
            LocalDate date = PDF_date_picker.getValue();
            if (date != null) {
                if (PDF_start != null && PDF_end != null) {
                    if (date.isEqual(PDF_start)) {
                        PDF_start = null;
                        PDF_interval_start.setText("");
                        selectedDates.remove(date);
                    }
                    else if (date.isEqual(PDF_end)) {
                        PDF_end = null;
                        PDF_interval_end.setText("");
                        selectedDates.remove(date);
                    }
                    else if (date.isBefore(PDF_start)) {
                        selectedDates.add(date);
                        selectedDates.remove(PDF_start);
                        PDF_start = date;
                        PDF_interval_start.setText(PDF_start.toString());
                    }
                    else if (date.isAfter(PDF_end)) {
                        selectedDates.add(date);
                        selectedDates.remove(PDF_end);
                        PDF_end = date;
                        PDF_interval_end.setText(PDF_end.toString());
                    }
                    else if (date.isAfter(PDF_start) && date.isBefore(PDF_end)) {
                        selectedDates.add(date);
                        selectedDates.remove(PDF_end);
                        PDF_end = date;
                        PDF_interval_end.setText(PDF_end.toString());
                    }
                }
                else if (PDF_start == null && PDF_end != null) {
                    if (date.isEqual(PDF_end)) {
                        PDF_end = null;
                        PDF_interval_end.setText("");
                        selectedDates.remove(date);
                    }
                    else if (date.isBefore(PDF_end)) {
                        selectedDates.add(date);
                        PDF_start = date;
                        PDF_interval_start.setText(PDF_start.toString());
                    }
                    else if (date.isAfter(PDF_end)) {
                        selectedDates.add(date);
                        selectedDates.remove(PDF_end);
                        PDF_end = date;
                        PDF_interval_end.setText(PDF_end.toString());
                    }
                }
                else if (PDF_start != null && PDF_end == null) {
                    if (date.isEqual(PDF_start)) {
                        PDF_start = null;
                        PDF_interval_start.setText("");
                        selectedDates.remove(date);
                    }
                    else if (date.isBefore(PDF_start)) {
                        selectedDates.remove(PDF_start);
                        selectedDates.add(date);
                        PDF_start = date;
                        PDF_interval_start.setText(PDF_start.toString());
                    }
                    else if (date.isAfter(PDF_start)) {
                        selectedDates.add(date);
                        PDF_end = date;
                        PDF_interval_end.setText(PDF_end.toString());
                    }
                }
                else if (PDF_start == null && PDF_end == null) {
                    PDF_start = date;
                    selectedDates.add(date);
                    PDF_interval_start.setText(date.toString());
                }
            }
            UpdatePDFList();
        });
        groups_table1.setOnMouseClicked(event -> {
            TreeItem<Student> selectedItem = groups_table1.getSelectionModel().getSelectedItem();
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
                if (selectedItem != null) {
                    Student selectedPerson = selectedItem.getValue();
                    if (selectedPerson != null) {
                        int i = 0;
                        for (TreeItem<Student> group : groups_table1.getRoot().getChildren()) {
                            if (group.getValue().equals(selectedPerson)) {
                                date_view.setVisible(false);
                                selectedGroup = i;
                                list_view1.setItems(groupList.get(i).getStudents());
                                UpdateFilterList();
                                setData();
                                return;
                            }
                            i++;
                        }
                        for (TreeItem<Student> group : groups_table1.getRoot().getChildren()) {
                            for (TreeItem<Student> student : group.getChildren()) {
                                if (student.getValue().equals(selectedPerson)) {
                                    date_view.setVisible(true);
                                    //selectedPerson.makeFilteredDates(filter_date_start, filter_date_end);
                                    selectedGroup = -3;
                                    selectedStudent = selectedPerson;
                                    if (filter_date_start == null && filter_date_end == null) {
                                        date_view.setItems(selectedPerson.getAttendedDates());
                                    }
                                    else {
                                        UpdateFilterList();
                                        date_view.setItems(selectedPerson.getFilteredDates());
                                        date_view.refresh();
                                    }
                                    return;
                                }
                                i++;
                            }
                            i = 0;
                        }
                    }
                }
            }
        });
    }
    /**
     * Opens another window to modify student data.
     *
     * @param event      The MouseEvent that triggered the method.
     * @param person     The Student object to be modified.
     * @param modifiable A boolean value indicating if the data is modifiable inside the student modifying screen.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    private void openAnotherWindow(MouseEvent event, Student person, boolean modifiable) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("modify_student.fxml"));
        ModifyStudentController newController = new ModifyStudentController(this, modifiable);
        fxmlLoader.setController(newController);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        newController.setData(person, studentList);
        stage.setTitle("Alter data");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Opens a new window to modify a group.
     *
     * @param event The MouseEvent that triggered the method.
     * @param group The Group object to be modified.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    private void openGroupWindow(MouseEvent event, Group group) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("modify_group.fxml"));
        GroupModifyController newController = new GroupModifyController(this, group, freeStudents);
        fxmlLoader.setController(newController);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Alter group");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Method to set the text field, so it takes numbers only.
     * @param field TextField parameter needed to modify the text field.
     */
    private void setNumeric(TextField field) {
        field.setTextFormatter(new TextFormatter<>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                if (change.isContentChange()) {
                    if (change.getControlNewText().matches("[\\d\b]*")) {
                        return change;
                    }
                    return null;
                }
                return change;
            }
        }));
    }
    /**
     * Handles the event when the add student button is clicked.
     * Creates a new Student object with the entered student details and adds it to the studentList.
     * If any of the student details are empty or if a student with the same ID or student ID already exists in the studentList, the method returns without adding the student.
     * After adding the student, updates the free student list and refreshes the list view.
     * If an exception occurs during the process, prints "Data not entered" to the console.
     *
     * @param event the event object representing the button click
     */
    @FXML
    void add_student_button_click(ActionEvent event) {
        try {
            Student toAdd = new Student(stud_name.getText(), id.getText(), stud_id.getText(), stud_address.getText());
            if (Objects.equals(toAdd.getId(),"") || Objects.equals(toAdd.getName(),"") || Objects.equals(toAdd.getStudentId(),"") || Objects.equals(toAdd.getAddress(),""))  {
                return;
            }
            for (Student student : studentList) {
                if (Objects.equals(toAdd.getId(), student.getId()) || Objects.equals(toAdd.getStudentId(), student.getStudentId())) {
                    return;
                }
            }
            studentList.add(toAdd);
            stud_name.setText("");
            stud_id.setText("");
            id.setText("");
            stud_address.setText("");
            updateFreeStudentList();
            list_view.refresh();
        } catch (Exception e) {
            System.out.println("Data not entered");
        }
    }
    /**
     * Updates the data in the application by refreshing various UI components and lists.
     * This method is responsible for updating the groups table, list view, free students table,
     * groups table1, date view, PDF list, and filter list.
     */
    void setData() {
        updateGroups();
        groups_table.refresh();
        list_view.refresh();
        free_students_table.refresh();
        list_view.refresh();
        groups_table1.refresh();
        date_view.refresh();
        UpdatePDFList();
        UpdateFilterList();
    }
    /**
     * Updates the list of free students.
     * A student is considered free if they are not already in a group.
     * This method adds any free students to the freeStudents list.
     * It also removes any students from the freeStudents list who have joined a group.
     */
    void updateFreeStudentList() {
        for (Student student : studentList) {
            if (!freeStudents.contains(student) && !student.isInGroup()) {
                freeStudents.add(student);
            }
        }
        freeStudents.removeIf(Student::isInGroup);
    }
    /**
     * Updates the groups by updating the free student list and updating the group tables.
     */
    void updateGroups() {
        updateFreeStudentList();
        IGroupTableView newFullTable = new GroupTableViewFullImpl();
        IGroupTableView newGroupOnlyTable = new GroupOnlyTableViewImpl();
        newFullTable.updateData(groups_table, groupList);
        newGroupOnlyTable.updateData(groups_table1, groupList);
    }
    /**
     * Creates a new group and adds it to the group list.
     * Updates the groups display.
     *
     * @param event the action event that triggered the method
     */
    @FXML
    void create_new_group(ActionEvent event) {
        groupList.add(new Group(groupList.size()));
        updateGroups();
    }
    /**
     * Deletes a group from the groupList and updates the UI accordingly.
     * If deletion is not allowed or the groupToDelete index is out of range, no action is taken.
     * After deleting a group, the remaining groups are renumbered and the UI is updated.
     */
    @FXML
    void delete_group(ActionEvent event) {
        if (!allowDeletion) return;
        if (groupToDelete < groupList.size()) {
            groupList.get(groupToDelete).clear();
            groupList.remove(groupToDelete);
            int i = 0;
            for (Group group : groupList) {
                group.newName(i);
                i++;
            }
        }
        updateGroups();
        updateFreeStudentList();
    }
    /**
     * Handles the event when the "group_auto_click" button is clicked.
     * This method is responsible for grouping the students based on the desired group count.
     * If the desired group count is greater than the number of students, each student will be assigned to a separate group.
     * If the desired group count is less than or equal to the number of students, the students will be evenly distributed among the groups.
     * After grouping the students, the method updates the groups and disables the deletion of groups.
     *
     * @param event The event object that triggered the method.
     */
    @FXML
    void group_auto_click(ActionEvent event) {
        if (Objects.equals(groups_desired.getText(), "")) return;
        int desiredGroupCount = Integer.parseInt(groups_desired.getText());
        allowDeletion = true;
        for (int i = 0; i < groupList.size(); i++) {
            groupList.get(0).clear();
        }
        groupList.clear();
        if (desiredGroupCount > studentList.size()) {
            int i = 0;
            for (Student student : studentList) {
                groupList.add(new Group(groupList.size()));
                groupList.get(i).add(student);
                i++;
            }
        }
        else if (desiredGroupCount > 0) {
            for (int i = 0; i < desiredGroupCount; i++) {
                groupList.add(new Group(groupList.size()));
            }
            int i = 0;
            for (Student student : studentList) {
                groupList.get(i).add(student);
                i++;
                if (i == groupList.size()) i = 0;
            }
        }
        updateGroups();
        allowDeletion = false;
    }
    /**
     * Saves the PDF file using the PDF_Saver class.
     * 
     * @param event the action event that triggered the method
     * @throws IOException if an I/O error occurs while saving the PDF file
     * @throws DocumentException if an error occurs while generating the PDF document
     */
    @FXML
    void save_to_PDF(ActionEvent event) throws IOException, DocumentException {
        PDF_Saver.generatePDF(PDF_List, "report.pdf");
    }
    /**
     * Updates the PDF list based on the start and end dates.
     * If the start or end date is null, the PDF list will be cleared.
     * The PDF list is populated with PDF_Data objects for each date between the start and end dates.
     * Each PDF_Data object is populated with students from the groupList and freeStudents.
     * Finally, the PDF view is refreshed.
     */
    void UpdatePDFList() {
        if (PDF_start == null || PDF_end == null) {
            PDF_List.clear();
            return;
        }
        int i = 0;
        LocalDate startDate = PDF_start;
        LocalDate endDate = PDF_end.plusDays(1);
        PDF_List.clear();
        while (startDate.isBefore(endDate)) {
            i = 0;
            PDF_List.add(new PDF_Data(startDate));
            for (PDF_Data data : PDF_List) {
                if (startDate.equals(data.getDate())) {
                    break;
                }
                i++;
            }
            for (Group group : groupList) {
                for (Student student : group.getStudents()) {
                    PDF_List.get(i).Add(student);
                }
            }
            for (Student student : freeStudents) {
                PDF_List.get(i).Add(student);
            }
            startDate = startDate.plusDays(1);
        }
        PDF_view.refresh();
    }
    /**
     * Updates the filter list based on the selected group and date range.
     * If no date range is specified, the filter list is updated based on the selected group and student.
     * If a date range is specified, the filter list is updated based on the selected group and the dates attended by the students.
     * The updated filter list is then displayed in the list view.
     */
    void UpdateFilterList() {
        if (filter_date_start == null && filter_date_end == null) {
            if (selectedGroup == -3 && selectedStudent != null) {
                date_view.setItems(FXCollections.observableArrayList(selectedStudent.getAttendedDates()));
                return;
            }
            if (selectedGroup == -2) {
                for (Student student : studentList) {
                    student.setFilteredDates(FXCollections.observableArrayList(student.getAttendedDates()));
                    filterItems.add(student);
                }
            }
            else if (selectedGroup == -1) {
                for (Student student : freeStudents) {
                    student.setFilteredDates(FXCollections.observableArrayList(student.getAttendedDates()));
                    filterItems.add(student);
                }
            }
            else {
                for (Student student : groupList.get(selectedGroup).getStudents()) {
                    student.setFilteredDates(FXCollections.observableArrayList(student.getAttendedDates()));
                    filterItems.add(student);
                }
            }
            return;
        }
        list_view1.setItems(filterItems);
        int i = 0;
        LocalDate startDate = filter_date_start;
        LocalDate endDate = filter_date_end;
        filterItems.clear();
        if (selectedGroup == -3) {
            selectedStudent.makeFilteredDates(startDate, endDate);
            date_view.setItems(selectedStudent.getFilteredDates());
        }
        else if (selectedGroup == -2) {
            for (Student student : studentList) {
                student.makeFilteredDates(startDate, endDate);
                if (!student.getFilteredDates().isEmpty()) {
                    filterItems.add(student);
                }
            }
        }
        else if (selectedGroup == -1) {
            for (Student student : freeStudents) {
                student.makeFilteredDates(startDate, endDate);
                if (!student.getFilteredDates().isEmpty()) {
                    filterItems.add(student);
                }
            }
        }
        else {
            System.out.print("ok");
            for (Student student : groupList.get(selectedGroup).getStudents()) {
                student.makeFilteredDates(startDate, endDate);
                if (!student.getFilteredDates().isEmpty()) {
                    System.out.print("ok");
                    filterItems.add(student);
                }
            }
        }
        list_view1.refresh();
    }
    /**
     * Imports data from a CSV file and updates the application's data.
     *
     * @param event the event that triggered the import action
     * @throws IOException if an I/O error occurs while reading the CSV file
     */
    @FXML
    void import_csv(ActionEvent event) throws IOException {
        FileHandler newFileHandler = new FileHandler(groupList, studentList, freeStudents);
        newFileHandler.readCSV();
        set_filter_to_all(event);
        setData();
    }

    /**
     * Imports data from an Excel file and updates the PDF list, filter list, and sets the data.
     *
     * @param event the event that triggered the import action
     * @throws IOException if an I/O error occurs while reading the Excel file
     */
    @FXML
    void import_excel(ActionEvent event) throws IOException {
        FileHandler newFileHandler = new FileHandler(groupList, studentList, freeStudents);
        newFileHandler.readExcel();
        UpdatePDFList();
        UpdateFilterList();
        setData();
    }
    /**
     * Exports the data to a CSV file.
     *
     * @param event the event that triggered the export
     * @throws IOException if an I/O error occurs while writing the CSV file
     */
    @FXML
    void export_csv(ActionEvent event) throws IOException {
        FileHandler newFileHandler = new FileHandler(groupList, studentList, freeStudents);
        newFileHandler.WriteCSV();
    }

    /**
     * Export the data to an Excel file.
     *
     * @param event the action event that triggered the export
     * @throws IOException if an I/O error occurs while writing the Excel file
     */
    @FXML
    void export_excel(ActionEvent event) throws IOException {
        FileHandler newFileHandler = new FileHandler(groupList, studentList, freeStudents);
        newFileHandler.writeExcel();
    }
    /**
     * Sets the filter to show all students.
     * This method is called when the "Set Filter to All" button is clicked.
     * It hides the date view, sets the selected group to -2, and updates the filter list.
     *
     * @param event The event that triggered the method.
     */
    @FXML
    void set_filter_to_all(ActionEvent event) {
        date_view.setVisible(false);
        selectedGroup = -2;
        list_view1.setItems(studentList);
        UpdateFilterList();
    }

    /**
     * Sets the filter to display only free students.
     * This method hides the date view, resets the selected group to -1,
     * and updates the list view to display only free students.
     * It also updates the filter list.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    void set_filter_to_free_students(ActionEvent event) {
        date_view.setVisible(false);
        selectedGroup = -1;
        list_view1.setItems(freeStudents);
        UpdateFilterList();
    }
}
