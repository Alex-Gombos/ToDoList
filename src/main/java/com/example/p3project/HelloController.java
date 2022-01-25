package com.example.p3project;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public Button btnDelete;
    public Button btnDone;
    @FXML
    private TableColumn<Task, String> col1;
    @FXML
    private TableColumn<Task, String> col2;
    @FXML
    private TableColumn<Task, String> col3;
    @FXML
    private TableColumn<Task, String> col4;
    @FXML
    private TableColumn<Task, String> col5;
    @FXML
    private TableColumn<Task, String> col6;

    @FXML
    TableView<Task> tableview1;

    @FXML
    Button btnAdd;

    @FXML
    TextField txtField1;
    @FXML
    TextField txtField2;
    @FXML
    TextField txtField3;
    @FXML
    TextField txtField4;
    @FXML
    TextField txtField5;
    @FXML
    TextField txtField6;
    @FXML
    TextField txtField7;
    @FXML
    TextField txtFieldShow;
    @FXML
    Button btnShow;

    @FXML
    TextField txtFieldDone;
    @FXML
    TextField txtFieldDelete;

    List<TextField> txtFieldList = new ArrayList<>();


    List <Task> workList = new ArrayList<>();
    dbOperations db = new dbOperations();
    final ObservableList<Task> data = FXCollections.observableArrayList();

    public void init(){

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        db.populateTable(workList);

        data.addAll(workList);

        tableview1.setEditable(true);

        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("location"));
        col4.setCellValueFactory(new PropertyValueFactory<>("date"));
        col5.setCellValueFactory(new PropertyValueFactory<>("time"));
        col6.setCellValueFactory(new PropertyValueFactory<>("details"));
        col6.setCellFactory(TextFieldTableCell.forTableColumn());
        col6.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDetails(t.getNewValue())
        );

        tableview1.getColumns().clear();

        tableview1.setItems(data);
        tableview1.getColumns().addAll(col2, col3, col4, col5, col6);

        customiseFactory(col3);

    }
    private void customiseFactory(TableColumn<Task, String> obj) {
        obj.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem());
                setGraphic(null);

                TableRow<Task> currentRow = getTableRow();
                if (!isEmpty()) {

                    if (workList.get(currentRow.getIndex()) != null && workList.get(currentRow.getIndex()) instanceof Work && ((Work) workList.get(currentRow.getIndex())).isUrgent())
                        currentRow.setStyle("-fx-background-color:lightcoral");
                    else {
                        if (workList.get(currentRow.getIndex()) != null && workList.get(currentRow.getIndex()) instanceof Other)
                            currentRow.setStyle("-fx-background-color:lightblue");
                        else
                            currentRow.setStyle("-fx-background-color:lightgray");
                    }
                    if (workList.get(currentRow.getIndex()) != null && workList.get(currentRow.getIndex()) instanceof Done && ((Done) workList.get(currentRow.getIndex())).isDone())
                        currentRow.setStyle("-fx-background-color:lightgreen");
                } else
                    currentRow.setStyle("-fx-background-color:white");
            }
        });
    }


    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    public void btnAddClick(MouseEvent mouseEvent) {

        txtFieldList.add(txtField1);
        txtFieldList.add(txtField2);
        txtFieldList.add(txtField3);
        txtFieldList.add(txtField4);
        txtFieldList.add(txtField5);
        txtFieldList.add(txtField6);
        txtFieldList.add(txtField7);

        boolean add = true;
        for(TextField txt:txtFieldList){
            System.out.println(txt.getText());
            if(Objects.equals(txt.getText(), "")){
                add = false;
            }
        }
        if(add) {
            if (Objects.equals(txtField7.getText(), "work")) {
                boolean status;
                status = Objects.equals(txtField6.getText(), "1");
                Work work = new Work(txtField1.getText(), txtField2.getText(), txtField3.getText(), Integer.parseInt(txtField4.getText()), txtField5.getText(), status, false);
                db.addTask(work);
                workList.add(work);
                data.add(work);
            }
            else{
                if (Objects.equals(txtField7.getText(), "other")){
                    boolean done;
                    done = Objects.equals(txtField6.getText(), "1");
                    Other other = new Other(txtField1.getText(), txtField2.getText(), txtField3.getText(), Integer.parseInt(txtField4.getText()), txtField5.getText(), done);
                    db.addTask(other);
                    workList.add(other);
                    data.add(other);
                }
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Error adding Task!", "Please fill in all fields!");
        }

    }

    @FXML
    public void btnShowClick(MouseEvent mouseEvent) {

        if(Objects.equals(txtFieldShow.getText(), "urgent")) {
            data.clear();
            tableview1.getItems().clear();
            for (Task task : workList) {
                if (task instanceof Work && ((Work) task).isUrgent()) {
                    data.add(task);
                    System.out.println(task.nameProperty().getValue());
                }
            }

        }

        if(Objects.equals(txtFieldShow.getText(), "other")) {
            data.clear();
            tableview1.getItems().clear();
            for (Task task : workList) {
                if (task instanceof Other) {
                    data.add(task);
                    System.out.println(task.nameProperty().getValue());
                }
            }

        }
        if(Objects.equals(txtFieldShow.getText(), "all")) {
            data.clear();
            tableview1.getItems().clear();
            data.addAll(workList);
        }
        if(Objects.equals(txtFieldShow.getText(), ""))
            showAlert(Alert.AlertType.ERROR, "Error searching tasks!", "Not an option!");
        customiseFactory(col3);
    }

    public void btnShowDone(MouseEvent mouseEvent) {
        if(!Objects.equals(txtFieldDone.getText(), "")) {
            int row = Integer.parseInt(txtFieldDone.getText());
            System.out.println(row);
            if(workList.get(row) instanceof Done){
                ((Done) workList.get(row)).toggleDone();
            }
            customiseFactory(col3);
            db.updateTableDone(workList.get(row));

        }else
            showAlert(Alert.AlertType.ERROR, "Error searching tasks!", "Not a row!");
    }

    @FXML
    public void btnDeleteClick(MouseEvent mouseEvent) {
        if(!Objects.equals(txtFieldDelete.getText(), "")) {
            int rownum = db.deleteTask(txtFieldDelete);
            //workList.remove(rownum);
            workList.clear();
            db.populateTable(workList);
            data.clear();
            tableview1.getItems().clear();
            data.addAll(workList);
        }
        else
            showAlert(Alert.AlertType.ERROR, "Error deleting task!", "Not a row!");
    }
}