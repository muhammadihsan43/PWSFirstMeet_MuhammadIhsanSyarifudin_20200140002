/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.learnmigratedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hp
 */
class Koneksi {
    
    private static Connection koneksi;
    
    public static Connection getCOnnection() throws SQLException {
        String db = "jdbc:mysql://localhost:3306/tokohp";
        String username = "root";
        String password = "";
        
        if(koneksi == null) {
            koneksi = DriverManager.getConnection(db, username, password);
        }
        return koneksi;
    }
    
}
