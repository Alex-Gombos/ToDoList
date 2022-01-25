package com.example.p3project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelloController2 {

    public Label labelName;
    public Label labelTitle;
    public Label labelEmail;
    public Label labelPassword;
    public Button btnSubmit;

    public void switchToScene(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("table.fxml")));

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    dbOperations db = new dbOperations();




    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;



    public static boolean validateEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPet = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPet.matcher(email);
        return  matcher.find();
    }

    public static FileInputStream checkFile(){
        FileInputStream fstream=null;
        try {
            fstream = new FileInputStream("credentials.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file");
        }
        return fstream;
    }

    public static boolean checkCredentials(TextField txt1, TextField txt2, PasswordField psw1){
        try {
            FileInputStream fstream;
            fstream = checkFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine = br.readLine();
            String[] str = strLine.split(" ");
            String a, b, c;
            a = str[0];
            b = str[1];
            c = str[2];
            //System.out.println(txt1.getText() + txt2.getText() + psw1.getText());
            if(Objects.equals(txt1.getText(), a) && Objects.equals(txt2.getText(), b) && Objects.equals(psw1.getText(), c)){
                //showAlert(Alert.AlertType.ERROR, "Form Error!", "Login detail incorrect");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file");
        }
        return false;
    }

    public static void writeFile(String name, String email, String password){
        try {
            File myObj = new File("credentials.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter("credentials.txt");
            myWriter.write(name + " " + email + " " + password + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    public void clickSubmit(MouseEvent mouseEvent) throws IOException {

        String mail = emailField.getText();

        if(nameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter your name");
            return;
        }
        if(emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter your email id");
            return;
        }
        if(!validateEmail(mail)){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Entered email not valid");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter a password");
            return;
        }


        //writeFile(nameField.getText(), emailField.getText(), passwordField.getText());
        if(checkCredentials(nameField, emailField, passwordField)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Registration Successful!", "Welcome " + nameField.getText());
            switchToScene(mouseEvent);
        }
        else
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Entered credentials are wrong");

    }

}
