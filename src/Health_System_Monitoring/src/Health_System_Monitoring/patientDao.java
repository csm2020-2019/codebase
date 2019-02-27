package Health_System_Monitoring;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.*;


public class patientDao implements patientDaoInterface{

    /*
    @param patient the Patient object
    @param user the user logged into the system (maybe GP) adding the patient
    to database
    we can access the id of the user by user.getUserId()
    @return boolean true if patient record successfully added
    false for not added
     */
    @Override
    public boolean addPatientToDatabase(Patient patient, User user) {

        Connection databaseConnection = database_driver.getConnection();

        //NULL CHECKER FOR THE  METHOD ARGUMENTS
        if(patient != null && user != null) {

            PreparedStatement sqlStatement = null;
            try {
                String query = "INSERT INTO patient_records (patient_dob, patient_address, patient_medical_history,"
                        + "patient_diagnosis, patient_prescriptions, userId, patient_first_name, patient_last_name)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                //create mysql prepared statement
                sqlStatement = databaseConnection.prepareStatement(query);
                sqlStatement.setDate(1, patient.getPatientDob());
                sqlStatement.setString(2, patient.getPatientAddress());
                sqlStatement.setString(3, patient.getPatientMedicalHistory());
                sqlStatement.setString(4, patient.getPatientDiagnosis());
                sqlStatement.setString(5, patient.getPatientPrescriptions());
                sqlStatement.setInt(6, user.getUserId());
                sqlStatement.setString(7, patient.getPatientFirstName());
                sqlStatement.setString(8, patient.getPatientLastName());

                sqlStatement.executeUpdate();
                //closeDbConnection();

                System.out.println("Patient record added");

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());

            } finally {
                if (sqlStatement != null) {
                    try {
                        sqlStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                //closeDbConnection();
            }
        }

        return false;
    }

    @Override
    public boolean updatePatientRecord(Patient patient) {

        Connection databaseConnection = database_driver.getConnection();

        //NULL CHECKER FOR THE  METHOD ARGUMENTS
        if(patient != null) {

            PreparedStatement sqlStatement = null;

            try {
                String query = "UPDATE patient_records SET patient_dob=?, patient_address=?, patient_medical_history=?,"
                        + "patient_diagnosis=?, patient_prescriptions=?, patient_first_name=?," +
                        "patient_last_name=? where patient_id=?";

                sqlStatement = databaseConnection.prepareStatement(query);

                sqlStatement.setDate(1, patient.getPatientDob());
                sqlStatement.setString(2, patient.getPatientAddress());
                sqlStatement.setString(3, patient.getPatientMedicalHistory());
                sqlStatement.setString(4, patient.getPatientDiagnosis());
                sqlStatement.setString(5, patient.getPatientPrescriptions());
                sqlStatement.setString(6, patient.getPatientFirstName());
                sqlStatement.setString(7, patient.getPatientLastName());
                sqlStatement.setInt(8, patient.getPatientId());

                sqlStatement.executeUpdate();
                System.out.println("Patient record updated!");

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());

            } finally {
                if (sqlStatement != null) {
                    try {
                        sqlStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
//                closeDbConnection();
            }
        }
        return false;

    }

    @Override
    public List<Patient> getAllPatientRecords() {
        Connection databaseConnection = database_driver.getConnection();

        List<Patient> patientRecordList = new ArrayList<>();

        PreparedStatement sqlStatement = null;

        try {
            String query = "SELECT * FROM patient_records";
            sqlStatement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = sqlStatement.executeQuery();

            Patient patient = null;

            while (resultSet.next()) {
                int patient_id = resultSet.getInt("patient_id");
                int patient_userid = resultSet.getInt("userId");
                Date patient_dob = resultSet.getDate("patient_dob");
                String patient_first_name = resultSet.getString("patient_first_name");
                String patient_last_name = resultSet.getString("patient_last_name");
                String patient_address = resultSet.getString("patient_address");
                String patient_medical_history = resultSet.getString("patient_medical_history");
                String patient_diagnosis = resultSet.getString("patient_diagnosis");
                String patient_prescriptions = resultSet.getString("patient_prescriptions");

                patient = new Patient(patient_id, patient_userid, patient_first_name, patient_last_name, patient_dob, patient_address,
                        patient_medical_history, patient_diagnosis, patient_prescriptions);

                patientRecordList.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {
            if (sqlStatement != null) {
                try {
                    sqlStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //closeDbConnection();
        }

        return patientRecordList;

    }

    @Override
    public boolean deletePatientRecord(int patient_id) {
        Connection databaseConnection = database_driver.getConnection();

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
                try {
                    sqlStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//            closeDbConnection();
        }
        return false;
    }

    @Override
    public List<Patient> searchPatient(String patient_last_name) {
        Connection databaseConnection = database_driver.getConnection();

        List<Patient> patientMatchList = new ArrayList<>();
        PreparedStatement sqlStatement = null;
        ResultSet resultSet = null;

        try{
            //make it like sql command by adding the percentage
            patient_last_name += "%";

            String query = "SELECT * FROM patient_records WHERE patient_last_name like ?";

            sqlStatement = databaseConnection.prepareStatement(query);

            sqlStatement.setString(1, patient_last_name);
            resultSet = sqlStatement.executeQuery();

            Patient patient = null;

            while (resultSet.next()){
                //call convertToPatient object method below
                patient = convertToPatient(resultSet);

                patientMatchList.add(patient);
            }
            return patientMatchList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {
            if(sqlStatement != null){
                try {
                    sqlStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //closeDbConnection();
        }
        return null;
    }

    /*private method to convert result from database
   into a patient object
   @param resultSet is result from database
   @return patient object
    */
    private Patient convertToPatient(ResultSet resultSet) throws SQLException {

        int patient_id = resultSet.getInt("patient_id");
        Date patient_dob = resultSet.getDate("patient_dob");
        String patient_first_name = resultSet.getString("patient_first_name");
        String patient_last_name = resultSet.getString("patient_last_name");
        String patient_address = resultSet.getString("patient_address");
        String patient_medical_history = resultSet.getString("patient_medical_history");
        String patient_diagnosis = resultSet.getString("patient_diagnosis");
        int patient_userid = resultSet.getInt("userId");
        String patient_prescriptions = resultSet.getString("patient_prescriptions");

        Patient patient = new Patient(patient_id, patient_userid, patient_first_name, patient_last_name, patient_dob, patient_address,
                patient_medical_history, patient_diagnosis, patient_prescriptions);

        return patient;
    }
}
