
package com.main;

import com.main.pages.SingleTaskPage;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskPaneGenerator {
    
    private ArrayList<Task> tasks;
    
    public TaskPaneGenerator(ArrayList<Task> tasks){
        this.tasks = tasks;
    }
    
    public VBox generateTaskPane(){
        //aabid implement this function!!
        //you can change pane to a more relevant type if you want
        VBox vBox = new VBox();
        //you can modify the follow too:
        for(Task t : tasks){
            vBox.getChildren().add(generateSingleTaskPane(t));
        }
        
        return vBox; //also change this line
    }
    
    public GridPane generateSingleTaskPane(Task t){
        //aabid implement this function!!
        //again, you can change pane if you have a better type in mind
        GridPane taskPane = new GridPane();
        Button btTaskName = new Button("Task Name: " + t.getName());
        btTaskName.setOnMousePressed(e -> {
            SingleTaskPage dtp = new SingleTaskPage(t);  
            Stage stage = new Stage();
            dtp.start(stage);
        });
        btTaskName.widthProperty();
        Button btDueDate = new Button("Due Date: " + t.getDueDate());
        taskPane.add(btTaskName, 0, 0);
        taskPane.add(btDueDate, 1, 0);
        return taskPane;
    }
}