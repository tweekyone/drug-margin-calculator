package ru.tweekyone.graduateQualificationWork.databaseConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Пирожок
 */
public class ConnectionPool {
    private static Properties properties = new Properties();
    
    static{
        try{
            FileInputStream fis = new FileInputStream("src\\ru\\tweekyone\\graduateQualificationWork\\resources\\database.properties");
            properties.load(fis);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection()throws SQLException{
        Connection connection = DriverManager.getConnection(properties.getProperty("URL"), properties);
        return connection;
    }
}
