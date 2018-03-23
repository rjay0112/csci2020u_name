package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML GridPane addUser;
    @FXML TableView<StudentRecord> students;
    @FXML Label SIDLabel;
    @FXML TextField SIDField;
    @FXML Label MidtermLabel;
    @FXML TextField MidtermField;
    @FXML Label AssignmentsLabel;
    @FXML TextField AssignmentsField;
    @FXML Label FinalExamLabel;
    @FXML TextField FinalExamField;
    File currentFileName;


    public void initialize(){
        TableColumn<StudentRecord,String> idCol=new TableColumn<>("SID");
        idCol.setPrefWidth(150);
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        TableColumn<StudentRecord,Integer> assignCol=new TableColumn<>("Assignments");
        assignCol.setPrefWidth(150);
        assignCol.setCellValueFactory(new PropertyValueFactory<>("assignments"));

        TableColumn<StudentRecord,Integer> midtermCol=new TableColumn<>("Midterm");
        midtermCol.setPrefWidth(150);
        midtermCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));

        TableColumn<StudentRecord,Integer> examCol=new TableColumn<>("Final Exam");
        examCol.setPrefWidth(150);
        examCol.setCellValueFactory(new PropertyValueFactory<>("finalExam"));

        TableColumn<StudentRecord,Integer> markCol=new TableColumn<>("Final Mark");
        markCol.setPrefWidth(150);
        markCol.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

        TableColumn<StudentRecord,Integer> gradeCol=new TableColumn<>("Letter Grade");
        gradeCol.setPrefWidth(150);
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("studentGrade"));
        students.getColumns().add(idCol);
        students.getColumns().add(assignCol);
        students.getColumns().add(midtermCol);
        students.getColumns().add(examCol);
        students.getColumns().add(markCol);
        students.getColumns().add(gradeCol);
        students.setItems(DataSource.getAllMarks());

    }
    public void addStudent(ActionEvent event){
        students.getItems().add(new StudentRecord(SIDField.getText(),
                Float.parseFloat(AssignmentsField.getText()),
                Float.parseFloat(MidtermField.getText()),
                Float.parseFloat(FinalExamField.getText())));
    }
    public void newTable(ActionEvent event){
        students.getItems().clear();
    }
    public void openFile(ActionEvent event){
        FileChooser choosingFile=new FileChooser();
        choosingFile.setTitle("Choose csv file");
        currentFileName=choosingFile.showOpenDialog(new Stage());
        load(currentFileName);
    }
    public void exitProgram(ActionEvent event){
        Platform.exit();
    }
    public void load(File file){
        try{
            FileReader fileInput=new FileReader(file);
            BufferedReader input=new BufferedReader(fileInput);
            String line;
            students.getItems().clear();
            while((line=input.readLine())!=null){
                String[] data=line.split(",");
                students.getItems().add(new StudentRecord(data[0],
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3])));
            }
            input.close();
        }catch(Exception e){
            System.err.println("File not found");
        }
    }
    public void save(ActionEvent event){
        if(currentFileName==null){
            currentFileName=new File("test.csv");
        }
        try{
            PrintWriter output=new PrintWriter(currentFileName);
            for(int i=0;i<students.getItems().size();i++){
                StudentRecord row=students.getItems().get(i);
                output.println(row.toString());
            }
            output.close();
        }catch(Exception e){
            System.err.println("Invalid File");
        }
    }
    public void saveAs(ActionEvent event){
        FileChooser fc=new FileChooser();
        fc.setTitle("Save csv");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "csv files",
                        "*.csv"));
        currentFileName=fc.showOpenDialog(new Stage());
        try{
            PrintWriter output=new PrintWriter(currentFileName);
            for(int i=0;i<students.getItems().size();i++){
                StudentRecord row=students.getItems().get(i);
                output.println(row.toString());
            }
            output.close();
        }catch(Exception e){
            System.err.println("Invalid File");
        }
    }


}
