package Health_System_Monitoring;
//singleton class to get database connection

import java.sql.*;
import java.util.Calendar;


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
		PreparedStatement sqlStatement = null;

		
		if(username != null && userPassword != null) {
			try {
				String query = "SELECT * FROM `user` WHERE `username`=? AND `userPassword`=?";
				
				sqlStatement = databaseConnection.prepareStatement(query);
				sqlStatement.setString(1, username);
				sqlStatement.setString(2, userPassword);
				
				resultSet = sqlStatement.executeQuery();
//				closeDbConnection();
				
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
	 * @ patient_dob Date of birth of patient
	 */
	public boolean addNewPatientsToDatabase(Date patient_dob, String patient_address, String patient_medical_history,
			String patient_diagnosis, String patient_prescriptions, int gp_id, int userId) {
		
		PreparedStatement sqlStatement = null;
		
		//NULL CHECKER FOR THE  METHOD ARGUMENTS
		if(patient_dob != null && patient_address != null & patient_medical_history != null
				&& patient_diagnosis != null && patient_prescriptions != null && gp_id > 0) {
			
			//create a date object to be used for the patient dob
			Calendar calender = Calendar.getInstance();
			patient_dob = new Date(calender.getTime().getTime());
			
			try {
				String query = "INSERT INTO patient_records (patient_dob, patient_address, patient_medical_history,"
						+ "patient_diagnosis, patient_prescriptions, gp_id, userId)" + " values (?, ?, ?, ?, ?, ?, ?)";
				
				//create mysql prepared statement
				sqlStatement = databaseConnection.prepareStatement(query);
				sqlStatement.setDate(1, patient_dob);
				sqlStatement.setString(2, patient_address);
				sqlStatement.setString(3, patient_medical_history);
				sqlStatement.setString(4, patient_diagnosis);
				sqlStatement.setString(5, patient_prescriptions);
				sqlStatement.setInt(6, gp_id);
				sqlStatement.setInt(7, userId);

				
				sqlStatement.executeUpdate();
				closeDbConnection();

				System.out.println("Patient record added");
				
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
		
		return false;
	}
	

}
