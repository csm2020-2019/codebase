package Health_System_Monitoring;
//singleton class to get database connection

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/*
 * TODO:encrypt and decrypt password
 */

/*
 * @desc - A singleton database class access for mysql
 * This class will handle the database connection
 * @author - Kelechi Matthew Okwuriki
 */

public class database_driver {
    private static database_driver database_driver = null;
    private static Connection databaseConnection = null;


    private database_driver() {
        try {

            /*
             * *********** database connection info **********
             */

            String jdbcUrl = "jdbc:mysql://localhost:3306/csm2020_18_19";
            String databaseUser = "csm2020_admin";
            String databasePass = "wybRJB7Q";

            databaseConnection = DriverManager.getConnection(jdbcUrl, databaseUser, databasePass);
            System.out.println("Connected to database");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /*
     * @return mysql database connection object
     */
    public static Connection getConnection() {
        if(database_driver == null) {
            database_driver = new database_driver();
        }
        return databaseConnection;
    }
}
