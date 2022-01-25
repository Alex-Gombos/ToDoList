package com.example.p3project;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class dbOperations {

    public void addTask(Task task){
        ConnectDB conn = new ConnectDB();
        Connection connection = conn.getConn();

        String sql = "insert into ";
        
        int status = 0;
        int done = 0;
        
        if(task instanceof Work){
            sql = sql + "work(name, location, date, time, details, status, done) values ";
            if(((Work) task).isUrgent()) {
                status = 1;
            }
            sql = sql + "('" + task.nameProperty().getValue() + "','" + task.locationProperty().getValue() + "','" + task.dateProperty().getValue() + "'," + task.getTime() + ",'" + task.detailsProperty().getValue() + "'," + status + ", " + done + ")";
        }
        else{
            sql = sql + "other(name, location, date, time, details, done)values";
            if(((Other) task).isDone()) {
                done = 1;
            }
            sql = sql + "('" + task.nameProperty().getValue() + "','" + task.locationProperty().getValue() + "','" + task.dateProperty().getValue() + "'," + task.getTime() + ",'" + task.detailsProperty().getValue() + "'," + done + ")";
        }
        //sql = sql + "('" + task.nameProperty().getValue() + "','" + task.locationProperty().getValue() + "','" + task.dateProperty().getValue() + "'," + task.getTime() + ",'" + task.detailsProperty().getValue() + "'," + status + ")";
        //System.out.println(sql);
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void populateTable(List<Task>wrk){

        String name, location, date, details;
        int time;
        boolean status, done;


        List<Work> workList = new ArrayList<>();

        ConnectDB conn = new ConnectDB();
        Connection connection = conn.getConn();

        String sql = "select * from work";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                name = rs.getString("name");
                location = rs.getString("location");
                date = rs.getString("date");
                time =  Integer.parseInt(rs.getString("time"));
                details = rs.getString("details");
                //status =  Boolean.parseBoolean(rs.getString("status"));
                status = Integer.parseInt(rs.getString("status")) == 1;
                done = Integer.parseInt(rs.getString("done")) == 1;

                Work item = new Work(name, location, date, time, details, status, done);
                wrk.add(item);

                String str = name + location +" " + status;
               // System.out.println(str);
            }
            String sql1="select * from other";
            ResultSet rs1 = stmt.executeQuery(sql1);
            while(rs.next()){
                name = rs.getString("name");
                location = rs.getString("location");
                date = rs.getString("date");
                time =  Integer.parseInt(rs.getString("time"));
                details = rs.getString("details");
                //status =  Boolean.parseBoolean(rs.getString("status"));
                done = Integer.parseInt(rs.getString("done")) == 1;

                Other item = new Other(name, location, date, time, details, done);
                wrk.add(item);

                String str = name + location +" " + done;
                System.out.println(str);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int deleteTask(TextField txt){

        int rownum;

        try {
            ConnectDB conn = new ConnectDB();
            Connection connection = conn.getConn();

            String[] str_arr = txt.getText().split(" ");

            String a, b;
            a=str_arr[0];
            b=str_arr[1];


            String sql="delete from ";
            String tbname = a;
            rownum = Integer.parseInt(b);

            sql = sql + tbname + " where ROWID = " + rownum;

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("VACUUM");
            stmt.executeUpdate(sql);
            System.out.println("Entry successfully deleted");

        }catch(SQLException e) {
            throw new Error("Could not delete row", e);
        }
        return rownum;
    }
    public void updateTableDone(Task task){
        try {
            ConnectDB conn = new ConnectDB();
            Connection connection = conn.getConn();

            int done=0;
            if(task instanceof Done){
                if(((Done) task).isDone())
                    done=1;
            }

            String sql = "update work set done = " + done + " where name = '" + task.nameProperty().getValue() + "'" ;


            Statement stmt = connection.createStatement();
            stmt.executeUpdate("VACUUM");
            stmt.executeUpdate(sql);

        }catch(SQLException e) {
            throw new Error("Could not delete row", e);
        }
    }
}
