/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul9contoh1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Aldin
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pustakaloka";
    private static final String USER = "root";//sesuaikan usernya 
    private static final String PASSWORD = "";//sesuaikan password nya
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}