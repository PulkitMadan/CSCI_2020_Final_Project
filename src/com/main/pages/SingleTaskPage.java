package com.main.pages;
import com.main.Task;
import com.main.TaskPriority;
import java.util.ArrayList;
import java.io.File;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


public class SingleTaskPage extends Application {

    private Task t;
    private Date todaysDate  = new Date();
    private Runnable refresher;
    private ArrayList<TextField> tfs = new ArrayList<>() ;
    private String attachmentNames = "";
    
    public SingleTaskPage(Task task, Runnable refresher){
        t = task;
        this.refresher = refresher;
    }

    @Override
    public void start(Stage primaryStage){
        
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: rgb(253,255,226)");
        root.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.8));
        root.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.8));
        root.setPadding(new Insets(5, 5, 5, 5));

        //Horizontal Pane to display Task name and due date
        HBox hb1 = new HBox();
        hb1.setPadding(new Insets(5, 5, 5, 5));
        hb1.prefWidthProperty().bind(root.widthProperty());
        hb1.setStyle("-fx-border-color: #000000;-fx-border-radius: 5");
        hb1.setAlignment(Pos.CENTER);
        
        //text field to display the name
        TextField tfTaskName = new TextField(t.getName());
        tfTaskName.setStyle("-fx-border-color: #ffffff; -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        tfTaskName.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        tfTaskName.prefWidthProperty().bind(root.widthProperty().multiply(0.6));
        tfTaskName.setDisable(true);
        tfs.add(tfTaskName);

        //Label to display the due date
        Label lDue = new Label("DUE: ");
        lDue.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        lDue.setAlignment(Pos.BOTTOM_RIGHT);
        lDue.setStyle("-fx-border-color: #ffffff");
        lDue.prefWidthProperty().bind(root.widthProperty().multiply(0.15));

        //textField to display the due date
        TextField tfDueDate = new TextField(String.valueOf(t.getDueDate()));
        tfDueDate.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        tfDueDate.setStyle("-fx-border-color: #ffffff; -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        tfDueDate.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        tfDueDate.setDisable(true);
        tfs.add(tfDueDate);
        hb1.getChildren().addAll(tfTaskName,lDue, tfDueDate);
 
    
        //Button to display the priority of task
        Button bPriority = new Button();
        bPriority.setPadding(new Insets(5, 5, 5, 5));
        updateBPriority(bPriority);

                
        bPriority.setStyle("-fx-border-color: rgb(253,255,226); -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        bPriority.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        
        //adding edit(pencil) imagee to the button
        ImageView IeditDueIn = new ImageView("file:images/pencil2.png");
        IeditDueIn.setFitWidth(20);
        IeditDueIn.setFitHeight(bPriority.getHeight());
        IeditDueIn.setPreserveRatio(true);
        Button bEditDueIn = new Button();
        bEditDueIn.setGraphic(IeditDueIn);
        bEditDueIn.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000");
        
        
        bEditDueIn.setOnMousePressed(e->{
            
        //adding choice box to display choicbox to change the priority of the task

            VBox choicePane = new VBox(10);
            choicePane.setPadding(new Insets(5,5,5,5));
            choicePane.setStyle("-fx-background-color: rgb(105,202,42)");
            
            //choice box to view priority choices
            ChoiceBox<TaskPriority> selectPriority = new ChoiceBox<>();
            selectPriority.setStyle("-fx-background-color : rgb(105,202,42); -fx-border-color: #000000");
            selectPriority.setValue(t.getPriority());
            selectPriority.getItems().addAll(TaskPriority.HIGH,TaskPriority.MEDIUM, TaskPriority.LOW );
            
            //button to submit the changes
            Button bSubmit = new Button("Submit");
            bSubmit.setPadding(new Insets(5, 5, 5, 5));
            bSubmit.setStyle("-fx-border-color: #000000; -fx-background-color :rgb(105,202,42); -fx-opacity: 1.0; -fx-border-radius: 5");
            bSubmit.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));

            
            choicePane.getChildren().addAll(selectPriority, bSubmit);
            Scene scene = new Scene(choicePane, 100, 150);
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.setTitle("Edit Priority");
            secondStage.show();
           
            //update the priority and close the secondary stage
            bSubmit.setOnMousePressed(f->{
                t.setTaskPriority(selectPriority.getValue());
                refresher.run();
                secondStage.close();
                updateBPriority(bPriority);
            });
            
        });

        HBox hb2 = new HBox(bPriority, bEditDueIn);
        hb2.setPadding(new Insets(5, 5, 5, 5));
        hb2.setSpacing(7);

        
        //displaying the notes
        Label lNotes = new Label("Notes");
        lNotes.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        lNotes.setPadding(new Insets(5,5,5,5));
        lNotes.setStyle("-fx-border-color: black;");
        lNotes.setStyle("-fx-border-color: #000000;-fx-border-radius: 7");

        
        TextField tfDisplayNotes = new TextField();
        tfDisplayNotes.prefWidthProperty().bind(root.widthProperty());
        tfDisplayNotes.prefHeightProperty().bind(root.heightProperty().multiply(0.25));
        tfDisplayNotes.setText(t.getNotes());
        tfDisplayNotes.setAlignment(Pos.TOP_LEFT);
        tfDisplayNotes.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
        tfDisplayNotes.setDisable(true);
        tfs.add(tfDisplayNotes);
        
        //Label to display "Attachements"
        Label lAttachments = new Label("Attachments");
        lAttachments.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        lAttachments.setPadding(new Insets(5,5,5,5));
        lAttachments.setStyle("-fx-border-color: black;");
        lAttachments.setStyle("-fx-border-color: #000000;-fx-border-radius: 7");
       
        
        TextArea tfAttachments = new TextArea();
        tfAttachments.prefWidthProperty().bind(root.widthProperty());
        tfAttachments.prefHeightProperty().bind(root.heightProperty().multiply(0.20));
        if((t.getAttachedFiles())==null){
            tfAttachments.setText("No files attached");
        }
        else{
            updateAttachments(tfAttachments);           
        }
 
        tfAttachments.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
        tfAttachments.setDisable(false);
        tfAttachments.setEditable(false);
        
        
        //button to add attachments
        ImageView iAddAttachment = new ImageView("file:images/plus1.png");
        Button bAdd = new Button();
        iAddAttachment.setFitWidth(20);
        iAddAttachment.setFitHeight(bAdd.getHeight()-2);
        iAddAttachment.setPreserveRatio(true);
        bAdd.setGraphic(iAddAttachment);
        bAdd.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 7");
        
        bAdd.setOnMousePressed(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add attachment");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if(selectedFile != null){
                t.addAttachedFile(selectedFile);
                refresher.run();
                updateAttachments(tfAttachments);
            }
        });
        
        HBox hb3 = new HBox(lAttachments, bAdd);
        hb3.setPadding(new Insets(5, 5, 5, 5));
        hb3.setSpacing(7);
        
        
        root.getChildren().addAll(hb1, hb2, lNotes, tfDisplayNotes, hb3, tfAttachments);
         
        //Display a message and completion Date if task has been completed
        if(t.isCompleted() == true){
            Label lTaskCompleted = new Label();
            String message = "This task was completed on " + t.getCompletionDate();
            lTaskCompleted.setText(message);
            lTaskCompleted.prefWidthProperty().bind(root.widthProperty());
            lTaskCompleted.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            lTaskCompleted.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
            root.getChildren().add(lTaskCompleted);
        }
        
        
        //Adding the edit and save button 
        Button bEdit = new Button("Edit");
        bEdit.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        bEdit.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 5");
        Button bSave = new Button("Save");
        bSave.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        bSave.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 5");
        
        //Adding edit and save buttons to Hbox
        HBox hb4 = new HBox(bSave, bEdit);
        hb4.setPadding(new Insets(5, 5, 5, 5));
        hb4.setSpacing(7);
        hb4.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: rgb(253,255,226);");
        
        //Adding hbox to bottom pane
        BorderPane bottomPane = new BorderPane();
        bottomPane.setRight(hb4);
        bottomPane.setPadding(new Insets(5,5,5,5));
        bottomPane.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: rgb(253,255,226)");
        
        //Adding bottomPane to wrapper border pane
        BorderPane BPwrapper = new BorderPane();
        BPwrapper.setPadding(new Insets(5,5,5,5));
        BPwrapper.setCenter(root);
        BPwrapper.setBottom(bottomPane);
       
        
        
      



        primaryStage.setTitle(t.getName());
        primaryStage.setScene(new Scene(BPwrapper, 800, 600));
        primaryStage.show();
    }
    
    
    public void updateBPriority(Button bPriority){
        bPriority.setText("This task is of "+ t.getPriority().toString() + " priority");
        bPriority.setTextFill(t.getPriority().color);
    }
    
    public void updateAttachments(TextArea attachments){
        String attachedFileNames= "";
        for(File file : t.getAttachedFiles()){
            attachedFileNames += (file.getName()+ "\n");
        }
        attachments.setText(attachedFileNames);
    }
    
    //TODO add edit button at the bottom
    


    /*
    public static void main(String[] args) {
        launch(args);
    }*/
}