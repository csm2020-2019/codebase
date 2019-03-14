package Health_System_Monitoring;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;
import java.sql.*;


public class patientDao implements patientDaoInterface{


    @Override
    public boolean addPatientToDatabase(Patient patient, User user) {

        Connection databaseConnection = database_driver.getConnection();

        //NULL CHECKER FOR THE  METHOD ARGUMENTS
        if(patient != null && user != null) {

            PreparedStatement sqlStatement = null;
            try {
                String query = "INSERT INTO patient_records (patient_dob, patient_address, patient_medical_history,"
                        + "patient_diagnosis, patient_prescriptions, userId, patient_first_name, patient_last_name)" +
                        " values (?, ?, ?, ?, ?, ?, ?, ?)";

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


            while (resultSet.next()) {
                Patient patient = convertToPatient(resultSet);
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
        ResultSet resultSet;

        try{
            //make it like sql command by adding the percentage
            patient_last_name += "%";

            String query = "SELECT * FROM patient_records WHERE patient_last_name like ?";

            sqlStatement = databaseConnection.prepareStatement(query);

            sqlStatement.setString(1, patient_last_name);
            resultSet = sqlStatement.executeQuery();

            while (resultSet.next()){
                //call convertToPatient object method below
                Patient patient = convertToPatient(resultSet);

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

        return new Patient(patient_id, patient_userid, patient_first_name, patient_last_name, patient_dob, patient_address,
                patient_medical_history, patient_diagnosis, patient_prescriptions);
    }

    /*
    method to add nice results to database
    @param nice results parameters
    @return true if successfully added to database
     and false if not.
     */
    public boolean addNiceResults(int patient_id, int user_id, String sex, int age, Date result_date,
                                  int height, int weight, int systolic_bp, int diastolic_bp, boolean smoker,
                                  BigDecimal haemoglobin, BigDecimal urinary_albumin, int serum_creatinine, BigDecimal egfr,
                                  BigDecimal total_cholesterol, BigDecimal ldl_level, boolean kidney_damage, boolean eye_damage,
                                  boolean cercbrovascular_damage, boolean vision_loss, boolean eye_haemorrhage,
                                  boolean retinal_detachment, boolean rubeosis, boolean lack_senastion, boolean deformity,
                                  boolean foot_palpitation, boolean inappropriate_behaviour) {

        if(patient_id > 0 && user_id > 0 && sex != null && age > 0 && result_date != null &&
                height > 0 && weight > 0) {

            //create a date object to be used for the result_date
            Calendar calender = Calendar.getInstance();
            result_date = new Date(calender.getTime().getTime());

            Connection databaseConnection = database_driver.getConnection();

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
                sqlStatement.setInt(6, height);
                sqlStatement.setInt(7, weight);
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
                sqlStatement.setBoolean(19, cercbrovascular_damage);
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
                    try {
                        sqlStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        databaseConnection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }

    public boolean updateNiceResults(int result_id, int patient_id, int user_id, String sex, int age, Date result_date,
                                     int height, int weight, int systolic_bp, int diastolic_bp, boolean smoker,
                                     BigDecimal haemoglobin, BigDecimal urinary_albumin, int serum_creatinine, BigDecimal egfr,
                                     BigDecimal total_cholesterol, BigDecimal ldl_level, boolean kidney_damage, boolean eye_damage,
                                     boolean cercbrovascular_damage, boolean vision_loss, boolean eye_haemorrhage,
                                     boolean retinal_detachment, boolean rubeosis, boolean lack_senastion, boolean deformity,
                                     boolean foot_palpitation, boolean inappropriate_behaviour){

        if(result_id > 0 && patient_id > 0 && user_id > 0 && sex != null && age > 0 && result_date != null &&
                height > 0 && weight > 0){

            Connection databaseConnection = database_driver.getConnection();
            PreparedStatement sqlStatement = null;

            try{
                String query = "UPDATE nice_results SET sex=?, age=?, result_date=?, height=?, weight=?, systolic_bp=?, " +
                        "diastolic_bp=?, smoker=?, haemoglobin=?, urinary_albumin=?, serum_creatinine=?, egfr=?, " +
                        "total_cholesterol=?, ldl_level=?, kidney_damage=?, eye_damage=?, cercbrovascular_damage=?, " +
                        "vision_loss=?, eye_haemorrhage=?, retinal_detachment=?, rubeosis=?, lack_senastion=?, deformity=?, " +
                        "foot_palpitation=?, inappropriate_behaviour=? where result_id=?";

                sqlStatement = databaseConnection.prepareStatement(query);
                sqlStatement.setString(1, sex);
                sqlStatement.setInt(2, age);
                sqlStatement.setDate(3, result_date);
                sqlStatement.setInt(4, height);
                sqlStatement.setInt(5, weight);
                sqlStatement.setInt(6, systolic_bp);
                sqlStatement.setInt(7, diastolic_bp);
                sqlStatement.setBoolean(8, smoker);
                sqlStatement.setBigDecimal(9, haemoglobin);
                sqlStatement.setBigDecimal(10, urinary_albumin);
                sqlStatement.setInt(11, serum_creatinine);
                sqlStatement.setBigDecimal(12, egfr);
                sqlStatement.setBigDecimal(13, total_cholesterol);
                sqlStatement.setBigDecimal(14, ldl_level);
                sqlStatement.setBoolean(15, kidney_damage);
                sqlStatement.setBoolean(16, eye_damage);
                sqlStatement.setBoolean(17, cercbrovascular_damage);
                sqlStatement.setBoolean(18, vision_loss);
                sqlStatement.setBoolean(19, eye_haemorrhage);
                sqlStatement.setBoolean(20, retinal_detachment);
                sqlStatement.setBoolean(21, rubeosis);
                sqlStatement.setBoolean(22, lack_senastion);
                sqlStatement.setBoolean(23, deformity);
                sqlStatement.setBoolean(24, foot_palpitation);
                sqlStatement.setBoolean(25, inappropriate_behaviour);
                sqlStatement.setInt(26, result_id);


                sqlStatement.executeUpdate();
                System.out.println("Nice result updated");

                return true;

            } catch(SQLException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                if (sqlStatement != null) {
                    try {
                        sqlStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        databaseConnection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
