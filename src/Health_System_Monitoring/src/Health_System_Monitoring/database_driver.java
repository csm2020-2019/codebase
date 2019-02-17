package Health_System_Monitoring;
//singleton class to get database connection

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
					closeDbConnection();
					return userId;
				}
				else {
					System.out.println("Incorrect username or password | Login failed");
					closeDbConnection();
					return loginFailed;
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		closeDbConnection();
		return loginFailed;
	}
	
	/*
	 * - method to add new patients to database
	 * @ patient_dob Date of birth of patient
	 */
	public boolean addNewPatientToDatabase(Date patient_dob, String patient_first_name, String patient_last_name, String patient_address, String patient_medical_history,
			String patient_diagnosis, String patient_prescriptions, int userId) {

		//NULL CHECKER FOR THE  METHOD ARGUMENTS
		if(patient_dob != null && patient_first_name != null && patient_last_name != null && patient_address != null & patient_medical_history != null
				&& patient_diagnosis != null && patient_prescriptions != null && userId > 0) {
			
			//create a date object to be used for the patient dob
			Calendar calender = Calendar.getInstance();
			patient_dob = new Date(calender.getTime().getTime());
			
			try {
				String query = "INSERT INTO patient_records (patient_dob, patient_address, patient_medical_history,"
						+ "patient_diagnosis, patient_prescriptions, userId, patient_first_name, patient_last_name)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
				
				//create mysql prepared statement
				PreparedStatement sqlStatement = databaseConnection.prepareStatement(query);
				sqlStatement.setDate(1, patient_dob);
				sqlStatement.setString(2, patient_address);
				sqlStatement.setString(3, patient_medical_history);
				sqlStatement.setString(4, patient_diagnosis);
				sqlStatement.setString(5, patient_prescriptions);
				sqlStatement.setInt(6, userId);
				sqlStatement.setString(7, patient_first_name);
				sqlStatement.setString(8, patient_last_name);

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
	
//	/*
//	 * method to edit patient record
//	 */
//	public boolean updatePatientRecord(int patient_id, Date patient_dob, String patient_address, String patient_medical_history,
//			String patient_diagnosis, String patient_prescriptions, int userId) {
//		PreparedStatement sqlStatement = null;
//		
//		//NULL CHECKER FOR THE  METHOD ARGUMENTS
//		if(patient_id > 0 && patient_dob != null && patient_address != null & patient_medical_history != null
//				&& patient_diagnosis != null && patient_prescriptions != null && userId > 0) {
//			
//			try {
//				String query = "UPDATE patient_records SET patient_dob=?, patient_address=?, patient_medical_history=?,"
//						+ "patient_diagnosis=?, patient_prescriptions=? where patient_id=?";
//				
//				sqlStatement = databaseConnection.prepareStatement(query);
//				sqlStatement.setDate(2, patient_dob);
//				
//				
//			} catch (SQLException e) {
//				
//			}
//		}
//		
//		return false;
//	}
	
	/*
	 * method to fetch all records from database
	 */
	public List<Patient> getAllPatientRecords() {
		List<Patient> patientRecordList = new ArrayList<>();
			
		
		try {
			String query = "SELECT * FROM patient_records";
			PreparedStatement sqlStatement = databaseConnection.prepareStatement(query);
			ResultSet resultSet = sqlStatement.executeQuery();

			Patient patient = null;
			while(resultSet.next()) {
				int patient_id = resultSet.getInt("patient_id");
				Date patient_dob =  resultSet.getDate("patient_dob");
				String patient_first_name = resultSet.getString("patient_first_name");
				String patient_last_name = resultSet.getString("patient_last_name");
				String patient_address = resultSet.getString("patient_address");
				String patient_medical_history = resultSet.getString("patient_medical_history");
				String patient_diagnosis = resultSet.getString("patient_diagnosis");
				String patient_prescriptions = resultSet.getString("patient_prescriptions");	
				
				patient = new Patient(patient_id, patient_first_name, patient_last_name, patient_dob, patient_address,
						patient_medical_history, patient_diagnosis, patient_prescriptions);
								
				patientRecordList.add(patient);
			}
			
			
			
//			for(Patient patientView : patientRecordList) {
//				System.out.println(patientView);
//			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		closeDbConnection();
		return patientRecordList;
		
	}

	/*
	method to delete a patient record from database
	 */
	public boolean deletePatientRecord(int patient_id){
		PreparedStatement sqlStatement = null;

		try{
			String query = "DELETE FROM patient_records WHERE patient_id=?";

			sqlStatement = databaseConnection.prepareStatement(query);

			sqlStatement.setInt(1, patient_id);

			sqlStatement.executeUpdate();

			closeDbConnection();
			System.out.println("Patient record deleted!");
			return true;

		} catch (SQLException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}


}
