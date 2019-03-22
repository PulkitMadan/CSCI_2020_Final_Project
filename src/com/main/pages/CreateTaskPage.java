
package com.main.pages;

import com.main.Task;
import com.main.TaskPool;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateTaskPage{
    
    private final TaskPool taskPool;
    private TextField nameField, notesField;
    private Runnable refresher;
    
    public CreateTaskPage(TaskPool taskPool, Runnable refresher){
        this.taskPool = taskPool;
        this.refresher = refresher;
        nameField = new TextField();
        notesField = new TextField();
    }
    
    public void start(Stage stage){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        Button okButton = new Button("Create Task");
        okButton.setOnMousePressed(e -> {
            Task t = createTask();
            taskPool.addTask(t);
            refresher.run();
        });
        
        pane.add(new Label("Task name: "), 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(new Label("Due date: "), 0, 1);
        pane.add(notesField, 1, 1);
        pane.add(okButton, 1, 2);
        
        stage.setScene(new Scene(pane));
        stage.show();
    }
    
    public Task createTask(){
        Task t = new Task(nameField.getText());
        t.setNotes(notesField.getText());
        return t;
    }
}