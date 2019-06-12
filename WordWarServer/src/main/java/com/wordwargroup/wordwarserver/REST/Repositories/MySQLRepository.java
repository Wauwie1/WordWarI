package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;
import com.github.windpapi4j.InitializationFailedException;
import com.github.windpapi4j.WinAPICallFailedException;
import com.github.windpapi4j.WinDPAPI;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j
public class MySQLRepository implements IDatabase {

    String connectionUrl = "jdbc:mysql://studmysql01.fhict.local:3306/dbi402348";
    @Value("${db.user}")
    private String dbUsername = "dbi402348";
    private static WinDPAPI winDPAPI;

    private String secret = "AQAAANCMnd8BFdERjHoAwE/Cl+sBAAAA4C6Y9jPWR0i7GjNs5yAttQAAAAACAAAAAAAQZgAAAAEAACAAAABnYiSVAFtYxxbvYEA2Z4BrsbZqzEhXrBnbZ1fmAUINjQAAAAAOgAAAAAIAACAAAABke5tvXEPsxP7gIEqOmLMjk+c3bjkZvVCDznMLtwEFCRAAAADYDdykXf5N5PPsXfqtz+klQAAAALakMdV2f7FSko5M2dSGR0FtIYWhdqEpCECEXD3bVse+7L6gk5wwvtGTHooj9B5mAwcKZsq3IGQpUI7NFto1snI=";

    private String loginQuery = "{CALL Login(?, ?, ?, ?)}";
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
                .getConnection(connectionUrl, dbUsername, decrypt(secret));
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
            log.error(e);
            return user;
        }
    }

    @Override
    public List<String> getWords() {
        List<String> words = new ArrayList<>();

        try ( Connection connect = DriverManager
                .getConnection(connectionUrl, dbUsername, decrypt(secret));
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(randomWordQuery)) {

            while (resultSet.next()) {
                String word = resultSet.getString(1).toUpperCase();
                words.add(word);
            }

            return words;

        } catch (Exception e) {
            log.error(e);
            return words;
        }


    }

    private static String decrypt(String secret) throws WinAPICallFailedException, InitializationFailedException {
        winDPAPI = WinDPAPI.newInstance(WinDPAPI.CryptProtectFlag.CRYPTPROTECT_UI_FORBIDDEN);
        byte[] encryptedBytes = Base64.getDecoder().decode(secret);
        return new String(winDPAPI.unprotectData(encryptedBytes), UTF_8);
    }
}
