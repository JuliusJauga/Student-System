package com.example.lab4;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.util.List;
/**
* This class implements the IGroupTableView interface and provides methods to create and update a group view in a TreeTableView.
* @author Julius Jauga 5 gr. 1 pogr.
*/
public class GroupTableViewFullImpl implements IGroupTableView {

    /**
     * Creates the group view in the provided TreeTableView.
     *
     * @param startingTable The TreeTableView in which to create the group view.
     */
    @Override
    public void createGroupView(TreeTableView<Student> startingTable) {
        startingTable.getColumns().clear();
        TreeTableColumn<Student, String> treeTableColumn1 = new TreeTableColumn<>("Student name");
        TreeTableColumn<Student, String> treeTableColumn2 = new TreeTableColumn<>("Student ID");
        treeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        treeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("studentId"));
        startingTable.getColumns().addAll(treeTableColumn1, treeTableColumn2);
        startingTable.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Updates the data in the group view based on the provided list of groups.
     *
     * @param startingTable The TreeTableView to update.
     * @param groups        The list of groups containing the updated data.
     */
    @Override
    public void updateData(TreeTableView<Student> startingTable, List<Group> groups) {
        startingTable.setRoot(null);
        TreeItem<Student> groupsRoot = new TreeItem<>(new Student("Groups", "...", "...", "..."));
        int i = 0;
        for (Group group : groups) {
            i++;
            TreeItem<Student> groupItem = new TreeItem<>(new Student("Group " + i, "...", "...", "..."));
            for (Student students : group.getStudents()) {
                groupItem.getChildren().add(new TreeItem<>(students));
            }
            groupsRoot.getChildren().add(groupItem);
        }
        startingTable.setRoot(groupsRoot);
        startingTable.refresh();
    }
}
