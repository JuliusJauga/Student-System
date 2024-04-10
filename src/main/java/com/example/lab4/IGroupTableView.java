package com.example.lab4;

import javafx.scene.control.TreeTableView;

import java.util.List;

/**
 * The IGroupTableView interface represents a view for displaying and updating group data in a TreeTableView.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public interface IGroupTableView {
    
    /**
     * Creates the group view in the specified TreeTableView.
     * 
     * @param startingTable The TreeTableView to create the group view in.
     */
    void createGroupView(TreeTableView<Student> startingTable);
    
    /**
     * Updates the data in the specified TreeTableView with the given list of groups.
     * 
     * @param startingTable The TreeTableView to update the data in.
     * @param groups The list of groups to update the TreeTableView with.
     */
    void updateData(TreeTableView<Student> startingTable, List<Group> groups);
}
