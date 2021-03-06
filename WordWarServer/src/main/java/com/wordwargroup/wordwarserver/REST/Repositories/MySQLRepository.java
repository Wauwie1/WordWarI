package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLRepository implements IDatabase {

    String connectionUrl = "jdbc:mysql://studmysql01.fhict.local:3306/dbi402348";
    @Value("${db.user}")
    private String username;
    @Value("${db.pass}")
    private String password;

    private String loginQuery = "{CALL Login(?, ?, ?, ?)}";
    private String wordQuery = "{CALL GetWord(?, ?)}";
    private String randomWordQuery =
            " SELECT\n" +
            " Word\n" +
            " FROM\n" +
            " words\n" +
            " ORDER BY RAND()\n" +
            " LIMIT 50;";

    @Override
    public User login(String username, String password) {
        User user = null;
        try ( Connection connect = DriverManager
                .getConnection(connectionUrl, "dbi402348", "Ifi4god#");
                CallableStatement statement = connect.prepareCall(loginQuery)) {

            // Set IN and OUT parameters
            statement.setString(1, username);
            statement.setString(2, password);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.registerOutParameter(4, Types.NVARCHAR);

            statement.execute();

            // Get returned user
            int id = statement.getInt(3);
            String dbUsername = statement.getString(4);

            if(dbUsername != null && id != 0) {
                user = new User();
                user.setUsername(dbUsername);
                user.setId(id);
                System.out.println(dbUsername + " logged in with id: " + id);
            }

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
    }

    @Override
    public String getWord(int id) {
        String word = null;
        try ( Connection connect = DriverManager
                .getConnection(connectionUrl, "dbi402348", "Ifi4god#");
              CallableStatement statement = connect.prepareCall(wordQuery)) {

            // Set IN and OUT parameters
            statement.setInt(1, id);
            statement.registerOutParameter(2, Types.NVARCHAR);

            statement.execute();

            // Get returned word
            word = statement.getString(2);

            return word;

        } catch (Exception e) {
            e.printStackTrace();
            return word;
        }
    }

    @Override
    public List<String> getWords() {
        List<String> words = new ArrayList<>();

        try ( Connection connect = DriverManager
                .getConnection(connectionUrl, "dbi402348", "Ifi4god#");
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(randomWordQuery)) {

            while (resultSet.next()) {
                String word = resultSet.getString(1).toUpperCase();
                words.add(word);
            }

            return words;

        } catch (Exception e) {
            e.printStackTrace();
            return words;
        }


    }
}
