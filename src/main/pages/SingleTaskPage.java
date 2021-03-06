package main.pages;

import java.util.ArrayList;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import main.Task;
import main.TaskPriority;


public class SingleTaskPage extends Application {

    private Task t;
    private Date todaysDate  = new Date();
    private Runnable refresher;
    private ArrayList<TextField> tfs = new ArrayList<>() ;
    private String attachmentNames = "";
    
    //setting up uniform date format 
    private String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    
    //constructor
    public SingleTaskPage(Task task, Runnable refresher){
        t = task;
        this.refresher = refresher;
    }

    @Override
    public void start(Stage primaryStage){
        
        //main pane that stores the other objects
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
        
        //text field to display the task name
        TextField tfTaskName = new TextField(t.getName());
        tfTaskName.setStyle("-fx-border-color: #ffffff; -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        tfTaskName.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        tfTaskName.prefWidthProperty().bind(root.widthProperty().multiply(0.6));
        tfTaskName.setDisable(true);  //task name will be editable only in the edit mode
        tfs.add(tfTaskName);

        //Label to display the text : Due
        Label lDue = new Label("DUE: ");
        lDue.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        lDue.setAlignment(Pos.BOTTOM_RIGHT);
        lDue.setStyle("-fx-border-color: #ffffff");
        lDue.prefWidthProperty().bind(root.widthProperty().multiply(0.15));

        //textField to display the due date
        TextField tfDueDate = new TextField(simpleDateFormat.format(t.getDueDate()));
        tfDueDate.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        tfDueDate.setStyle("-fx-border-color: #ffffff; -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        tfDueDate.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        tfDueDate.setDisable(true);
        
        //allows the user to change the due date when clicked in edit mode
        tfDueDate.setOnMousePressed(event -> {
            //using DatePicker to pick a date from calender
            DatePicker datePicker = new DatePicker();
            datePicker.setStyle(" -fx-background-color : rgb(253,255,226)");
            //removing week #s from display 
            datePicker.setShowWeekNumbers(false);
            datePicker.setPromptText("Select Due Date");
            //creating a new scene to display the datePicker
            Scene dateScene = new Scene(datePicker, 300, 200);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(dateScene);
            secondaryStage.setTitle("Select Due Date");
            secondaryStage.show();
            
            
            datePicker.setOnAction(e->{
                //getting the date selected by the user and setting as the due date for the task
                Date date =  java.sql.Date.valueOf(datePicker.getValue());
                t.setDueDate(date);
            });
            
            secondaryStage.setOnCloseRequest(f->{
                //update the dueDate text label when datePicker is closed
                tfDueDate.setText(t.getDueDate().toString());
            });
        });
        tfs.add(tfDueDate);
        hb1.getChildren().addAll(tfTaskName,lDue, tfDueDate);
 
    
        //Button to display the priority of task
        Button bPriority = new Button();
        bPriority.setPadding(new Insets(5, 5, 5, 5));
        updateBPriority(bPriority);
      
        bPriority.setStyle("-fx-border-color: rgb(253,255,226); -fx-background-color : rgb(253,255,226); -fx-opacity: 1.0; -fx-border-radius: 5");
        bPriority.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));
        
        //adding image to the button
        ImageView IeditDueIn = new ImageView("file:images/ud2.png");
        IeditDueIn.setFitWidth(20);
        IeditDueIn.setFitHeight(bPriority.getHeight());
        IeditDueIn.setPreserveRatio(true);
        Button bEditDueIn = new Button();
        bEditDueIn.setGraphic(IeditDueIn);
        bEditDueIn.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000");
        
        //user can change the priority of the task when button is pressed
        bEditDueIn.setOnMousePressed(e->{
            VBox choicePane = new VBox(10);
            choicePane.setPadding(new Insets(5,5,5,5));
            choicePane.setStyle("-fx-background-color: rgb(253,255,226)");
            
            //choice box to view priority choices
            ChoiceBox<TaskPriority> selectPriority = new ChoiceBox<>();
            selectPriority.setStyle("-fx-background-color :rgba(224,108,214,0.87); -fx-border-color: #000000");
            selectPriority.setValue(t.getPriority());
            selectPriority.getItems().addAll(TaskPriority.HIGH,TaskPriority.MEDIUM, TaskPriority.LOW );
            
            //button to submit the changes
            Button bSubmit = new Button("Submit");
            bSubmit.setPadding(new Insets(5, 5, 5, 5));
            bSubmit.setStyle("-fx-border-color: #000000; -fx-background-color :rgba(224,108,214,0.87); -fx-opacity: 1.0; -fx-border-radius: 5");
            bSubmit.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 16));

            
            choicePane.getChildren().addAll(selectPriority, bSubmit);
            Scene scene = new Scene(choicePane, 300, 100);
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

        //text Area to display the notes
        TextArea taDisplayNotes = new TextArea();
        taDisplayNotes.prefWidthProperty().bind(root.widthProperty());
        taDisplayNotes.prefHeightProperty().bind(root.heightProperty().multiply(0.25));
        taDisplayNotes.setText(t.getNotes());
        taDisplayNotes.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
        taDisplayNotes.setDisable(false);
        taDisplayNotes.setEditable(false);
        
        //Label to display "Attachements"
        Label lAttachments = new Label("Attachments");
        lAttachments.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        lAttachments.setPadding(new Insets(5,5,5,5));
        lAttachments.setStyle("-fx-border-color: black;");
        lAttachments.setStyle("-fx-border-color: #000000;-fx-border-radius: 7");
       
        //Text area to view names of attached Files
        TextArea taAttachments = new TextArea();
        taAttachments.prefWidthProperty().bind(root.widthProperty());
        taAttachments.prefHeightProperty().bind(root.heightProperty().multiply(0.22));
        if((t.getAttachedFiles())==null){
            taAttachments.setText("No files attached");
        }
        else{
            updateAttachments(taAttachments);           
        }
 
        taAttachments.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
        taAttachments.setDisable(false);
        taAttachments.setEditable(false);
        
        
        //button to add attachments
        ImageView iAddAttachment = new ImageView("file:images/plus1.png");
        Button bAdd = new Button();
        iAddAttachment.setFitWidth(20);
        iAddAttachment.setFitHeight(bAdd.getHeight()-2);
        iAddAttachment.setPreserveRatio(true);
        bAdd.setGraphic(iAddAttachment);
        bAdd.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 7");
        
        //launches file chooser to add attachments
        bAdd.setOnMousePressed(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add attachment");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if(selectedFile != null){
                t.addAttachedFile(selectedFile);
                refresher.run();
                updateAttachments(taAttachments);
            }
        });
        
        //HBox to store the label attachments and the add button
        HBox hb3 = new HBox(lAttachments, bAdd);
        hb3.setPadding(new Insets(5, 5, 5, 5));
        hb3.setSpacing(7);
        
        //adding all the nodes to the root
        root.getChildren().addAll(hb1, hb2, lNotes, taDisplayNotes, hb3, taAttachments);
         
        //Display a message and completion Date if task has been completed
        if(t.isCompleted() == true){
            Label lTaskCompleted = new Label();
            String message = "This task was completed on " + simpleDateFormat.format(t.getCompletionDate());
            lTaskCompleted.setText(message);
            lTaskCompleted.prefWidthProperty().bind(root.widthProperty());
            lTaskCompleted.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            lTaskCompleted.prefHeightProperty().bind(root.heightProperty().multiply(0.1));
            lTaskCompleted.setStyle("-fx-border-color: #000000; -fx-background-color: rgb(209,193,245);  -fx-opacity: 1.0");
            root.getChildren().add(lTaskCompleted);
        }
        
        //Display a message if a task is past its due date
        if(isPastDueDate(t.getDueDate()) == true && !t.isCompleted()){
            Label lPastDueDate = new Label();
            String message = " This task is past its due date of : " + simpleDateFormat.format(t.getDueDate());
            lPastDueDate.setText(message);
            lPastDueDate.prefWidthProperty().bind(root.widthProperty());
            lPastDueDate.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            lPastDueDate.prefHeightProperty().bind(root.heightProperty().multiply(0.1));
            lPastDueDate.setStyle("-fx-border-color: #000000; -fx-background-color: rgba(224,9,0,0.87);  -fx-opacity: 1.0");
            root.getChildren().add(lPastDueDate);
        }
        
        
        //Adding the edit and save button 
        Button bEdit = new Button("Edit");
        bEdit.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        bEdit.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 5");
        
        //launch edit mode when pressed
        bEdit.setOnMousePressed(e->{
           edit(tfs, taDisplayNotes); 
        });
        
        Button bSave = new Button("Save");
        bSave.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        bSave.setStyle("-fx-background-color : rgb(253,255,226); -fx-border-color: #000000; -fx-border-radius: 5");
        
        //save the edits when save button is pressed
        bSave.setOnMousePressed(e->{
           save(primaryStage, tfTaskName, tfDueDate, taDisplayNotes); 
        });
        
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
       
        //addinf title to the stage and displaying
        primaryStage.setTitle(t.getName());
        primaryStage.setScene(new Scene(BPwrapper, 800, 600));
        primaryStage.show();
    }
    
    
    //updates color and text according to priority of the task
    public void updateBPriority(Button bPriority){
        bPriority.setText("This task is of "+ t.getPriority().toString() + " priority");
        bPriority.setTextFill(t.getPriority().color);
    }
    
    //updates the text area for attachments when new files are attached
    public void updateAttachments(TextArea attachments){
        String attachedFileNames= "";
        for(File file : t.getAttachedFiles()){
            attachedFileNames += (file.getName()+ "\n");
        }
        attachments.setText(attachedFileNames);
    }
    
    
    //launches edit mode; user can now edit notes all the text fields
    public void edit(ArrayList<TextField> tfs, TextArea taDisplayNotes){
        for (TextField tf : tfs){
            tf.setDisable(false);
        }
        taDisplayNotes.setDisable(false);
        taDisplayNotes.setEditable(true);   
    }
    
    //this function saves the changes made by the user in edit mode
    public void save(Stage stage, TextField TaskName, TextField dueDate, TextArea taDisplayNotes){
        t.setTaskName(TaskName.getText());
        TaskName.setDisable(true);
        
        dueDate.setDisable(true);
        
        t.setNotes(taDisplayNotes.getText());
        taDisplayNotes.setText(t.getNotes());
        taDisplayNotes.setEditable(false);
        //taDisplayNotes.setDisable(true);
        refresher.run();
        stage.close();
    }
    
   //function to check if a task is past its due date
    public boolean isPastDueDate(Date dueDate){
        boolean pastDueDate = false;
        Date currentDate = new Date();
        if(currentDate.compareTo(dueDate)>0){
            pastDueDate = true;
        }
        return pastDueDate;
    }
    
}