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
	private Connection databaseConnection = null; 
	
	//id of user from database - user table
	private int userId = 0;
	
	//login failed 
	private int loginFailed = -1;
	


	
	
	protected database_driver() {
		try {

			/*
			 * *********** database connection info **********
			 */

			String jdbcUrl = "jdbc:mysql://db.dcs.aber.ac.uk:3306/csm2020_18_19";
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
	public int checkCredentials(String username, String userPassword) throws SQLException {

		if(username != null && userPassword != null) {
            PreparedStatement sqlStatement = null;
            try {
                String query = "SELECT * FROM `user` WHERE `username`=? AND `userPassword`=?";

                sqlStatement = databaseConnection.prepareStatement(query);
                sqlStatement.setString(1, username);
                sqlStatement.setString(2, userPassword);

                ResultSet resultSet = sqlStatement.executeQuery();
//				closeDbConnection();

                if (resultSet.next()) {
                    userId = resultSet.getInt("userId");
                    return userId;
                } else {
                    System.out.println("Incorrect username or password | Login failed");
                    return loginFailed;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());

            } finally {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                closeDbConnection();
            }

        }

		return loginFailed;
	}
	
	/*
	 * - method to add new patients to database
	 * @ patient_dob Date of birth of patient
	 */
	public boolean addNewPatientToDatabase(Date patient_dob, String patient_first_name, String patient_last_name, String patient_address, String patient_medical_history,
			String patient_diagnosis, String patient_prescriptions, int userId) throws SQLException {

		//NULL CHECKER FOR THE  METHOD ARGUMENTS
		if(patient_dob != null && patient_first_name != null && patient_last_name != null && patient_address != null & patient_medical_history != null
				&& patient_diagnosis != null && patient_prescriptions != null && userId > 0) {

            //create a date object to be used for the patient dob
            Calendar calender = Calendar.getInstance();
            patient_dob = new Date(calender.getTime().getTime());

            PreparedStatement sqlStatement = null;
            try {
                String query = "INSERT INTO patient_records (patient_dob, patient_address, patient_medical_history,"
                        + "patient_diagnosis, patient_prescriptions, userId, patient_first_name, patient_last_name)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                //create mysql prepared statement
                sqlStatement = databaseConnection.prepareStatement(query);
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
            } finally {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                closeDbConnection();
            }
        }
		
		
		return false;
	}
	
	/*
	 * method to edit patient record
	 */
	public boolean updatePatientRecord(int patient_id, Date patient_dob, String patient_address, String patient_medical_history,
			String patient_diagnosis, String patient_prescriptions, int userId, String patient_first_name, String patient_last_name) throws SQLException {

		//NULL CHECKER FOR THE  METHOD ARGUMENTS
		if(patient_id > 0 && patient_dob != null && patient_address != null & patient_medical_history != null
				&& patient_diagnosis != null && patient_prescriptions != null && userId > 0 && patient_first_name != null
				&& patient_last_name != null) {

            PreparedStatement sqlStatement = null;
            try {
                String query = "UPDATE patient_records SET patient_dob=?, patient_address=?, patient_medical_history=?,"
                        + "patient_diagnosis=?, patient_prescriptions=?, patient_first_name=?," +
                        "patient_last_name=? where patient_id=?";

                sqlStatement = databaseConnection.prepareStatement(query);
                sqlStatement.setDate(1, patient_dob);
                sqlStatement.setString(2, patient_address);
                sqlStatement.setString(3, patient_medical_history);
                sqlStatement.setString(4, patient_diagnosis);
                sqlStatement.setString(5, patient_prescriptions);
                sqlStatement.setString(6, patient_first_name);
                sqlStatement.setString(7, patient_last_name);
                sqlStatement.setInt(8, patient_id);

                sqlStatement.executeUpdate();
                System.out.println("Patient record updated!");

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                closeDbConnection();
            }
        }

		return false;
	}
	
	/*
	 * method to fetch all records from database
	 */
	public List<Patient> getAllPatientRecords() throws SQLException {
        List<Patient> patientRecordList = new ArrayList<>();

        PreparedStatement sqlStatement = null;
        try {
            String query = "SELECT * FROM patient_records";
            sqlStatement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = sqlStatement.executeQuery();

            Patient patient = null;

            while (resultSet.next()) {
                int patient_id = resultSet.getInt("patient_id");
                Date patient_dob = resultSet.getDate("patient_dob");
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

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {
            if (sqlStatement != null) {
                sqlStatement.close();
            }
            closeDbConnection();
        }

        return patientRecordList;

    }

	/*
	method to delete a patient record from database
	@ param patient_id is the id of the row in patient_records table
	 */
	public boolean deletePatientRecord(int patient_id) throws SQLException {

        PreparedStatement sqlStatement = null;
        try {
            String query = "DELETE FROM patient_records WHERE patient_id=?";

            sqlStatement = databaseConnection.prepareStatement(query);

            sqlStatement.setInt(1, patient_id);

            sqlStatement.executeUpdate();
            System.out.println("Patient record deleted!");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {
            if (sqlStatement != null) {
                sqlStatement.close();
            }
            closeDbConnection();
        }
        return false;
    }

	/*
	method to add nice results to database
	 */
	public boolean addNiceResults(int result_id, int patient_id, int user_id, String sex, int age, Date result_date,
                                  String height, String weight, int systolic_bp, int diastolic_bp, boolean smoker,
                                  BigDecimal haemoglobin, BigDecimal urinary_albumin, int serum_creatinine, BigDecimal egfr,
                                  BigDecimal total_cholesterol, BigDecimal ldl_level, boolean kidney_damage, boolean eye_damage,
                                  boolean cerebrovascular_damage, boolean vision_loss, boolean eye_haemorrhage,
                                  boolean retinal_detachment, boolean rubeosis, boolean lack_senastion, boolean deformity,
                                  boolean foot_palpitation, boolean inappropriate_behaviour) throws SQLException {

	    if(result_id > 0 && patient_id > 0 && user_id > 0 && sex != null && age > 0 && result_date != null &&
                height != null && weight != null) {

            //create a date object to be used for the result_date
            Calendar calender = Calendar.getInstance();
            result_date = new Date(calender.getTime().getTime());

            PreparedStatement sqlStatement = null;

            try {
                String query = "INSERT INTO nice_results (patient_id, user_id, sex, age, result_date, height, weight, " +
                        "systolic_bp, diastolic_bp, smoker, haemoglobin, urinary_albumin, serum_creatinine, egfr, " +
                        "total_cholesterol, ldl_level, kidney_damage, eye_damage, cerebrovascular_damage, vision_loss, " +
                        "eye_haemorrhage, retinal_detachment, rubeosis, lack_senastion, deformity, foot_palpitation, " +
                        "inappropriate_behaviour)" + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?)";

                sqlStatement = databaseConnection.prepareStatement(query);
                sqlStatement.setInt(1, patient_id);
                sqlStatement.setInt(2, user_id);
                sqlStatement.setString(3, sex);
                sqlStatement.setInt(4, age);
                sqlStatement.setDate(5, result_date);
                sqlStatement.setString(6, height);
                sqlStatement.setString(7, weight);
                sqlStatement.setInt(8, systolic_bp);
                sqlStatement.setInt(9, diastolic_bp);
                sqlStatement.setBoolean(10, smoker);
                sqlStatement.setBigDecimal(11, haemoglobin);
                sqlStatement.setBigDecimal(12, urinary_albumin);
                sqlStatement.setInt(13, serum_creatinine);
                sqlStatement.setBigDecimal(14, egfr);
                sqlStatement.setBigDecimal(15, total_cholesterol);
                sqlStatement.setBigDecimal(16, ldl_level);
                sqlStatement.setBoolean(17, kidney_damage);
                sqlStatement.setBoolean(18, eye_damage);
                sqlStatement.setBoolean(19, cerebrovascular_damage);
                sqlStatement.setBoolean(20, vision_loss);
                sqlStatement.setBoolean(21, eye_haemorrhage);
                sqlStatement.setBoolean(22, retinal_detachment);
                sqlStatement.setBoolean(23, rubeosis);
                sqlStatement.setBoolean(24, lack_senastion);
                sqlStatement.setBoolean(25, deformity);
                sqlStatement.setBoolean(26, foot_palpitation);
                sqlStatement.setBoolean(27, inappropriate_behaviour);

                sqlStatement.executeUpdate();
                System.out.println("Nice result added to database");

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());

            } finally {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                closeDbConnection();
            }

        }

	    return false;
    }
}
