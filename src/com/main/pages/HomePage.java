
package com.main.pages;

import com.main.TaskPaneGenerator;
import com.main.TaskPoolQuery;
import com.main.TaskPool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class HomePage extends Application{
    
    private final TaskPool taskPool;
    private final TaskPoolQuery taskPoolQuery;
    private BorderPane mainPane;

    public HomePage(){
        taskPool = new TaskPool();
        taskPool.load();
        taskPoolQuery = new TaskPoolQuery(taskPool);
        mainPane = new BorderPane();
    }
    
    public void start(Stage primaryStage){
        
        //Pane that holds the pane for buttons
        BorderPane holdButtonsPane = new BorderPane();
        ImageView settingsButton = new ImageView(new Image("file:images/settingsButton.png"));
        ImageView addTaskButton = new ImageView(new Image("file:images/addTaskButton.png"));
        
        addTaskButton.setOnMousePressed(e -> {
            CreateTaskPage tap = new CreateTaskPage(taskPool, this::refresh);  
            Stage stage = new Stage();
            tap.start(stage);
        });
        
        //Pane that has the settings and add new task buttons
        GridPane buttonsPane = new GridPane(); 
        buttonsPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        buttonsPane.setHgap(10);
        buttonsPane.add(settingsButton, 0, 0);
        buttonsPane.add(addTaskButton, 1, 0);
        holdButtonsPane.setRight(buttonsPane);
        
        //RNG
        //RandomTaskGenerator taskGenerator = new RandomTaskGenerator();
        //ArrayList<Task> tasks = taskGenerator.generateTaskArray(5);

        //Task Display
        //GenerateTaskDisplay taskDisplay = new GenerateTaskDisplay(tasks);
        //for(Task task : tasks){
            //taskPool.addTask(task);
        //}
        
        refresh();
        
        //Add GridPanes to BorderPane
        mainPane.setBottom(holdButtonsPane);
                
        Scene scene = new Scene(mainPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void refresh(){
        TaskPaneGenerator tpg = new TaskPaneGenerator(taskPoolQuery.getTasks());
        mainPane.setRight(tpg.generateTaskPane());
    }
    
    public void stop(){
        taskPool.save();
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}