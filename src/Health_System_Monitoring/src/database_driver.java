//singleton class to get database connection

import java.sql.*;

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
	private Connection databaseConnection = null; 
	private PreparedStatement sqlStatement = null;
	
	//id of user from database - user table
	private int userId = 0;
	
	//login failed 
	private int loginFailed = -1;
	
	
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
	 * method to check login credentials
	 * 
	 */
	public int checkCredentials(String username, String userPassword) {
		ResultSet resultSet = null;
		
		if(username != null && userPassword != null) {
			String query = "SELECT * FROM `user` WHERE `username`=? AND `userPassword`=?";
			
			try {
				sqlStatement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				sqlStatement.setString(1, username);
				sqlStatement.setString(2, userPassword);
				
				resultSet = sqlStatement.executeQuery();
				
				if(resultSet.next()) {
					userId = resultSet.getInt("userId");
					return userId;
				}
				else {
					System.out.println("Incorrect username or password | Login failed");
					return loginFailed;
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
		return loginFailed;
	}
	
	/*
	 * - method to add new patients to database
	 */
	

}
