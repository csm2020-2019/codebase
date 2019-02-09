//singleton class to get database connection
import java.sql.*;


/*
 * @desc - A singleton database class access for mysql
 * This class will handle the database connection
 * @author - Kelechi Matthew Okwuriki
 */

public class database_driver {
	private static database_driver database_driver = null;
	
	
	/*
	 * *********** database connection info **********
	 */
	
	private String jdbcUrl = "jdbc:mysql://db.dcs.aber.ac.uk:3306/csm2020_18_19";
	private String databaseUser = "csm2020_admin";
	private String databasePass = "wybRJB7Q";
	
	
	private database_driver() {			
		try {			
			DriverManager.getConnection(jdbcUrl, databaseUser, databasePass);
			System.out.println("Connected to database");

		} catch (SQLException e) {
			e.printStackTrace();
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
	 * 
	 */
//	public ResultSet query(String query) {
//		statement 
//	}
//	
}
