package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private TextField uName;
    private PasswordField pw;
    private TextField fullname;
    private TextField email;
    private TextField phone;
    private DatePicker birthday;
    private Button register;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("lab04");

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5,5,5,5));
        gp.setHgap(5);
        gp.setVgap(5);

        Label uNameLabel= new Label("Username:");
        uName = new TextField();
        uName.setPromptText("Username here");
        gp.add(uNameLabel,0,0);
        gp.add(uName,1,0);

        Label pwLabel = new Label("Password");
        pw=new PasswordField();
        pw.setPromptText("Password here");
        gp.add(pwLabel,0,1);
        gp.add(pw,1,1);

        Label fullnameLabel = new Label("Full Name");
        fullname= new TextField();
        fullname.setPromptText("Full name here");
        gp.add(fullnameLabel,0,2);
        gp.add(fullname,1,2);

        Label emailLabel = new Label("E-Mail:");
        email= new TextField();
        email.setPromptText("E-Mail here");
        gp.add(emailLabel,0,3);
        gp.add(email,1,3);

        Label phoneLabel = new Label("Phone #:");
        phone= new TextField();
        phone.setPromptText("Phone #");
        gp.add(phoneLabel,0,4);
        gp.add(phone,1,4);

        Label birthdayLabel = new Label("Date of Birth:");
        birthday = new DatePicker();
        birthday.setPromptText("Birthday here");
        gp.add(birthdayLabel,0,5);
        gp.add(birthday,1,5);

        register = new Button("Register");
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Username: "+ uName.getText());
                System.out.println("Password: "+ pw.getText());
                System.out.println("Full name: "+ fullname.getText());
                System.out.println("E-Mail: "+ email.getText());
                System.out.println("Phone #: "+phone.getText());
                System.out.println("Date of Birth: "+birthday.getValue());

                uName.clear();
                pw.clear();
                fullname.clear();
                email.clear();
                phone.clear();
                birthday.getEditor().clear();
            }
        });

        gp.add(register,1,6);

        primaryStage.setScene(new Scene(gp, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
