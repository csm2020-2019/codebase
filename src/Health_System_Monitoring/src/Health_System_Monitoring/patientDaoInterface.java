package Health_System_Monitoring;

import java.sql.Date;
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
}
