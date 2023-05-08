package com.klotski.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    // The path where the DB is located
    private static final String URL = "../Data/DB/Klotski.db";
    private static final String PREFIX_CONNECTOR = "jdbc:sqlite:path";

    // I don't need a constructor => the default is fine

    public void Connect()
    {
        // check if DB file exists...otherwise throw an error
        // create a Path to check in filesystem the existence
        Path path = Paths.get(URL);
        if(!Files.exists(path))
            throw new IllegalStateException("The DB file does not exist");
        // here the file exist so I am able to connect
        try{
            Connection connection = DriverManager.getConnection(PREFIX_CONNECTOR+URL);
            System.out.println("Connect to Klotski.db");
        }
        catch (Exception e )
        {
            System.out.println("ERROR");
        }


    }



}
