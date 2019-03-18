package Health_System_Monitoring;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface patientDaoInterface {
    /*
    @param patient the Patient object - Create a patient object and pass into the method
    @param user the user logged into the system (maybe GP) adding the patient
    to database
    we can access the id of the user by user.getUserId()
    @return boolean true if patient record successfully added
    false for not added
     */
    boolean addPatientToDatabase(Patient patient, User user);

    /*
    @param patient is the patient object
    Create a patient object and pass into
    the method
    @return boolean true if updated and false if not updated
     */
    boolean updatePatientRecord(Patient patient);

    /*
     * method to fetch all records from database
     * @return a list of all patients from database
     */
    List<Patient> getAllPatientRecords();


    /*
     method to delete a patient record from database
     @ param patient_id is the id of the row in patient_records table
     @return true if deleted and false if not
    */
    boolean deletePatientRecord(int patient_id);

    /*
    method to search patient
    @param patient name
    @return a list of patients that matches the name
     */
    List<Patient> searchPatient(String patient_last_name);

    public boolean addNiceResults(int patient_id, int user_id, String sex, int age, Date result_date,
                                  int height, int weight, int systolic_bp, int diastolic_bp, boolean smoker,
                                  double haemoglobin, double urinary_albumin, int serum_creatinine, double egfr,
                                  double total_cholesterol, double ldl_level, boolean kidney_damage, boolean eye_damage,
                                  boolean cercbrovascular_damage, boolean vision_loss, boolean eye_haemorrhage,
                                  boolean retinal_detachment, boolean rubeosis, boolean lack_sensation, boolean deformity,
                                  boolean foot_palpitation, boolean inappropriate_behaviour);

    public boolean updateNiceResults(int result_id, int patient_id, int user_id, String sex, int age, Date result_date,
                                     int height, int weight, int systolic_bp, int diastolic_bp, boolean smoker,
                                     double haemoglobin, double urinary_albumin, int serum_creatinine, double egfr,
                                     double total_cholesterol, double ldl_level, boolean kidney_damage, boolean eye_damage,
                                     boolean cercbrovascular_damage, boolean vision_loss, boolean eye_haemorrhage,
                                     boolean retinal_detachment, boolean rubeosis, boolean lack_sensation, boolean deformity,
                                     boolean foot_palpitation, boolean inappropriate_behaviour);
}
