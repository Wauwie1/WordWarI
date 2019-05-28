package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;

import java.sql.*;

public class MySQLRepository implements IDatabase {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public User login(String username, String password) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB

            connect = DriverManager
                    .getConnection("jdbc:mysql://studmysql01.fhict.local:3306/dbi402348", "dbi402348", "Ifi4god#");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String user = resultSet.getString("Username");
                System.out.println(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }



}
