//singleton class to get database connection
package Health_System_Monitoring.Health_System_Monitoring;
import java.sql.*;


/*
 * @desc - A singleton database class access for mysql
 * This class will handle the database connection
 * @author - Kelechi Matthew Okwuriki
 */

public class database_driver {
	private static database_driver database_driver = null;
	private Connection databaseConnection = null; 
	
	
	/*
	 * *********** database connection info **********
	 */
	
	private String jdbcUrl = "jdbc:mysql://db.dcs.aber.ac.uk:3306/csm2020_18_19";
	private String databaseUser = "csm2020_admin";
	private String databasePass = "wybRJB7Q";
	
	
	private database_driver() {			
		try {			
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
	public static database_driver getConnection() {
		if(database_driver == null) {
			database_driver = new database_driver();
		}
		return database_driver;
	}
	
	/*
	 * method to close database connection
	 */
	public void closeDbConnection() {
		if(databaseConnection != null) {
			try {
				databaseConnection.close();
				System.out.println("Database connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*
	 * 
	 */
//	public ResultSet query(String query) {
//		statement 
//	}
//	
}
